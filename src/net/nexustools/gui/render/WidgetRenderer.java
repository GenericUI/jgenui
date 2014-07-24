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

package net.nexustools.gui.render;

import net.nexustools.gui.Widget;

/**
 *
 * @author katelyn
 */
public class WidgetRenderer extends Renderer {
	
	private final Widget widget;
	public WidgetRenderer(Widget widget) {
		this.widget = widget;
	}
	
	public final Widget widget() {
		return widget;
	}

	public void render(Painter p) {
		
	}
	
}
