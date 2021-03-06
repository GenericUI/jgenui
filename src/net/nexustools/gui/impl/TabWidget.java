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

import net.nexustools.utils.Pair;

/**
 *
 * @author katelyn
 */
public interface TabWidget extends ContentHolder<Pair<String, Widget>> {
	
	/**
	 * Adds a new tab to this widget.
	 * 
	 * @param content Content of this tab
	 * @param title Title to give this tab
	 */
	public void setTab(String title, Widget content);
	
	/**
	 * Removes a tab by its title.
	 * 
	 * @param title 
	 */
	public void remove(String title);
	
}
