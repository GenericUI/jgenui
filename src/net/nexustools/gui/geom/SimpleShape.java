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

package net.nexustools.gui.geom;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author katelyn
 */
public abstract class SimpleShape extends BasicShape {
	
	static class SimpleShapeIterator implements ListIterator<Point> {
		
		private int pos = -1;
		private final Point[] points;
		public SimpleShapeIterator(Point[] points) {
			this.points = points;
		}

		public boolean hasNext() {
			return pos+1 < points.length;
		}

		public Point next() {
			return points[++pos];
		}

		public void remove() {
			throw new UnsupportedOperationException("SimpleShapes have a set amount of points.");
		}

		public boolean hasPrevious() {
			return pos > 0;
		}

		public Point previous() {
			return points[--pos];
		}

		public int nextIndex() {
			return pos;
		}

		public int previousIndex() {
			return pos-1;
		}

		public void set(Point e) {
			points[pos].set(e);
		}

		public void add(Point e) {
			throw new UnsupportedOperationException("SimpleShapes have a set amount of points.");
		}
	}
	

	public ListIterator<Point> iterator() {
		return new SimpleShapeIterator(points());
	}
	
	protected abstract Point[] points();
	
}
