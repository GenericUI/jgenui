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

package net.nexustools.gui;

import net.nexustools.concurrent.MapAccessor;
import net.nexustools.concurrent.PropMap;
import net.nexustools.concurrent.SoftWriteReader;
import net.nexustools.concurrent.Writer;
import net.nexustools.gui.impl.Body;
import net.nexustools.gui.impl.Container;
import net.nexustools.gui.impl.Frame;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.platform.Platform;

/**
 *
 * @author katelyn
 */
public class GUIWrapper<W extends Widget> {
	
	public static PropMap<Widget, GUIWidget> widgetMap = new PropMap(PropMap.Type.WeakHashMap);
	
	public static void register(final Widget widget, final GUIWidget guiWidget) {
		widgetMap.write(new Writer<MapAccessor<Widget, GUIWidget>>() {
			@Override
			public void write(MapAccessor<Widget, GUIWidget> data) {
				data.put(widget, guiWidget);
			}
		});
	}
	
	public static GUIWidget wrap(final Widget widget, final Platform platform) {
		if(widget instanceof GUIWidget)
			return ((GUIWidget)widget);
		
		return widgetMap.read(new SoftWriteReader<GUIWidget, MapAccessor<Widget, GUIWidget>>() {

			@Override
			public boolean test(MapAccessor<Widget, GUIWidget> against) {
				return !against.has(widget);
			}
			
			@Override
			public GUIWidget soft(MapAccessor<Widget, GUIWidget> data) {
				return data.get(widget);
			}

			@Override
			public GUIWidget read(MapAccessor<Widget, GUIWidget> data) {
				GUIWidget newWidget;
				
				if(widget instanceof Body)
					newWidget = new GUIBody((Body)widget, platform);
				else if(widget instanceof Frame)
					newWidget = new GUIFrame((Frame)widget, platform);
				else if(widget instanceof Body)
					newWidget = new GUIContainer((Container)widget, platform);
				else
					newWidget = new GUIWidget(widget, platform);
				
				data.put(widget, newWidget);
				return newWidget;
			}
		});
	}
	
}
