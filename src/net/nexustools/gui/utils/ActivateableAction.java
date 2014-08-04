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

package net.nexustools.gui.utils;

import net.nexustools.gui.impl.AbstractAction;
import net.nexustools.gui.impl.Activateable;

/**
 *
 * @author katelyn
 */
public class ActivateableAction extends AbstractAction {
	
	private final Activateable deligate;
	public ActivateableAction(Activateable deligate) {
		this.deligate = deligate;
	}

	@Override
	public String text() {
		return getClass().getName();
	}

	public boolean selectable() {
		return deligate.selectable();
	}

	public boolean isSelected() {
		return deligate.isSelected();
	}
	
}
