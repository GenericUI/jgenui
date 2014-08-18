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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import net.nexustools.AppDelegate;
import net.nexustools.gui.impl.Body;
import net.nexustools.utils.Pair;
import net.nexustools.utils.log.Logger;

/**
 *
 * @author kate
 */
public abstract class GUIAppDelegate<B extends Body, P extends GUIPlatform> extends AppDelegate<P> {
	
	protected GUIAppDelegate(String[] args, P platform) {
		this(args, defaultName(), defaultOrganization(), platform);
	}
	protected GUIAppDelegate(String[] args, String name, String organization) {
		this(args, name, organization, (P)GUIPlatform.findRichestImpl());
	}
	protected GUIAppDelegate(String[] args, String name, String organization, P platform) {
		super(args, name, organization, platform);
	}
	
	protected B create() {
		return (B) queue().create(Body.class);
	}
	protected void launch(String[] args) {
		Logger.debug("Creating GUI Body");
		B body = create();

		Logger.debug("Initializing GUI Body");
		populate(args, body);

		Logger.debug("Showing GUI Body");
		body.setVisible(true);
	}
	protected abstract void populate(String[] args, B body);

    public static GUIAppDelegate start(String[] args) throws NoSuchMethodException, ClassNotFoundException {
    	return start((Class<? extends GUIAppDelegate>)Class.forName(System.getProperty("")), args);
    }
    public static GUIAppDelegate start(Class<? extends GUIAppDelegate> guiDelegate, String[] args) throws NoSuchMethodException {
		try {
			return tryConstruct(guiDelegate, new Pair(String[].class, args), new Pair(String.class, defaultName()), new Pair(String.class, defaultOrganization()));
		} catch(Throwable t) {}
		try {
			return tryConstruct(guiDelegate, new Pair(String[].class, args));
		} catch(Throwable t) {}
		try {
			GUIAppDelegate delegate = tryConstruct(guiDelegate);
			Logger.warn("Arguments were ignored by created AppDelegate");
			return delegate;
		} catch(Throwable t) {}
		
		throw new NoSuchMethodException("No valid constructors found in class: " + guiDelegate.getName());
    }
    
    private static GUIAppDelegate tryConstruct(Class<? extends GUIAppDelegate> delegate, Pair<Class<?>, ?>... args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		Class<?>[] argClasses = new Class[args.length];
		Object[] argObjects = new Object[args.length];
		for(int i=0; i<args.length; i++) {
			argClasses[i] = args[i].i;
			argObjects[i] = args[i].v;
		}
		Constructor<? extends GUIAppDelegate> constructor = delegate.getConstructor(argClasses);
		return constructor.newInstance(argObjects);
    }
    
}
