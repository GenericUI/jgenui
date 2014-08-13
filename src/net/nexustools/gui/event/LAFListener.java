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
import net.nexustools.event.Event;
import net.nexustools.gui.platform.GUIPlatform;

/**
 *
 * @author katelyn
 */
public interface LAFListener<S extends GUIPlatform> extends EventListener {
	
	public static class LAFEvent<S extends GUIPlatform> extends Event<S> {
		public final String lafName;
		public LAFEvent(String lafName, S source) {
			super(source);
			this.lafName = lafName;
		}
	}
	
	public void lafChanged(LAFEvent<S> lafEvent);
	
	public void lafDiscovered(LAFEvent<S> lafEvent);
	public void lafVanished(LAFEvent<S> lafEvent);
	
}
