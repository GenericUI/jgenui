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

/**
 *
 * @author katelyn
 */
public class Rect extends SimpleShape {
	
	public final Size size;
	public final Point topLeft;
	public Rect(float x, float y, float w, float h) {
		topLeft = new Point(x, y);
		size = new Size(w, h);
	}
	
	public Rect(Point pos, Size size) {
		topLeft = pos.clone();
		this.size = size.clone();
	}
	
	public Rect(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft.clone();
		size = new Size(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
	}
	
	public Rect() {
		this(0, 0, 0, 0);
	}
	
	public Rect(Rect other) {
		topLeft = other.topLeft.clone();
		size = other.size.clone();
	}
	
	public Rect plus(Point p) {
		topLeft.plus(p);
		return this;
	}
	
	public Rect minus(Point p) {
		topLeft.minus(p);
		return this;
	}

	@Override
	public Point point(Direction dir) {
		switch(dir) {
			case TopLeft:
				return topLeft.clone();
			case TopCenter:
				return new Point(topLeft.x + size.w/2, topLeft.y);
			case TopRight:
				return new Point(topLeft.x + size.w, topLeft.y);
				
			case MiddleLeft:
				return new Point(topLeft.x, topLeft.y + size.h/2);
			case MiddleCenter:
				return new Point(topLeft.x + size.w/2, topLeft.y + size.h/2);
			case MiddleRight:
				return new Point(topLeft.x + size.w, topLeft.y + size.h/2);
				
			case BottomLeft:
				return new Point(topLeft.x, topLeft.y + size.h);
			case BottomCenter:
				return new Point(topLeft.x + size.w/2, topLeft.y + size.h);
			case BottomRight:
				return new Point(topLeft.x + size.w, topLeft.y + size.h);
		}
		return null;
	}

	@Override
	public Rect bounds() {
		return new Rect(this);
	}

	@Override
	public Size size() {
		return size.clone();
	}
	
	public Point[] points() {
		return new Point[]{topLeft.clone(), new Point(topLeft.x+size.w, topLeft.y+size.h)}; 
	}
	
}
