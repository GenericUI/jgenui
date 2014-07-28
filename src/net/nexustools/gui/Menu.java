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
import net.nexustools.concurrent.IterationActor;
import net.nexustools.concurrent.Prop;

/**
 *
 * @author katelyn
 */
public class Menu extends AbstractMenu {
	
	public static class Separator implements MenuItem {}
	
	private final net.nexustools.concurrent.ConcurrentList<MenuItem> menuItems = new net.nexustools.concurrent.ConcurrentList();
	
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
		menuItems.iterate(new IterationActor<MenuItem>() {
			@Override
			public void iterate(Iterator<MenuItem> it) {
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
		menuItems.add(at, menuItem);
	}
	public void insert(AbstractMenu menu, int at) {
		menuItems.add(at, menu);
	}
	public void insertSeparator(int at) {
		menuItems.add(at, new Separator());
	}
	
	public void add(AbstractAction action) {
		menuItems.add(action);
	}
	public void add(AbstractMenu menu) {
		menuItems.add(menu);
	}
	public void addSeparator() {
		menuItems.add(new Separator());
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
