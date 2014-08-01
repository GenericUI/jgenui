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

package net.nexustools.gui.render;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import net.nexustools.concurrent.MapAccessor;
import net.nexustools.concurrent.PropList;
import net.nexustools.concurrent.PropMap;
import net.nexustools.concurrent.SoftWriteReader;
import net.nexustools.concurrent.WriteReader;
import net.nexustools.gui.List;
import net.nexustools.io.Stream;
import net.nexustools.utils.Pair;

/**
 * 
 * 
 * @author katelyn
 */
public class StyleSheet {
	
	/**
	 * A element identifier,
	 * consists of a tag, state, and list of values to match.
	 * 
	 * The tag, and values, can be null or "*" implying a "catch-all".
	 */
	public static class ID {
		public final String tag;
		public final String state;
		public final Map<String,String> values;
		
		public ID(String tag, String state, Map<String,String> values) {
			this.tag = tag;
			this.state = state;
			this.values = values;
		}
	}
	
	/**
	 * A element path, consisting of identifiers.
	 * This allows for selecting elements within a specific hierarchy.
	 */
	public static class Path extends ArrayList<ID> {}
	
	public static interface Matcher {
		public boolean matches(Path id);
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
		public boolean matches(Path id) {
			return false;
		}
	}
	
	private final PropList<Pair<Path, Style>> rules = new PropList();
	public StyleSheet(InputStream inStream) {
	}
	public StyleSheet(Stream stream) throws IOException {
		this(stream.createInputStream());
	}
	public StyleSheet(String path) throws IOException, URISyntaxException {
		this(Stream.open(path));
	}
	
	public void inherit(Style style, Matcher matcher) {
		for(Pair<Path, Style> stylePair : rules) {
			if(matcher.matches(stylePair.i))
				style.inherit(stylePair.v);
		}
	}
	
}
