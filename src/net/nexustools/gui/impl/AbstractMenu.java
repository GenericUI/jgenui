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

import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Size;
import net.nexustools.concurrent.ReadWriteLock;

/**
 *
 * @author katelyn
 */
public abstract class AbstractMenu implements Iterable<MenuItem>, MenuItem, Base {
	
	private Shortcut shortcut;
	public void show(Widget from, Point at) {
		from.showMenu(this, at);
	}
	public void show(Widget from) {
		Size size = from.size();
		show(from, new Point(size.w, size.h));
	}
	
	public Shortcut getShortcut() {
		return null;
	}
	public void setShortcut(Shortcut shortcut) {
		
	}
	
	public abstract String text();
	
}
