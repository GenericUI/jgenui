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

import net.nexustools.utils.Creator;

/**
 *
 * @author katelyn
 */
public interface AdvList<I> extends List<I> {
	
	public Creator<Widget, I> cellCreator();
	public void setCellCreator(Creator<Widget, I> cellCreator);
	
	/**
	 * Adds a creator which can display details about selected items.
	 * 
	 * @param detailCreator 
	 */
	public void setDetailCreator(Creator<Widget, I> detailCreator);
	
}
