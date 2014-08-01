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
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.UIManager;
import net.nexustools.concurrent.IfWriter;
import net.nexustools.concurrent.MapAccessor;
import net.nexustools.concurrent.PropList;
import net.nexustools.concurrent.PropMap;
import net.nexustools.concurrent.WriteReader;
import net.nexustools.event.DefaultEventDispatcher;
import net.nexustools.event.Event;
import net.nexustools.event.EventDispatcher;
import net.nexustools.gui.Base;
import net.nexustools.gui.StyleRoot;
import net.nexustools.gui.Widget;
import net.nexustools.gui.event.LAFListener;
import net.nexustools.gui.event.LAFListener.LAFEvent;
import net.nexustools.gui.render.StyleSheet;
import net.nexustools.io.format.StreamReader;
import net.nexustools.runtime.ThreadedRunQueue;

/**
 *
 * @author katelyn
 * 
 * @param <W> Base type of native widgets
 */
public abstract class Platform<W> extends ThreadedRunQueue implements StyleRoot {
	
	private static final ThreadLocal<Platform> current = new ThreadLocal();
	private static final PropMap<Class<? extends Platform>, Platform> platformsByClass = new PropMap();
	private static final PropMap<String, Platform> platformsByName = new PropMap();
	private static final PropMap<String, StyleSheet> cssLAFs = new PropMap();
	private static final PropList<Platform> allPlatforms = new PropList();
	private static boolean needScanPlatforms = true;
	
	public static void registerLAF(String name, String path) throws IOException, URISyntaxException {
		registerLAF(name, new StyleSheet(path));
	}
	public static void registerLAF(final String name, final StyleSheet styleSheet) throws IOException, URISyntaxException {
		if(cssLAFs.read(new WriteReader<Boolean, MapAccessor<String, StyleSheet>>() {
					@Override
					public Boolean read(MapAccessor<String, StyleSheet> data) {
						try {
							return !data.has(name);
						} finally {
							data.put(name, styleSheet);
						}
					}
				}))
			for(final Platform platform : allPlatforms)
				platform.eventDispatcher.dispatch(new EventDispatcher.Processor<LAFListener, LAFEvent>() {
					public LAFEvent create() {
						return new LAFEvent(name, platform);
					}
					public void dispatch(LAFListener listener, LAFEvent event) {
						listener.lafDiscovered(event);
					}
				});
	}
	
	protected void lafChanged(final String name) {
		for(final Platform platform : allPlatforms)
			platform.eventDispatcher.dispatch(new EventDispatcher.Processor<LAFListener, LAFEvent>() {
				public LAFEvent create() {
					return new LAFEvent(name, platform);
				}
				public void dispatch(LAFListener listener, LAFEvent event) {
					listener.lafChanged(event);
				}
			});
	}
	
	public static StyleSheet lafStyleSheet(String laf) {
		return cssLAFs.get(laf);
	}
	
	public static String[] cssLAFs() {
		Set<String> keySet = cssLAFs.copy().keySet();
		return keySet.toArray(new String[keySet.size()]);
	}
	
	static {
		try {
			registerLAF("Blank", "resource:/net/nexustools/gui/lafs/Blank.css");
			registerLAF("HyperSpace", "resource:/net/nexustools/gui/lafs/HyperSpace.css");
		} catch (IOException ex) {
		} catch (URISyntaxException ex) {
		}
	}
	
	public static Platform current() {
		return current.get();
	}
	
	public static void scanPlatforms() {
		needScanPlatforms = false;
	}
	public static void register(Platform platform) {
		if(allPlatforms.unique(platform)) {
			platformsByName.put(platform.name(), platform);
			platformsByClass.put(platform.getClass(), platform);
		}
	}
	
