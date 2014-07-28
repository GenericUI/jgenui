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
import java.util.ListIterator;
import net.nexustools.concurrent.Prop;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.geom.Size;
import net.nexustools.io.Stream;

/**
 *
 * @author katelyn
 */
public class Pixmap implements Renderable {
	
	public static interface BundleCallback {
		public void sizeFound(Size size);
		public void animationFound(int frames, long[] delays, int loops);
	}
	
	public static class PixmapBundle implements Painter.Instruction {
		public final Pixmap pixmap;
		public final BundleCallback callback;
		public PixmapBundle(Pixmap pixmap, BundleCallback callback) {
			this.pixmap = pixmap;
			this.callback = callback;
		}
	}
	
	private Prop<Stream> stream;
	private Prop<Size> size = new Prop(new Size(16,16));
	public Pixmap(String source) throws IOException, URISyntaxException {
		this(Stream.open(source));
	}
	public Pixmap(Stream source) {
		setSource(source);
	}
	
	public void setSource(String source) throws IOException, URISyntaxException {
		setSource(Stream.open(source));
	}
	
	public void setSource(Stream source) {
		this.stream.set(source);
		size.set(new Size(16,16));
	}

	public Shape shape() {
		return bounds();
	}

	public Rect bounds() {
		return new Rect(new Point(0,0),size.get());
	}

	public void render(Painter p) {
		
	}

	public void optimize(ListIterator<Painter.Instruction> instructions) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void pushRedraw(Painter.Instruction[] instructions) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
