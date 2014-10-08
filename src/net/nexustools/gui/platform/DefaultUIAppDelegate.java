/*
 * jgenui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 or any later version.
 * 
 * jgenui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with jgenui.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.nexustools.gui.platform;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import net.nexustools.DefaultAppDelegate;
import static net.nexustools.Application.defaultName;
import static net.nexustools.Application.defaultOrganization;
import net.nexustools.gui.impl.Body;
import net.nexustools.utils.Pair;
import net.nexustools.utils.log.Logger;

/**
 *
 * @author kate
 */
public abstract class DefaultUIAppDelegate<B extends Body, P extends GUIPlatform> extends DefaultAppDelegate<P> {
	
	private final P platform;
	protected DefaultUIAppDelegate(String[] args, P platform) {
		this(args, defaultName(), defaultOrganization(), platform);
	}
	protected DefaultUIAppDelegate(String[] args, String name, String organization) {
		this(args, name, organization, (P)GUIPlatform.findRichestImpl());
	}
	protected DefaultUIAppDelegate(String[] args, String name, String organization, P platform) {
		super(args, name, organization);
		this.platform = platform;
	}
	
	protected B create() {
		return (B) platform.create(Body.class);
	}

	@Override
	protected Runnable launch(String[] args) throws Throwable {
		Logger.debug("Creating GUI Body");
		B body = create();

		Logger.debug("Initializing GUI Body");
		populate(args, body);

		Logger.debug("Showing GUI Body");
		body.setVisible(true);
		
		return new Runnable() {
			public void run() {
				while(true)
					try {
						Thread.sleep(50);
					} catch (InterruptedException ex) {}
			}
		};
	}
	protected abstract void populate(String[] args, B body);

    public static DefaultUIAppDelegate start(String[] args) throws NoSuchMethodException, ClassNotFoundException {
    	return start((Class<? extends DefaultUIAppDelegate>)Class.forName(System.getProperty("")), args);
    }
    public static DefaultUIAppDelegate start(Class<? extends DefaultUIAppDelegate> guiDelegate, String[] args) throws NoSuchMethodException {
		try {
			return tryConstruct(guiDelegate, new Pair(String[].class, args), new Pair(String.class, defaultName()), new Pair(String.class, defaultOrganization()));
		} catch(Throwable t) {}
		try {
			return tryConstruct(guiDelegate, new Pair(String[].class, args));
		} catch(Throwable t) {}
		try {
			DefaultUIAppDelegate delegate = tryConstruct(guiDelegate);
			Logger.warn("Arguments were ignored by created AppDelegate");
			return delegate;
		} catch(Throwable t) {}
		
		throw new NoSuchMethodException("No valid constructors found in class: " + guiDelegate.getName());
    }
    
    private static DefaultUIAppDelegate tryConstruct(Class<? extends DefaultUIAppDelegate> delegate, Pair<Class<?>, ?>... args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		Class<?>[] argClasses = new Class[args.length];
		Object[] argObjects = new Object[args.length];
		for(int i=0; i<args.length; i++) {
			argClasses[i] = args[i].i;
			argObjects[i] = args[i].v;
		}
		Constructor<? extends DefaultUIAppDelegate> constructor = delegate.getConstructor(argClasses);
		return constructor.newInstance(argObjects);
    }
    
}
