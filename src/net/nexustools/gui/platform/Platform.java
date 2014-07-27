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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.nexustools.gui.Base;
import net.nexustools.gui.Widget;
import nexustools.io.format.StreamTokenizer;
import nexustools.runtime.DefaultRunQueue;

/**
 *
 * @author katelyn
 */
public abstract class Platform extends DefaultRunQueue {
	
	private static final HashMap<Class<? extends Platform>, Platform> platformsByClass = new HashMap();
	private static final HashMap<String, Platform> platformsByName = new HashMap();
	private static final ArrayList<Platform> allPlatforms = new ArrayList();
	private static boolean needScanPlatforms = true;
	
	public static void scanPlatforms() {
		needScanPlatforms = false;
	}
	public static void register(Platform platform) {
		allPlatforms.add(platform);
		platformsByName.put(platform.name(), platform);
		platformsByClass.put(platform.getClass(), platform);
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
		
		ArrayList<Platform> compatible = new ArrayList<Platform>(allPlatforms);
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
	
	public abstract Base create(Class<? extends Base> type) throws RenderTargetSupportedException;
	public abstract Widget parse(StreamTokenizer processor) throws PlatformException;
	public abstract boolean supports(Feature feature);
	
	public abstract String[] LAFs();
	public abstract void setLAF(String laf);
	public abstract String LAF();
	
	public abstract void onIdle(Runnable run);
	public abstract void act(Runnable run) throws InvocationTargetException;
	
	public abstract void open(String url);
    
}
