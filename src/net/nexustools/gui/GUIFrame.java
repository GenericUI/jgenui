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
import net.nexustools.gui.impl.Container;
import net.nexustools.gui.impl.Frame;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.platform.Platform;

/**
 *
 * @author katelyn
 */
public class GUIFrame<W extends Frame> extends GUIContainer<W> implements Frame {

	GUIFrame(W instance, Platform platform) {
		super(instance, platform);
	}
	protected GUIFrame(Class<? extends Frame> frameClass, Platform platform) {
		super(frameClass, platform);
	}
	public GUIFrame(Platform platform) {
		this(Frame.class, platform);
	}
	public GUIFrame() {
		this(Platform.current());
	}
	
	public String title() {
		return read(new Reader<String, W>() {
			@Override
			public String read(W data) {
				return data.title();
			}
		});
	}

	public void setTitle(final String title) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setTitle(title);
			}
		});
	}

	public boolean raisedBorder() {
		return read(new Reader<Boolean, W>() {
			@Override
			public Boolean read(W data) {
				return data.raisedBorder();
			}
		});
	}

	public void setRaisedBorder(final boolean raised) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setRaisedBorder(raised);
			}
		});
	}

	public void setMainWidget(final Widget mainWidget) {
		write(new Writer<W>() {
			@Override
			public void write(W data) {
				data.setMainWidget(mainWidget);
			}
		});
	}
	
}
