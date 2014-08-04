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

package net.nexustools.gui.layout;

import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;

/**
 *
 * @author katelyn
 */
public class BoxLayout implements Layout<LayoutObject> {
	
	public static final BoxLayout Vertical = new BoxLayout(Direction.Vertical);
	public static final BoxLayout Horizontal = new BoxLayout(Direction.Horizontal);
	
	private static enum Direction {
		Vertical,
		Horizontal
	}
	
	private final Direction direction;
	protected BoxLayout(Direction direction) {
		this.direction = direction;
	}

	public SizeConstraints calculateConstraints(Iterable<LayoutObject> container) {
		SizeConstraints sizeConstraints = new SizeConstraints(new Size(), new Size(), new Size());
		
		switch(direction) {
			case Horizontal:
			{
				for(LayoutObject child : container) {
					Size size = child.minimumSize();
					sizeConstraints.min.h = Math.max(sizeConstraints.min.h, size.h);
					sizeConstraints.min.w += size.w;
					
					size = child.maximumSize();
					sizeConstraints.max.h = Math.max(sizeConstraints.max.h, size.h);
					sizeConstraints.max.w += size.w;
					
					size = child.preferredSize();
					sizeConstraints.pref.h = Math.max(sizeConstraints.pref.h, size.h);
					sizeConstraints.pref.w += size.w;
				}
			}
			break;
			case Vertical:
			{
				for(LayoutObject child : container) {
					Size size = child.minimumSize();
					sizeConstraints.min.w = Math.max(sizeConstraints.min.w, size.w);
					sizeConstraints.min.h += size.h;
					
					size = child.maximumSize();
					sizeConstraints.max.w = Math.max(sizeConstraints.max.w, size.w);
					sizeConstraints.max.h += size.h;
					
					size = child.preferredSize();
					sizeConstraints.pref.w = Math.max(sizeConstraints.pref.w, size.w);
					sizeConstraints.pref.h += size.h;
				}
			}
			break;
		}
		return sizeConstraints;
	}

	public void performLayout(Iterable<LayoutObject> container, SizeConstraints constraints, Size contentSize, int count) {
		switch(direction) {
			case Horizontal:
			{
				float containerHeight = contentSize.h;
				
				float x = 0;
				for(LayoutObject child : container) {
					Size newSize = child.minimumSize();
					child.updateBounds(new Rect(x, 0, newSize.w, containerHeight));
					x += newSize.w;
				}
			}
			break;
			case Vertical:
			{
				float containerWidth = contentSize.w;
				
				float y = 0;
				for(LayoutObject child : container) {
					Size newSize = child.minimumSize();
					child.updateBounds(new Rect(0, y, containerWidth, newSize.h));
					y += newSize.h;
				}
			}
			break;
		}
	}
	
}