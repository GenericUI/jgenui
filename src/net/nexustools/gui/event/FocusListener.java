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

/**
 *
 * @author katelyn
 */
public interface FocusListener extends EventListener {
	
	public static class FocusEvent<Widget> extends Event {
		private final Widget old;
		private final Widget cur;
		public FocusEvent(Widget old, Widget cur) {
			this.old = old;
			this.cur = cur;
		}
		public Widget current() {
			return cur;
		}
		public Widget last() {
			return old;
		}
	}
	
	public void focusLost(FocusEvent event);
	public void focusGained(FocusEvent event);
	
}
