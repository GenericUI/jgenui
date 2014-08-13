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

package net.nexustools.gui.impl;

import net.nexustools.concurrent.BaseAccessor;
import net.nexustools.data.Storage;
import net.nexustools.event.EventListenerRedirect;
import net.nexustools.event.FocusListener;
import net.nexustools.event.VisibilityListener;
import net.nexustools.gui.event.MoveListener;
import net.nexustools.gui.event.OnscreenListener;
import net.nexustools.gui.event.SizeListener;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.LayoutObject;
import net.nexustools.gui.platform.GUIPlatform;
import net.nexustools.gui.render.Renderable;
import net.nexustools.gui.render.Renderer;
import net.nexustools.gui.style.Style;
import net.nexustools.gui.style.StyleSheet;

/**
 *
 * @author katelyn
 */
public interface Widget extends Renderable, LayoutObject {
	
	public String id();
	public void setID(String name);
	
	public String tag();
	public void setTag(String name);
	
	public boolean isVisible();
	public boolean isOnscreen();
	public void setVisible(boolean visible);
	
	public Menu contextMenu();
	public void setContextMenu(Menu menu);
	
	// Styling
	public void addClass(String clazz);
	public boolean hasClass(String clazz);
	public void removeClass(String clazz);
	
	// Events
	public void addMoveListener(MoveListener<Widget> moveListener);
	public void addSizeListener(SizeListener<Widget> sizeListener);
	public void addVisibilityListener(VisibilityListener<Widget> visibilityListener);
	public void addOnscreenListener(OnscreenListener<Widget> onscreenListener);
	public void addFocusListener(FocusListener<Widget> focusListener);
	
	public void removeMoveListener(MoveListener<Widget> moveListener);
	public void removeSizeListener(SizeListener<Widget> sizeListener);
	public void removeVisibilityListener(VisibilityListener<Widget> visibilityListener);
	public void removeOnscreenListener(OnscreenListener<Widget> onscreenListener);
	public void removeFocusListener(FocusListener<Widget> focusListener);
	
	public void uninstallRedirect(EventListenerRedirect<Widget> redirect);
	public void installRedirect(EventListenerRedirect<Widget> redirect);
	
	// Cache Boundaries
	public Rect visibleBounds();
	public Rect topBounds();
	
	public void setMinimumSize(Size size);
	public void setMaximumSize(Size size);
	public void setPreferredSize(Size prefSize);
	
	// Heirarchy
	public ContentHolder container();
	public Window topLevel();
	public Body body();
	
	// Rendering
	public Renderer renderer();
	public Renderer defaultRenderer();
	public void setRenderer(Renderer renderer);
	
	public StyleSheet activeStyleSheet();
	
	public boolean enabled();
	public void setEnabled(boolean enabled);
	
	/**
	 * Gets the style object associated with this Widget.
	 * 
	 * Each widget may implement its own version of the style object,
	 * with support for a different set of selectors and rules.
	 * So make sure to instanceof with the interface you desire from
	 * the net.nexustools.gui.style package.
	 * 
	 * @return 
	 */
	public Style style();
	
	// Dynamic Properties
	public Storage storage();
	
	// Focus1
	public boolean hasFocus();
	public void requestFocus();
	
	public boolean isFocusable();
	public void setFocusable(boolean focusable);

	public void showMenu(AbstractMenu menu, Point at);
	
	public GUIPlatform platform();
	public Widget effective();
	
}
