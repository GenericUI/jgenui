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

package net.nexustools.gui;

import net.nexustools.gui.event.EventListenerRedirect;
import net.nexustools.gui.event.FocusListener;
import net.nexustools.gui.event.MoveListener;
import net.nexustools.gui.event.SizeListener;
import net.nexustools.gui.event.VisibilityListener;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.LayoutObject;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.render.Renderable;
import net.nexustools.gui.render.Renderer;
import net.nexustools.gui.render.Style;
import net.nexustools.gui.render.StyleSheet;

/**
 *
 * @author katelyn
 */
public interface Widget extends Renderable, LayoutObject, Base {
	
	public String tag();
	public void setTag(String name);
	
	public boolean isVisible();
	public boolean isOnscreen();
	public void setVisible(boolean visible);
	
	public Menu contextMenu();
	public void setContextMenu(Menu menu);
	
	public void show();
	public void hide();
	
	// Styling
	public void addClass(String clazz);
	public boolean hasClass(String clazz);
	public void removeClass(String clazz);
	
	// Events
	public void addMoveListener(MoveListener<Widget> moveListener);
	public void addSizeListener(SizeListener<Widget> sizeListener);
	public void addVisibilityListener(VisibilityListener<Widget> visibilityListener);
	public void addFocusListener(FocusListener<Widget> focusListener);
	
	public void removeMoveListener(MoveListener<Widget> moveListener);
	public void removeSizeListener(SizeListener<Widget> sizeListener);
	public void removeVisibilityListener(VisibilityListener<Widget> visibilityListener);
	public void removeFocusListener(FocusListener<Widget> focusListener);
	
	public void uninstallRedirect(EventListenerRedirect<Widget> redirect);
	public void installRedirect(EventListenerRedirect<Widget> redirect);
	
	// Boundaries and Shape
	public Point pos();
	public Size size();
	public Shape shape();
	public Rect bounds();
	public Rect visibleBounds();
	public Rect topBounds();
	
	public void move(Point pos);
	public void resize(Size size);
	public void setBounds(Rect geom);
	
	public Size minimumSize();
	public void setMinimumSize(Size size);
	
	public Size maximumSize();
	public void setMaximumSize(Size size);
	
	// Heirarchy
	public Container container();
	public Container topLevel();
	
	// Rendering
	public Renderer renderer();
	public Renderer defaultRenderer();
	public void setRenderer(Renderer renderer);
	
	public StyleSheet activeStyleSheet();
	
	public boolean enabled();
	public void setEnabled(boolean enabled);
	
	public void setPreferredSize(final Size prefSize);
	public Size preferredSize();
	
	public Style style();
	
	// Implementation
	public Platform platform();
	
	// Focus1
	public boolean hasFocus();
	public void requestFocus();
	
	public boolean isFocusable();
	public void setFocusable(boolean focusable);

	public void showMenu(AbstractMenu menu, Point at);
	
	public Object _n();
	
}
