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
public abstract class BasicShape implements Shape {

	public long distance(Shape other) {
		return distance(Center, other);
	}

	public Point center() {
		return point(Center);
	}

	public Point topLeft() {
		return point(Direction.TopLeft);
	}

	public Point topRight() {
		return point(Direction.TopLeft);
	}

	public Point bottomLeft() {
		return point(Direction.TopLeft);
	}

	public Point bottomRight() {
		return point(Direction.TopLeft);
	}

	public Size size() {
		return bounds().size();
	}

	public Shape combine(Shape shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public Shape subtract(Shape shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public Shape diff(Shape shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public Shape mask(Shape shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public boolean contains(Point shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public boolean contains(Shape shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public boolean intersects(Shape shape) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public long distance(Direction directon, Shape other) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public Point point(Direction dir) {
		return bounds().point(dir);
	}

	public Rect bounds() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
