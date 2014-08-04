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

import net.nexustools.concurrent.BaseAccessor;
import net.nexustools.concurrent.Reader;
import net.nexustools.concurrent.VoidReader;
import net.nexustools.concurrent.Writer;
import net.nexustools.gui.impl.AbstractContainer;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.platform.Platform;

/**
 *
 * @author katelyn
 */
public abstract class GUIAbstractContainer<W extends AbstractContainer> extends GUIContentHolder<W, Widget> implements AbstractContainer {

	GUIAbstractContainer(W instance, Platform platform) {
		super(instance, platform);
	}
	protected GUIAbstractContainer(Class<? extends AbstractContainer> abstractContainerClass, Platform platform) {
		super(abstractContainerClass, platform);
	}

	public void add(final Widget widget) {
		write(new Writer<W>() {
			@Override
			public void write(final W container) {
				// This unwraps, so the "data" is really "impl"
				((GUIWidget)widget).read(new VoidReader<Widget>() {
					@Override
					public void readV(Widget data) {
						container.add(data);
					}
				});
			}
		});	
	}

	public void remove(final Widget widget) {
		write(new Writer<W>() {
			@Override
			public void write(final W container) {
				// This unwraps, so the "data" is really "impl"
				((GUIWidget)widget).read(new VoidReader<Widget>() {
					@Override
					public void readV(Widget data) {
						container.remove(data);
					}
				});
			}
		});	
	}

	public int childCount() {
		return read(new Reader<Integer, W>() {
			@Override
			public Integer read(AbstractContainer data) {
				return data.childCount();
			}
		});
	}
	
}
