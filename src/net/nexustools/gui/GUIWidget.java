
import net.nexustools.concurrent.BaseAccessor;
import net.nexustools.concurrent.BaseReader;
import net.nexustools.concurrent.BaseWriter;
import net.nexustools.concurrent.ReadWriteConcurrency;
import net.nexustools.concurrent.ReadWriteLock;
import net.nexustools.concurrent.Reader;
import net.nexustools.concurrent.Writer;
import net.nexustools.event.EventListenerRedirect;
import net.nexustools.event.FocusListener;
import net.nexustools.event.VisibilityListener;
import net.nexustools.gui.event.MoveListener;
import net.nexustools.gui.event.OnscreenListener;
import net.nexustools.gui.event.SizeListener;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.impl.AbstractMenu;
import net.nexustools.gui.impl.Base;
import net.nexustools.gui.impl.Body;
import net.nexustools.gui.impl.ContentHolder;
import net.nexustools.gui.impl.Menu;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.impl.Window;
import net.nexustools.gui.layout.SizeConstraints;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.render.Painter;
import net.nexustools.gui.render.Renderer;
import net.nexustools.gui.render.Style;
import net.nexustools.gui.render.StyleSheet;

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

/**
 *
 * @author katelyn
 * @param <W>
 */
public class GUIWidget<W extends Widget> implements Widget, ReadWriteConcurrency<W> {
	
	protected final W impl;
	protected final Platform platform;
	protected final ReadWriteLock<W> lock = new ReadWriteLock();
	public GUIWidget(Class<W> baseClass, Platform platform) {
		impl = platform.create(baseClass);
		this.platform = platform;
	}

	public W directAccessor() {
		return impl;
	}

	public String tag() {
		return read(new Reader<String, W>() {
			@Override
			public String read(W data) {
				return data.tag();
			}
		});
	}

