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

package net.nexustools.gui.style;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A Style Selector Identifier.
 * 
 * @author katelyn
 */
public class ID {
	
	public static class Path extends ArrayList<ID> {}
	
	/**
	 * The tag this id references.
	 */
	public final String tag;

	/**
	 * The :states or ::seudo-states this ID selects.
	 */
	public final String[] states;

	/**
	 * The properties this id selects,
	 * 
	 */
	public final Map<String,String> properties;

	public ID(String tag, String[] states, Map<String,String> values) {
		this.tag = tag;
		this.states = states;
		this.properties = values;
	}
	public ID(final String id) {
		this(null, null, new HashMap<String,String>() {
			{
				put("id", id);
			}
		});
	}
	
}
