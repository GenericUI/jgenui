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

import net.nexustools.gui.Container;
import net.nexustools.gui.Widget;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;

/**
 *
 * @author katelyn
 */
public class BoxLayout implements Layout {
	
	public static final BoxLayout Vertical = new BoxLayout(Direction.Vertical);
	public static final BoxLayout Horizontal = new BoxLayout(Direction.Horizontal);
	
	private static enum Direction {
		Vertical,
		Horizontal
	}
	
	private final Direction direction;
	private BoxLayout(Direction direction) {
		this.direction = direction;
	}

	public void update(Container container) {
		Size prefFill = calculatePreferredSize(container);
		Size contentSize = container.contentSize();
		float extra;
		switch(direction) {
			case Horizontal:
			{
				float containerHeight = contentSize.h;
				extra = (contentSize.w - prefFill.w) / container.childCount()-1;
				
				float x = 0;
				for(Widget child : container) {
					Size prefSize = child.preferredSize();
					prefSize.w += extra;
					child.setBounds(new Rect(x, 0, prefSize.w, Math.max(child.minimumSize().h, Math.min(containerHeight, child.maximumSize().h))));
					x += prefSize.w;
				}
			}
			break;
			case Vertical:
			{
				float containerWidth = contentSize.w;
				extra = (contentSize.h - prefFill.h) / container.childCount()-1;
				
				float y = 0;
				for(Widget child : container) {
					Size prefSize = child.preferredSize();
					prefSize.h += extra;
					child.setBounds(new Rect(0, y, Math.max(child.minimumSize().w, Math.min(containerWidth, child.maximumSize().w)), prefSize.h));
					y += prefSize.h;
				}
			}
			break;
		}
	}

	public Size calculateMinimumSize(Container container) {
		Size size = new Size();
		switch(direction) {
			case Horizontal:
			{
				for(Widget child : container) {
					Size prefSize = child.minimumSize();
					size.h = Math.max(size.h, prefSize.h);
					size.w += prefSize.w;
				}
			}
			break;
			case Vertical:
			{
				for(Widget child : container) {
					Size prefSize = child.minimumSize();
					size.w = Math.max(size.w, prefSize.w);
					size.h += prefSize.h;
				}
			}
			break;
		}
		return size;
	}

	public Size calculatePreferredSize(Container container) {
		Size size = new Size();
		switch(direction) {
			case Horizontal:
			{
				for(Widget child : container) {
					Size prefSize = child.preferredSize();
					size.h = Math.max(size.h, prefSize.h);
					size.w += prefSize.w;
				}
			}
			break;
			case Vertical:
			{
				for(Widget child : container) {
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
