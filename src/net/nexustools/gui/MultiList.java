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

import net.nexustools.gui.event.SelectionListener;

/**
 *
 * @author katelyn
 */
public interface MultiList<I> extends List<I>, ComplexSelection {
	
	public I[] selected();
	
	public boolean isAllowingMultiple();
	public void allowMultiple(boolean yes);
	
	// Events
	public void addSelectionListener(SelectionListener<I, MultiList> selectionListener);
	public void removeSelectionListener(SelectionListener<I, MultiList> selectionListener);
	
}
