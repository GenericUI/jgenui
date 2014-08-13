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

package net.nexustools.gui.impl;

import java.util.Iterator;
import java.util.ListIterator;
import net.nexustools.concurrent.PropList;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.style.ID;

/**
 *
 * @author katelyn
 * @param <T> The type of content this ContentHolder holds.
 */
public interface ContentHolder<T> extends Widget, Iterable<T> {
	
	public Rect contentBounds();
	public Point contentOffset();
	public Size contentSize();
	
	public Widget findByID(String id);
	public Iterable<Widget> searchByID(String id);
	public Iterable<Widget> search(ID.Path path);
	
	public void iterate(PropList.PropIterator<T> widget);
	public void clearContent();
	
}
