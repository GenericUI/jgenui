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

import java.util.HashMap;
import java.util.Iterator;
import net.nexustools.concurrent.Prop;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.render.Painter;
import net.nexustools.gui.render.Renderable;
import net.nexustools.gui.render.Renderer;

/**
 *
 * @author katelyn
 */
public class Style implements Renderer, Iterable<StyleRule> {
	
	public static interface Applicator<S> {
		public boolean apply(StyleRule rule, S style);
	}
	
	private static HashMap<Class<?>, Applicator<?>> applicators = new HashMap();
	public static <S> void installApplicator(Class<S> baseClazz, Applicator<S> applicator) {
		applicators.put(baseClazz, applicator);
	}
	
	public static class Brush {
	}
	
	public static class Border {
		public Integer gaps;
	}
	
	private final Prop<Brush> brush = new Prop();
	private final Prop<Border> border = new Prop();
	
	public boolean applyRule(StyleRule rule) {
		return false;
	}
	
	public Iterable<StyleRule> iterate() {
		return null;
	}
	
	public static Style createFor(Widget widget) {
		return null;
	}
	
	public void render(Renderable widget, Painter painter) {
		//compiledRenderer.get().render(widget, painter);
	}

	public Iterator<StyleRule> iterator() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
