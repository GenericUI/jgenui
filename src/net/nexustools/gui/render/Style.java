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

import net.nexustools.concurrent.CacheProp;
import net.nexustools.concurrent.Prop;
import net.nexustools.gui.impl.Widget;

/**
 *
 * @author katelyn
 */
public class Style implements Renderer {
	
	public static class Pen {
		final Prop<Color> color = new Prop();
	}
	
	public static class Brush {
	}
	
	public static class Border {
		final Prop<Integer> gaps = new Prop();
	}
	
	private final Pen pen = new Pen();
	private final Brush brush = new Brush();
	private final Border border = new Border();
	
	public static Style createFor(Widget widget) {
		return null;
	}
	
	public void render(Renderable widget, Painter painter) {
		//compiledRenderer.get().render(widget, painter);
	}

	public void inherit(Style v) {
	
	}
	
}
