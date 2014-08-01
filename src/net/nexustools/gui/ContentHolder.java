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

import java.util.ListIterator;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;

/**
 *
 * @author katelyn
 * @param <T> The type of content this ContentHolder holds.
 */
public interface ContentHolder<T> extends Widget, Iterable<T> {
	
	public static interface ContentIterator<T> {
		public void iterate(ListIterator<T> it);
	}
	
	/**
	 * Creates a write lock and than runs the content iterator.
	 * Allows advanced modification of the widget within a locked environment.
	 * 
	 * @param it The ContentIterator to use
	 */
	public void iterate(ContentIterator<T> it);
	
	public Rect contentBounds();
	public Point contentOffset();
	public Size contentSize();
	
}
