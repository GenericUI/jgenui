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

import net.nexustools.concurrent.Prop;

/**
 *
 * @author katelyn
 */
public class Style {
	
	public static abstract class BorderRenderer extends Renderer {
		public abstract int top();
		public abstract int left();
		public abstract int right();
		public abstract int bottom();
	}
	
	public final Prop<Renderer> background = new Prop();
	public final Prop<Renderer> foreground = new Prop();
	public final Prop<BorderRenderer> border = new Prop();
	
}
