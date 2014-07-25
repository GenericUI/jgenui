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
	
	private final Point[] points;
	public Rect(float x, float y, float w, float h) {
		points = new Point[]{new Point(x, y), new Point(x+w,y+h)};
	}
	
	public Rect(Point pos, Size size) {
		points = new Point[]{pos.clone(), new Point(pos.x + size.w, pos.y + size.h)};
	}
	
	public Rect(Point topLeft, Point bottomRight) {
		points = new Point[]{topLeft.clone(), bottomRight.clone()};
	}
	
	public Rect() {
		this(0, 0, 0, 0);
	}
	
	public Rect(Rect other) {
		points = new Point[]{new Point(other.topLeft()),
			new Point(other.bottomRight())};
	}

	@Override
	public Point point(Direction dir) {
		switch(dir) {
			case TopLeft:
				return new Point(points[0].x, points[0].y);
			case TopCenter:
				return new Point(points[1].x-points[0].x, points[0].y);
			case TopRight:
				return new Point(points[1].x, points[0].y);
				
			case MiddleLeft:
				return new Point(points[0].x, points[1].y-points[0].y);
			case MiddleCenter:
				return new Point(points[1].x-points[0].x, points[1].y-points[0].y);
			case MiddleRight:
				return new Point(points[1].x, points[1].y-points[0].y);
				
			case BottomLeft:
				return new Point(points[0].x, points[1].y);
			case BottomCenter:
				return new Point(points[1].x-points[0].x, points[1].y);
			case BottomRight:
				return new Point(points[1].x, points[1].y);
		}
		return null;
	}

	@Override
	public Rect bounds() {
		return new Rect(this);
	}

	@Override
	public Size size() {
		return new Size(Math.abs(points[1].x - points[0].x),
				Math.abs(points[1].y - points[0].y));
	}
	
	public Point[] points() {
		return points; 
	}
	
}
