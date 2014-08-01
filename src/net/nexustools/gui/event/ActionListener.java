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

package net.nexustools.gui.event;

import java.util.EventListener;
import net.nexustools.gui.Activateable;
import net.nexustools.event.Event;

/**
 *
 * @author katelyn
 */
public interface ActionListener<S extends Activateable> extends EventListener {
	
	public static class ActionEvent<S extends Activateable> extends Event<S> {
		public ActionEvent(S source) {
			super(source);
		}
	}
	
	public void activated(ActionEvent<S> event);
	
}