	public void setTag(final String name) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setTag(name);
			}
		});
	}

	public boolean isVisible() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.isVisible();
			}
		});
	}

	public boolean isOnscreen() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.isOnscreen();
			}
		});
	}

	public void setVisible(final boolean visible) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setVisible(visible);
			}
		});
	}

	public Menu contextMenu() {
		return read(new Reader<Menu, W>() {
			@Override
			public Menu read(W data) {
				return data.contextMenu();
			}
		});
	}

	public void setContextMenu(final Menu menu) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setContextMenu(menu);
			}
		});
	}

	public void show() {
		setVisible(true);
	}

	public void hide() {
		setVisible(false);
	}

	public void addClass(final String clazz) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addClass(clazz);
			}
		});
	}

	public boolean hasClass(final String clazz) {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.hasClass(clazz);
			}
		});
	}

	public void removeClass(final String clazz) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeClass(clazz);
			}
		});
	}

	public void addMoveListener(final MoveListener<Widget> moveListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addMoveListener(moveListener);
			}
		});
	}

	public void addSizeListener(final SizeListener<Widget> sizeListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addSizeListener(sizeListener);
			}
		});
	}

	public void addVisibilityListener(final VisibilityListener<Widget> visibilityListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addVisibilityListener(visibilityListener);
			}
		});
	}

	public void addOnscreenListener(final OnscreenListener<Widget> onscreenListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addOnscreenListener(onscreenListener);
			}
		});
	}

	public void addFocusListener(final FocusListener<Widget> focusListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addFocusListener(focusListener);
			}
		});
	}

	public void removeMoveListener(final MoveListener<Widget> moveListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeMoveListener(moveListener);
			}
		});
	}

	public void removeSizeListener(final SizeListener<Widget> sizeListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeSizeListener(sizeListener);
			}
		});
	}

	public void removeVisibilityListener(final VisibilityListener<Widget> visibilityListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeVisibilityListener(visibilityListener);
			}
		});
	}

	public void removeOnscreenListener(final OnscreenListener<Widget> onscreenListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeOnscreenListener(onscreenListener);
			}
		});
	}

	public void removeFocusListener(final FocusListener<Widget> focusListener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeFocusListener(focusListener);
			}
		});
	}

	public void uninstallRedirect(final EventListenerRedirect<Widget> redirect) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.uninstallRedirect(redirect);
			}
		});
	}

	public void installRedirect(final EventListenerRedirect<Widget> redirect) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.installRedirect(redirect);
			}
		});
	}

	public Rect visibleBounds() {
		return read(new Reader<Rect, W>() {
			@Override
			public Rect read(W data) {
				return data.visibleBounds();
			}
		});
	}

	public Rect topBounds() {
		return read(new Reader<Rect, W>() {
			@Override
			public Rect read(W data) {
				return data.topBounds();
			}
		});
	}

	public void setMinimumSize(final Size size) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setMinimumSize(size);
			}
		});
	}

	public void setMaximumSize(final Size size) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setMaximumSize(size);
			}
		});
	}

	public void setPreferredSize(final Size prefSize) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setPreferredSize(prefSize);
			}
		});
	}

	public ContentHolder container() {
		return read(new Reader<ContentHolder, W>() {
			@Override
			public ContentHolder read(W data) {
				return data.container();
			}
		});
	}

	public Window topLevel() {
		return read(new Reader<Window, W>() {
			@Override
			public Window read(W data) {
				return data.topLevel();
			}
		});
	}

	public Body body() {
		return read(new Reader<Body, W>() {
			@Override
			public Body read(W data) {
				return data.body();
			}
		});
	}

	public Renderer renderer() {
		return read(new Reader<Renderer, W>() {
			@Override
			public Renderer read(W data) {
				return data.renderer();
			}
		});
	}

	public Renderer defaultRenderer() {
		return read(new Reader<Renderer, W>() {
			@Override
			public Renderer read(W data) {
				return data.defaultRenderer();
			}
		});
	}

	public void setRenderer(final Renderer renderer) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setRenderer(renderer);
			}
		});
	}

	public StyleSheet activeStyleSheet() {
		return read(new Reader<StyleSheet, W>() {
			@Override
			public StyleSheet read(W data) {
				return data.activeStyleSheet();
			}
		});
	}

	public boolean enabled() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.enabled();
			}
		});
	}

	public void setEnabled(final boolean enabled) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setEnabled(enabled);
			}
		});
	}

	public Style style() {
		return read(new Reader<Style, W>() {
			@Override
			public Style read(W data) {
				return data.style();
			}
		});
	}

	public Platform platform() {
		return platform;
	}

	public boolean hasFocus() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.hasFocus();
			}
		});
	}

	public void requestFocus() {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.requestFocus();
			}
		});
	}

	public boolean isFocusable() {
		return read(new Reader<Boolean, W>() {
			public Boolean read(W data) {
				return data.isFocusable();
			}
		});
	}

	public void setFocusable(final boolean focusable) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setFocusable(focusable);
			}
		});
	}

	public void showMenu(final AbstractMenu menu, final Point at) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.showMenu(menu, at);
			}
		});
	}

	public boolean isTrue() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.isTrue();
			}
		});
	}

	public boolean isset() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.isset();
			}
		});
	}

	public void clear() {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.clear();
			}
		});
	}

	public Rect bounds() {
		return read(new Reader<Rect, W>() {
			@Override
			public Rect read(W data) {
				return data.bounds();
			}
		});
	}

	public void pushRedraw(final Renderer renderer, final Painter.Instruction[] instructions) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.pushRedraw(renderer, instructions);
			}
		});
	}

	public Size size() {
		return read(new Reader<Size, W>() {
			@Override
			public Size read(W data) {
				return data.size();
			}
		});
	}

	public Point pos() {
		return read(new Reader<Point, W>() {
			@Override
			public Point read(W data) {
				return data.pos();
			}
		});
	}

	public Size minimumSize() {
		return read(new Reader<Size, W>() {
			@Override
			public Size read(W data) {
				return data.minimumSize();
			}
		});
	}

	public Size maximumSize() {
		return read(new Reader<Size, W>() {
			@Override
			public Size read(W data) {
				return data.maximumSize();
			}
		});
	}

	public Size preferredSize() {
		return read(new Reader<Size, W>() {
			@Override
			public Size read(W data) {
				return data.preferredSize();
			}
		});
	}

	public SizeConstraints constraints() {
		return read(new Reader<SizeConstraints, W>() {
			@Override
			public SizeConstraints read(W data) {
				return data.constraints();
			}
		});
	}

	public void updateBounds(final Rect rect) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.updateBounds(rect);
			}
		});
	}

	public void resize(final Size size) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.resize(size);
			}
		});
	}

	public void move(final Point pos) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.move(pos);
			}
		});
	}

	public void write(BaseWriter<W> actor) {
		lock.write(impl, actor);
	}

	public <R> R read(BaseReader<R, W> reader) {
		return lock.read(impl, reader);
	}
	
}
