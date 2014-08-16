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

import java.util.Iterator;
import net.nexustools.concurrent.ListAccessor;
import net.nexustools.concurrent.Prop;
import net.nexustools.concurrent.PropList;
import net.nexustools.concurrent.logic.Writer;

/**
 *
 * @author katelyn
 */
public class Menu extends AbstractMenu {
	
	public static class Separator implements MenuItem {}
	
	private final PropList<MenuItem> menuItems = new PropList();
	
	public Prop<String> text = new Prop();
	public Menu() {
	}
	public Menu(String text) {
		this.text.set(text);
	}
	
	public void clear() {
		menuItems.clear();
	}
	
	public void trimSeparators() {
		menuItems.write(new Writer<ListAccessor<MenuItem>>() {
			@Override
			public void write(ListAccessor<MenuItem> data) {
				Iterator<MenuItem> it = data.iterator();
				boolean lastValid = false;
				while(it.hasNext()) {
					MenuItem item = it.next();
					if(item instanceof Separator) {
						if(!lastValid)
							it.remove();
						lastValid = false;
					} else
						lastValid = true;
				}
			}
		});
	}
	
	public void remove(AbstractAction menuItem) {
		menuItems.remove(menuItem);
	}
	
	public void remove(AbstractMenu menu) {
		menuItems.remove(menu);
	}
	
	public void insert(AbstractAction menuItem, int at) {
		menuItems.insert(menuItem, at);
	}
	public void insert(AbstractMenu menu, int at) {
		menuItems.insert(menu, at);
	}
	public void insertSeparator(int at) {
		menuItems.insert(new Separator(), at);
	}
	
	public void add(AbstractAction action) {
		menuItems.push(action);
	}
	public void add(AbstractMenu menu) {
		menuItems.push(menu);
	}
	public void addSeparator() {
		menuItems.push(new Separator());
	}
	
	public void setText(final String text) {
		this.text.set(text);
	}

	@Override
	public String text() {
		return text.get();
	}

	public Iterator<MenuItem> iterator() {
		return menuItems.iterator();
	}
	
}
