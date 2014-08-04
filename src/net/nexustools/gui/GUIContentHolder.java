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

import java.util.Iterator;
import java.util.ListIterator;
import net.nexustools.concurrent.Reader;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.impl.ContentHolder;
import net.nexustools.gui.platform.Platform;

/**
 *
 * @author katelyn
 */
public class GUIContentHolder<W extends ContentHolder, T> extends GUIWidget<W> implements ContentHolder<T> {

	GUIContentHolder(W instance, Platform platform) {
		super(instance, platform);
	}
	public static interface ContentIterator<T> {
		public void iterate(ListIterator<T> it);
	}

	protected GUIContentHolder(Class<? extends ContentHolder> contentHolderClass, Platform platform) {
		super(contentHolderClass, platform);
	}
	
	/**
	 * Creates a lock and than runs the content iterator.
	 * Allows advanced modification of the widget within a locked environment.
	 * 
	 * @param it The ContentIterator to use
	 */
	public void iterate(ContentIterator<T> it, boolean write) {
		lockable().lock(write);
		try {
			 it.iterate((ListIterator<T>) directAccessor().listIterator());
		} finally {
			lockable().unlock();
		}
	}
	
	/**
	 * Creates a lock and than runs the content iterator.
	 * Allows advanced modification of the widget within a locked environment.
	 * 
	 * @param it The ContentIterator to use
	 */
	public void iterate(ContentIterator<T> it) {
		iterate(it, false);
	}

	public Rect contentBounds() {
		return read(new Reader<Rect, W>() {
			@Override
			public Rect read(W data) {
				return data.contentBounds();
			}
		});
	}

	public Point contentOffset() {
		return read(new Reader<Point, W>() {
			@Override
			public Point read(W data) {
				return data.contentOffset();
			}
		});
	}

	public Size contentSize() {
		return read(new Reader<Size, W>() {
			@Override
			public Size read(W data) {
				return data.contentSize();
			}
		});
	}

	public Iterator iterator() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
