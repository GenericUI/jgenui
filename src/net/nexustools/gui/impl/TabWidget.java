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
public interface TabWidget extends ContentHolder<Pair<Widget, String>> {
	
	/**
	 * Adds a new tab to this widget.
	 * 
	 * @param content Content of this tab
	 * @param title Title to give this tab
	 */
	public void add(Widget content, String title);
	
	/**
	 * Adds a new tab to this widget, replacing an existing tab if possible.
	 * 
	 * @param content Content to replace with
	 * @param title Title to replace
	 */
	public void addUnique(Widget content, String title);
	
	/**
	 * Adds a new tab to this widget, replacing an existing tab if desired.
	 * 
	 * @param content Content to add/replace with
	 * @param title Title to replace
	 * @param replace Whether or not to replace
	 * @return True if this content was added (or replace is true). False otherwise
	 */
	public boolean addUnique(Widget content, String title, boolean replace);
	
	/**
	 * Updates a tab's title by its content.
	 * This ensures the correct tab is effected, does nothing if the content doesn't exist.
	 * 
	 * @param content Content to look for
	 * @param title Title to replace with
	 */
	public void updateTitle(Widget content, String title);
	
	/**
	 * Removes a tab by its content.
	 * 
	 * @param content 
	 */
	public void remove(Widget content);
	
	/**
	 * Removes a tab by its title.
	 * 
	 * @param title 
	 */
	public void remove(String title);
	
}
