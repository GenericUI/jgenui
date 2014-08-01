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

import net.nexustools.gui.event.LayoutListener;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.layout.LayoutObject;

/**
 *
 * @author katelyn
 * @param <O>
 */
public interface LayoutContainer<O extends LayoutObject> extends Iterable<O> {
	
	public void addLayoutListener(LayoutListener listener);
	public void removeLayoutListener(LayoutListener listener);
	
	public void setLayout(Layout<O> layout);
	public Layout<O> layout();
	
}
