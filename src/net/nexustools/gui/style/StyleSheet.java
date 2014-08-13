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

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Iterator;
import net.nexustools.concurrent.MapAccessor;
import net.nexustools.concurrent.PropList;
import net.nexustools.concurrent.PropMap;
import net.nexustools.concurrent.SoftWriteReader;
import net.nexustools.io.Stream;

/**
 * 
 * 
 * @author katelyn
 */
public class StyleSheet implements Iterable<StyleBlock> {
	
	public static interface Matcher {
		public boolean matches(ID.Path id);
	}
	
	/**
	 * Generates a selector based off a CSS like selector query.
	 */
	public static class Selector implements Matcher {
		private static final PropMap<String, Selector> compiledSelectors = new PropMap(PropMap.Type.WeakHashMap);
		public static Selector compile(final String selector) {
			return compiledSelectors.read(new SoftWriteReader<Selector, MapAccessor<String, Selector>>() {

				@Override
				public Selector soft(MapAccessor<String, Selector> data) {
					return data.get(selector);
				}

				@Override
				public Selector read(MapAccessor<String, Selector> data) {
					Selector newSelector = new Selector(selector);
					data.put(selector, newSelector);
					return newSelector;
				}

				@Override
				public boolean test(MapAccessor<String, Selector> against) {
					return !against.has(selector);
				}
				
			});
		}
		protected Selector(String selector) {
			
		}
		public boolean matches(ID.Path id) {
			return false;
		}
	}
	
	private final PropList<StyleBlock> rules = new PropList();
	public StyleSheet(InputStream inStream) {
	}
	public StyleSheet(Stream stream) throws IOException {
		this(stream.createInputStream());
	}
	public StyleSheet(String path) throws IOException, URISyntaxException {
		this(Stream.open(path));
	}

	public Iterator<StyleBlock> iterator() {
		return rules.iterator();
	}
	
}
