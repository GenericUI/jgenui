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

import net.nexustools.gui.err.UnsupportedPlatformOperation;

/**
 *
 * @author katelyn
 */
public interface Body extends Window {
	
	/**
	 * 
	 * @return The widget representing the Body's title, null if not supported by platform
	 */
	public Widget titleWidget();
	
	/**
	 * Sets the Title Widget of this Body to something custom.
	 * 
	 * @param widget
	 * @throws UnsupportedPlatformOperation If the underlying platform does not allow modifying the title widget, such as on most Desktop platforms.
	 */
	public void setTitleWidget(Widget widget) throws UnsupportedPlatformOperation;
	
	public void addAction(Action action);
	public void insertAction(Action action, Action after);
	public void removeAction(Action action);
	public void clearActions();
	
}