	public static Platform byName(String name) {
		if(needScanPlatforms)
			scanPlatforms();
		return platformsByName.get(name);
	}
	public static Platform byClass(Class<? extends Platform> pClass) {
		Platform platform = platformsByClass.get(pClass);
		if(platform == null) {
			try {
				platform = pClass.newInstance();
				register(platform);
			} catch (InstantiationException ex) {
				throw new RuntimeException(ex);
			} catch (IllegalAccessException ex) {
				throw new RuntimeException(ex);
			}
		}
		return platform;
	}
	public static Platform best(Feature[] desired) {
		return best(desired, new Feature[0]);
	}
	public static Platform best(Feature[] desired, Feature[] required) {
		if(needScanPlatforms)
			scanPlatforms();
		
		List<Platform> compatible = allPlatforms.copy();
		Iterator<Platform> it = compatible.iterator();
		while(it.hasNext()) {
			Platform platform = it.next();
			for(Feature req : required)
				if(!platform.supports(req)) {
					it.remove();
					break;
				}
		}
		// TODO: Sort by desire list
		return compatible.get(0); 
	}
	
	public static enum Feature {
		/**
		 * Indicates whether or not this platform can provide complex drawing
		 * support when using a painter.
		 * 
		 * Lack of this feature may indicate use of a text interface,
		 * or segment display of some kind.
		 * 
		 * This includes Images, Polygons and Gradients.
		 */
		ComplexDrawing,
		/**
		 * Indicates whether or not this platform can provide GPU acceleration.
		 * This doesn't imply 3D rendering, or FastCanvas.
		 */
		GPUAcceleration,
		/**
		 * Indicates whether or not this platform can render 3D objects.
		 */
		GPU3DRendering,
		/**
		 * Indicates whether or not this platform provides video support.
		 * Whether or not audio is available is another issue.
		 */
		VideoPlayback,
		/**
		 * Indicates whether or not this platform provides video support.
		 * Whether or not video is available is another issue.
		 */
		AudioPlayback,
		/**
		 * Indicates whether or not the full feature set of the painter is available.
		 * For individual features you'll need to query a widget directly.
		 */
		FullPainter,
		/**
		 * Indicates whether or not the canvas implementation is considered fast.
		 * 
		 * A fast canvas can still be drawn without a GPU, it just needs to be
		 * sufficient to play most 2D games.
		 * 
		 */
		FastCanvas,
		
		AccurateTimers,
		/**
		 * Indicates whether or not this platform can have multiple bodies,
		 * a body is essentially a main window.
		 * 
		 * Mobile, Web and Text platforms generally don't support this feature.
		 * Desktop platforms usually do, but Windows 8 can complicate that.
		 * 
		 */
		MultipleBodies
	}
	
	static {
		try {
			registerLAF("Blank", "resource:/net/nexustools/gui/blank.css");
		} catch (IOException ex) {
		} catch (URISyntaxException ex) {}
	}
	
	public Platform(String name) {
		super(name);
	}
	
	public abstract Base create(Class<? extends Base> type) throws RenderTargetNotSupportedException;
	public abstract Widget parse(StreamReader processor) throws PlatformException;
	public abstract boolean supports(Feature feature);
	
	public abstract Clipboard clipboard();
	
    public String[] LAFs() {
        String[] LAFs = LAFs0();
        String[] cssLAFs = cssLAFs();
        String[] allLAFs = new String[LAFs.length + cssLAFs.length];
        for(int i=0; i<LAFs.length; i++) {
            allLAFs[i] = LAFs[i];
        }
        for(int i=0; i<cssLAFs.length; i++)
            allLAFs[LAFs.length+i] = cssLAFs[i];
        return allLAFs;
    }
	
	public void setLAF(String laf) {
		setLAF0(laf);
		lafChanged(laf);
	}
	
	protected abstract String[] LAFs0();
	protected abstract void setLAF0(String laf);
	public abstract String LAF();
	
	protected final EventDispatcher eventDispatcher = new DefaultEventDispatcher(this);
	public void addLAFListener(LAFListener lafListener) {
		eventDispatcher.add(lafListener);
	}
	public void removeLAFListener(LAFListener lafListener) {
		eventDispatcher.remove(lafListener);
	}
	
	public abstract void onIdle(Runnable run);
	public abstract void act(Runnable run) throws InvocationTargetException;
	public abstract W nativeFor(Widget widget) throws PlatformException;
	public abstract <T, F> T convert(F from) throws PlatformException;
	
	public abstract void open(String url);
	
	@Override
	public final void makeCurrent() {
		current.set(this);
	}
    
}
