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
public class Vec3f extends Vec2f {
	
	public float z;
	
	public Vec3f() {}
	public Vec3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
	public Vec3f(Vec3f v) {
		super(v);
	}
	public Vec3f(Point p) {
		super(p);
	}
	
	public void set(float x, float y, float z) {
		set(x, y);
		this.z = z;
	}
	
	public void set(Vec3f other) {
		set((Vec2f)other);
		this.z = other.z;
	}
	
}
