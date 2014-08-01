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

	public void performLayout(Iterable<LayoutObject> container, Size prefFill, Size contentSize, int count) {
		float extra;
		switch(direction) {
			case Horizontal:
			{
				float containerHeight = contentSize.h;
				boolean expand = contentSize.w >= prefFill.w;
				if(expand)
					extra = (contentSize.w - prefFill.w) / count-1;
				else
					extra = contentSize.w / prefFill.w;
				
				float x = 0;
				for(LayoutObject child : container) {
					Size newSize;
					if(extra > 0) {
						newSize = child.preferredSize();
						newSize.w += extra;
					} else {
						newSize = child.minimumSize();
						//newSize.w += (child.preferredSize().w - newSize.w)*extra;
					}
					child.setBounds(new Rect(x, 0, newSize.w, containerHeight));
					x += newSize.w;
				}
			}
			break;
			case Vertical:
			{
				float containerWidth = contentSize.w;
				boolean expand = contentSize.h >= prefFill.h;
				if(expand)
					extra = (contentSize.h - prefFill.h) / count-1;
				else {
					extra = contentSize.h / prefFill.h;
					System.out.println("Shrink: " + extra);
				}
				
				float y = 0;
				for(LayoutObject child : container) {
					Size newSize;
					if(expand) {
						newSize = child.preferredSize();
						newSize.h += extra;
					} else {
						newSize = child.minimumSize();
						newSize.h += (child.preferredSize().h - newSize.h)*extra;
					}
					child.setBounds(new Rect(0, y, containerWidth, newSize.h));
					y += newSize.h;
				}
			}
			break;
		}
	}

	public Size calculateMinimumSize(Iterable<LayoutObject> container) {
		Size size = new Size();
		switch(direction) {
			case Horizontal:
			{
				for(LayoutObject child : container) {
					Size prefSize = child.minimumSize();
					size.h = Math.max(size.h, prefSize.h);
					size.w += prefSize.w;
				}
			}
			break;
			case Vertical:
			{
				for(LayoutObject child : container) {
					Size prefSize = child.minimumSize();
					size.w = Math.max(size.w, prefSize.w);
					size.h += prefSize.h;
				}
			}
			break;
		}
		return size;
	}

	public Size calculatePreferredSize(Iterable<LayoutObject> container) {
		Size size = new Size();
		switch(direction) {
			case Horizontal:
			{
				for(LayoutObject child : container) {
					Size prefSize = child.preferredSize();
					size.h = Math.max(size.h, prefSize.h);
					size.w += prefSize.w;
				}
			}
			break;
			case Vertical:
			{
				for(LayoutObject child : container) {
					Size prefSize = child.preferredSize();
					size.w = Math.max(size.w, prefSize.w);
					size.h += prefSize.h;
				}
			}
			break;
		}
		return size;
	}
	
}