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

package net.nexustools.gui.layout;

import net.nexustools.gui.geom.Size;

/**
 *
 * @author katelyn
 * @param <O> The type of LayoutObject this layout is for
 */
public interface Layout<O extends LayoutObject> {
	
	public Size calculateMinimumSize(Iterable<O> container);
	public Size calculatePreferredSize(Iterable<O> container);
	public void performLayout(Iterable<O> container, Size prefSize, Size size, int count);
	
}
