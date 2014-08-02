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
public class Size {
	
	public float w;
	public float h;
	
	public Size() {
		this.w = 0;
		this.h = 0;
	}
	public Size(Size size) {
		this(size.w, size.h);
	}
	public Size(float w, float h) {
		this.w = w;
		this.h = h;
	}
	
	public void set(float x, float y) {
		this.w = x;
		this.h = y;
	}
	
	public void set(Size other) {
		set(other.w, other.h);
	}
	
	public double distance(Size other) {
		return Math.abs(other.w - w) + Math.abs(other.h - h);
	}
	
	public Size clone() {
		return new Size(this);
	}

	@Override
	public String toString() {
		return "Size[" + w + ", " + h + "]";
	}

	public Size max(Size other) {
		return new Size(Math.max(w, other.w),
						Math.max(h, other.h));
	}

	public boolean isCollapsed() {
		// TODO: Scale this a bit based off the DPI
		return w <= 0 || h <= 0;
	}
	
}
