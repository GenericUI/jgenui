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

import java.util.ListIterator;

/**
 *
 * @author katelyn
 */
public interface Shape extends Iterable<Point> {
	
	public static enum Direction {
		TopLeft,
		TopCenter,
		TopRight,
		
		MiddleLeft,
		MiddleCenter,
		MiddleRight,
		
		BottomLeft,
		BottomCenter,
		BottomRight
	}
	public static final Direction Top = Direction.TopCenter;
	public static final Direction Left = Direction.MiddleLeft;
	public static final Direction Right = Direction.MiddleRight;
	public static final Direction Bottom = Direction.BottomCenter;
	public static final Direction Center = Direction.MiddleCenter;
	
	// Tests
	public boolean contains(Point shape);
	public boolean contains(Shape shape);
	
	public boolean intersects(Shape shape);
	
	// Calculations
	public long distance(Shape other);
	public long distance(Direction directon, Shape other);
	
	// Points
	public Point center();
	public Point topLeft();
	public Point topRight();
	public Point bottomLeft();
	public Point bottomRight();
	public Point point(Direction dir);
	
	// Translations
	public Size size();
	public Rect bounds();
	
	// Mutations
	public Shape combine(Shape shape);
	public Shape subtract(Shape shape);
	public Shape diff(Shape shape);
	public Shape mask(Shape shape);

	// Upgrade to ListIterator
	public ListIterator<Point> iterator();
	
}
