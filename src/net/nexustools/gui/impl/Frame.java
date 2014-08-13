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

/**
 *
 * @author katelyn
 */
public interface Frame extends Container {
	
	public String title();
	public void setTitle(String title);
	
	public boolean raisedBorder();
	public void setRaisedBorder(boolean raised);
	
	/**
	 * Clears this window and sets a widget that will always fill the content area.
	 * 
	 * @param mainWidget The widget to act as a mainWidget
	 */
	public void setMainWidget(Widget mainWidget);
	public Widget mainWidget();
	
}
