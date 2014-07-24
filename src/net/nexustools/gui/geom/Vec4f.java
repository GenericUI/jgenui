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
public class Vec4f extends Vec3f {
	
	public float w;
	
	public Vec4f() {}
	public Vec4f(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}
	public Vec4f(Vec3f v) {
		super(v);
	}
	public Vec4f(Point p) {
		super(p);
	}
	
	public void set(float x, float y, float z, float w) {
		set(x, y, z);
		this.w = w;
	}
	
	public void set(Vec4f other) {
		set((Vec3f)other);
		this.w = other.w;
	}
	
}
