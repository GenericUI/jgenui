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

import net.nexustools.concurrent.Prop;

/**
 *
 * @author katelyn
 */
public final class Action extends AbstractAction {
	
	Prop<String> text = new Prop();
	public Action() {}
	public Action(String text) {
		setText(text);
	}

	public void setText(String text) {
		this.text.set(text);
	}
	
	public String text() {
		return text.get();
	}

	public boolean selectable() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public void setSelectable(boolean selectable) {
		
	}

	@Override
	public void setSelected(boolean selected) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public boolean isSelected() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
