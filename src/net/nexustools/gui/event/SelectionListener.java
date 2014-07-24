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
import net.nexustools.gui.List;

/**
 *
 * @author katelyn
 */
public interface SelectionListener extends EventListener {
	
	public static class SelectionEvent<I, Size> extends Event {
		public final I[] selection;
		public final long start;
		public final long stop;
		public SelectionEvent(I[] selection, long start, long stop) {
			this.selection = selection;
			this.start = start;
			this.stop = stop;
		}
	}
	
	public void selectionChanged(SelectionEvent event);
    
}
