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

import net.nexustools.concurrent.Reader;
import net.nexustools.concurrent.Writer;
import net.nexustools.gui.event.LayoutListener;
import net.nexustools.gui.impl.Container;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.platform.Platform;

/**
 *
 * @author katelyn
 */
public class GUIContainer<W extends Container> extends GUIAbstractContainer<W> implements Container {

	GUIContainer(W instance, Platform platform) {
		super(instance, platform);
	}
	protected GUIContainer(Class<? extends Container> containerClass, Platform platform) {
		super(containerClass, platform);
	}
	public GUIContainer(Platform platform) {
		this(Container.class, platform);
	}
	public GUIContainer() {
		this(Platform.current());
	}

	public void addLayoutListener(final LayoutListener listener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.addLayoutListener(listener);
			}
		});
	}

	public void removeLayoutListener(final LayoutListener listener) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.removeLayoutListener(listener);
			}
		});
	}

	public void setLayout(final Layout layout) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setLayout(layout);
			}
		});
	}

	public Layout layout() {
		return read(new Reader<Layout, W>() {
			@Override
			public Layout read(W data) {
				return data.layout();
			}
		});
	}

}
