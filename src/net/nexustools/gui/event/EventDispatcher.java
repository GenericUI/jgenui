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

package net.nexustools.gui.event;

import java.util.ArrayList;
import java.util.EventListener;
import nexustools.concurrent.ReadWriteLock;
import nexustools.concurrent.ReadWriteLock.ProcessingActor;
import nexustools.runtime.RunQueue;

/**
 *
 * @author katelyn
 */
public abstract class EventDispatcher<R extends RunQueue, L extends EventListener, E extends Event> {
	
	public static interface Processor<L extends EventListener, E extends Event> {
		public E create();
		public void dispatch(L listener, E event);
	}
	
	private L redirect;
	private final R queue;
	private boolean heavyRedirect;
	private final ReadWriteLock lock = new ReadWriteLock();
	private final ArrayList<L> listeners = new ArrayList();
	public EventDispatcher(R queue) {
		this.queue = queue;
	}
	
	public void dispatch(final Processor<L, E> processor) {
		lock.act(new ProcessingActor() {
			@Override
			public Runnable process() {
				if(listeners.size() > 0 || heavyRedirect) {
					if(redirect != null) {
						final L listener = redirect;
						return new Runnable() {
							public void run() {
								processor.dispatch(listener, processor.create());
							}
						};
					} else {
						final ArrayList<L> list = new ArrayList(listeners);
						return new Runnable() {
							public void run() {
								E event = processor.create();
								for(L listener : list) {
									processor.dispatch(listener, event);
								}
							}
						};
					}
				}
				return null;
			}
		});
	}
	
	public void add(final L listener) {
		lock.act(new ReadWriteLock.IfUpgradeWriter() {
			public void perform(ReadWriteLock lock) {
				System.out.println("Added Listener: " + listeners.size());
				if(listeners.isEmpty())
					setCleanup(new Runnable() {
						public void run() {
							connect();
						}
					});
				
				listeners.add(listener);
			}
			public boolean test() {
				return !listeners.contains(listener);
			}
		});
	}
	
	public void remove(final L listener) {
		lock.act(new ReadWriteLock.IfUpgradeWriter() {
			public void perform(ReadWriteLock lock) {
				listeners.remove(listener);
				
				if(listeners.isEmpty())
					setCleanup(new Runnable() {
						public void run() {
							disconnect();
						}
					});
			}
			public boolean test() {
				return listeners.contains(listener);
			}
		});
	}
	
	public void redirect(L listener) {
		redirect(listener, true);
	}
	public void redirect(final L listener, final boolean heavy) {
		lock.act(new ReadWriteLock.UpgradeWriter() {
			public void perform(ReadWriteLock lock) {
				heavyRedirect = listener != null && heavy;
				if(redirect == listener)
					return;
				
				if(redirect == null && heavyRedirect && listeners.isEmpty())
					connect();
				else if(listener == null && listeners.isEmpty())
					disconnect();
				
				redirect = listener;
			}
		});
			
	}
	
	
	public abstract void connect();
	public abstract void disconnect();
	
}
