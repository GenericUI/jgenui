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
public class Point {
	
	public float x;
	public float y;
	
	public Point() {}
	public Point(float x, float y) {
		set(x, y);
	}
	public Point(Point p) {
		set(p);
	}
	
	public final void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public final void set(Point other) {
		set(other.x, other.y);
	}
	
	public final double distance(Point other) {
		return Math.abs(other.x - x) + Math.abs(other.y - y);
	}
	
	public Vec2f toVector2f() {
		return new Vec2f(this);
	}
	
}
