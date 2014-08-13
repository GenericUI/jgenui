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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.nexustools.concurrent.IfWriter;
import net.nexustools.concurrent.MapAccessor;
import net.nexustools.concurrent.Prop;
import net.nexustools.concurrent.PropAccessor;
import net.nexustools.concurrent.PropList;
import net.nexustools.concurrent.PropMap;
import net.nexustools.concurrent.WriteReader;
import net.nexustools.event.DefaultEventDispatcher;
import net.nexustools.event.EventDispatcher;
import net.nexustools.gui.err.GUIPlatformException;
import net.nexustools.gui.err.UnsupportedWidgetType;
import net.nexustools.gui.event.LAFListener;
import net.nexustools.gui.event.LAFListener.LAFEvent;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.style.StyleRoot;
import net.nexustools.gui.style.StyleSheet;
import net.nexustools.io.Stream;
import net.nexustools.io.format.JSONParser;
import net.nexustools.io.format.StringParser;
import net.nexustools.io.format.XMLParser;
import net.nexustools.runtime.ThreadedRunQueue;
import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 * 
 */
public abstract class GUIPlatform extends ThreadedRunQueue implements StyleRoot {
	
	private static final ThreadLocal<GUIPlatform> current = new ThreadLocal();
	private static final PropMap<Class<? extends GUIPlatform>, GUIPlatform> platformsByClass = new PropMap();
	private static final PropMap<String, GUIPlatform> platformsByName = new PropMap();
	private static final PropMap<String, StyleSheet> cssLAFs = new PropMap();
	private static final PropList<GUIPlatform> allPlatforms = new PropList();
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
			for(final GUIPlatform platform : allPlatforms)
				platform.eventDispatcher.dispatch(new EventDispatcher.Processor<LAFListener, LAFEvent>() {
					public LAFEvent create() {
						return new LAFEvent(name, platform);
					}
					public void dispatch(LAFListener listener, LAFEvent event) {
						listener.lafDiscovered(event);
					}
				});
	}

	public static void resolve() {
		if(current.get() == null) {
			GUIPlatform resolved = allPlatforms.first();
			
			current.set(resolved);
		}
	}
	
	protected void lafChanged(final String name) {
		for(final GUIPlatform platform : allPlatforms)
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
	
	public static GUIPlatform current() {
		return current.get();
	}
	
	public static void scanPlatforms() {
		needScanPlatforms = false;
	}
	public static void register(GUIPlatform platform) {
		if(allPlatforms.unique(platform)) {
			platformsByName.put(platform.name(), platform);
			platformsByClass.put(platform.getClass(), platform);
		}
	}
	
	public static GUIPlatform byName(String name) {
		if(needScanPlatforms)
			scanPlatforms();
		return platformsByName.get(name);
	}
	public static GUIPlatform byClass(Class<? extends GUIPlatform> pClass) {
		GUIPlatform platform = platformsByClass.get(pClass);
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
	public static GUIPlatform best(Feature[] desired) {
		return best(desired, new Feature[0]);
	}
	public static GUIPlatform best(Feature[] desired, Feature[] required) {
		if(needScanPlatforms)
			scanPlatforms();
		
		List<GUIPlatform> compatible = allPlatforms.copy();
		Iterator<GUIPlatform> it = compatible.iterator();
		while(it.hasNext()) {
			GUIPlatform platform = it.next();
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
		
		AccurateTimers
	}
	
	static {
		try {
			registerLAF("Blank", "resource:/net/nexustools/gui/blank.css");
		} catch (IOException ex) {
		} catch (URISyntaxException ex) {}
	}
	
	public static interface WidgetRegistry {
		public <B extends Widget, P extends GUIPlatform> void add(Class<B> type, Creator<B, P> creator);
	}
	
	private final Prop<String> laf = new Prop();
	private final HashMap<Class<?>, Creator> typeMap = new HashMap();
	protected GUIPlatform(String name) {
        super(name + "-RunQueue");
        System.out.println("Spawning Platform `" + name + '`');
        makeCurrent();
	}
	
	protected abstract void populate(WidgetRegistry baseRegistry);
	
	protected void initTypes() {
		if(typeMap.isEmpty())
			populate(new WidgetRegistry() {
				public <B extends Widget, P extends GUIPlatform> void add(Class<B> type, Creator<B, P> creator) {
					typeMap.put(type, creator);
				}
			});
	}
	
	public final <B extends Widget> B create(Class<B> type) throws UnsupportedWidgetType{
		initTypes();
		
		Creator<B, GUIPlatform> creator = typeMap.get(type);
		if(creator == null)
			throw new UnsupportedWidgetType();
		return creator.create(this);
	}
	public final Widget parse(String path) throws GUIPlatformException, IOException, URISyntaxException{
		Stream stream = Stream.open(path);
		String mimetype = stream.getMimeType();
		if(mimetype.endsWith("/xml"))
			return parse(new XMLParser(stream));
		else if(mimetype.endsWith("/json"))
			return parse(new JSONParser(stream));
		else
			throw new GUIPlatformException("No parser for: " + mimetype);
	}
	public final Widget parse(XMLParser xmlParser) throws GUIPlatformException{
		return null;
	}
	public final Widget parse(JSONParser jsonParser) throws GUIPlatformException{
		return null;
	}
	public final Widget parse(StringParser parser) throws GUIPlatformException{
		if(parser instanceof XMLParser)
			return parse((XMLParser)parser);
		else if(parser instanceof JSONParser)
			return parse((JSONParser)parser);
		throw new GUIPlatformException("Cannot use: " + parser.getClass().getName());
	}
	
	public abstract boolean supports(Feature feature);
	
	public abstract Clipboard clipboard();
	
    public String[] LAFs() {
        return cssLAFs();
    }
	
	public void setLAF(final String laf) {
		this.laf.write(new IfWriter<PropAccessor<String>>() {
			@Override
			public boolean test(PropAccessor<String> against) {
				return !laf.equals(against.get());
			}
			@Override
			public void write(PropAccessor<String> data) {
				setStyleSheet(lafStyleSheet(laf));
				data.set(laf);
			}
		});
	}
	
	public String LAF() {
		return laf.get();
	}
	
	protected final EventDispatcher eventDispatcher = new DefaultEventDispatcher(this);
	public void addLAFListener(LAFListener lafListener) {
		eventDispatcher.add(lafListener);
	}
	public void removeLAFListener(LAFListener lafListener) {
		eventDispatcher.remove(lafListener);
	}
	
	public abstract void open(String url);
	
	@Override
	public final void makeCurrent() {
		current.set(this);
	}
    
}
