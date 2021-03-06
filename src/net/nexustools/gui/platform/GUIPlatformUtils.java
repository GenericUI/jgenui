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

package net.nexustools.gui.platform;

import net.nexustools.event.ValueListener;
import net.nexustools.gui.impl.ComboBox;
import net.nexustools.gui.impl.SingleInput;
import net.nexustools.gui.event.LAFListener;

/**
 *
 * @author katelyn
 */
public class GUIPlatformUtils {
	
	public static ComboBox createLAFController(final GUIPlatform platform) {
		final ComboBox<String> comboBox = (ComboBox) platform.create(ComboBox.class);
        comboBox.setTemplate("LAF: ####");
		comboBox.setOptions(platform.LAFs());
		comboBox.setValue(platform.LAF());
		platform.addLAFListener(new LAFListener() {
			public void lafChanged(LAFListener.LAFEvent lafEvent) {
				comboBox.setValue(lafEvent.lafName);
			}
			public void lafDiscovered(LAFListener.LAFEvent lafEvent) {}
			public void lafVanished(LAFListener.LAFEvent lafEvent) {}
		});
		comboBox.addValueListener(new ValueListener<String, SingleInput<String>>() {
			public void valueChanged(ValueListener.ValueEvent<String, SingleInput<String>> event) {
				platform.setLAF(event.value);
			}
		});
		return comboBox;
	}
	
}
