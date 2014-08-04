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

import net.nexustools.gui.impl.Frame;
import net.nexustools.gui.impl.Menu;
import net.nexustools.gui.impl.Toolbar;
import net.nexustools.gui.impl.Window;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.render.StyleSheet;

/**
 *
 * @author katelyn
 */
public class GUIWindow<W extends Window> extends GUIFrame<W> implements Window {

	GUIWindow(W instance, Platform platform) {
		super(instance, platform);
	}
	protected GUIWindow(Class<? extends Window> windowClass, Platform platform) {
		super(windowClass, platform);
	}
	
	public Menu menu(String pos) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void addMenu(String pos, Menu menu) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void addToolbar(Toolbar toolbar) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void removeToolbar(Toolbar toolbar) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public StyleSheet styleSheet() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void setStyleSheet(StyleSheet styleSheet) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
