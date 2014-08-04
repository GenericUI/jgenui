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
import net.nexustools.gui.err.UnsupportedPlatformOperation;
import net.nexustools.gui.impl.Action;
import net.nexustools.gui.impl.Body;
import net.nexustools.gui.impl.Widget;
import net.nexustools.gui.platform.Platform;

/**
 *
 * @author katelyn
 */
public class GUIBody<W extends Body> extends GUIWindow<W> implements Body {

	GUIBody(W instance, Platform platform) {
		super(instance, platform);
	}
	protected GUIBody(Class<? extends Body> bodyClass, Platform platform) {
		super(bodyClass, platform);
	}
	public GUIBody(Class<? extends Platform> platform) {
		this(Body.class, Platform.byClass(platform));
	}
	public GUIBody(Platform platform) {
		this(Body.class, platform);
	}
	public GUIBody() {
		this(Platform.current());
	}

	public Widget titleWidget() {
		return read(new Reader<Widget, W>() {
			@Override
			public Widget read(W data) {
				return data.titleWidget();
			}
		});
	}

	public void setTitleWidget(Widget widget) throws UnsupportedPlatformOperation {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void addAction(Action action) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public boolean hasAction(Action action) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void insertAction(Action action, int at) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public void removeAction(Action action) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public int indexOfAction(Action action) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	
	
}
