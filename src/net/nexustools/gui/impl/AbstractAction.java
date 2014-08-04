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

import net.nexustools.gui.event.ActionListener;
import net.nexustools.gui.event.ActionListener.ActionEvent;
import net.nexustools.event.DefaultEventDispatcher;
import net.nexustools.event.EventDispatcher;
import net.nexustools.runtime.RunQueue;

/**
 *
 * @author katelyn
 */
public abstract class AbstractAction implements Activateable, MenuItem, Base {
	
	private DefaultEventDispatcher<RunQueue, ActionListener, ActionEvent> actionDispatcher = new DefaultEventDispatcher(RunQueue.current());
	public abstract String text();

	public final Shortcut shortcut() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public final void setShortcut(Shortcut shortcut) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public final void addActionListener(ActionListener actionListener) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public final void removeActionListener(ActionListener actionListener) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public void setSelected(boolean selected) {
		throw new UnsupportedOperationException(getClass().getName() + " does not support setSelected");
	}

	public void activate() {
		actionDispatcher.dispatch(new EventDispatcher.Processor<ActionListener, ActionEvent>() {
			public ActionEvent create() {
				return new ActionEvent(AbstractAction.this);
			}
			public void dispatch(ActionListener listener, ActionEvent event) {
				listener.activated(event);
			}
		});
	}

	public final AbstractAction action() {
		return this;
	}

}
