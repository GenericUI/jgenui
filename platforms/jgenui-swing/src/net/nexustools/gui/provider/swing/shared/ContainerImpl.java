/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;
import net.nexustools.gui.Widget;
import net.nexustools.gui.event.DefaultEventDispatcher;
import net.nexustools.gui.event.EventDispatcher;
import net.nexustools.gui.event.LayoutListener;
import net.nexustools.gui.event.LayoutListener.LayoutEvent;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class ContainerImpl<J extends Container> extends FakeContainerImpl<J> {
    
    public static interface ContainerWrap {
        public net.nexustools.gui.Container getGenUIContainer();
    }
    
    public static class NativeLayout implements LayoutManager2 {
        
        public static class Cache {
            Dimension prefSize;
            Dimension minSize;
        }
        
        public final Layout layout;
        public static final Dimension maxSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        public final WeakHashMap<Component, Cache> cacheMap = new WeakHashMap();
        public NativeLayout(Layout layout) {
            this.layout = layout;
        }

        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            System.out.println("addLayoutComponent");
            System.out.println(comp);
            System.out.println(constraints);
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
            System.out.println("addLayoutComponent");
            System.out.println(comp);
            System.out.println(name);
        }

        @Override
        public void removeLayoutComponent(Component comp) {
            System.out.println("removeLayoutComponent");
            System.out.println(comp);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Cache cache = cacheMap.get(parent);
            if(cache == null) {
                cache = new Cache();
                cacheMap.put(parent, cache);
            } else if(cache.prefSize != null)
                return cache.prefSize;
            
            try {
                Size size = layout.calculatePreferredSize(((ContainerWrap)parent).getGenUIContainer());
                System.out.println(size);
                Insets insets = parent.getInsets();
                return cache.prefSize = new Dimension((int)size.w+insets.left+insets.right, (int)size.h+insets.top+insets.bottom);
            } catch(RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            Cache cache = cacheMap.get(parent);
            if(cache == null) {
                cache = new Cache();
                cacheMap.put(parent, cache);
            } else if(cache.minSize != null)
                return cache.minSize;
            
            try {
                System.out.println("preferredLayoutSize");
                Size size = layout.calculateMinimumSize(((ContainerWrap)parent).getGenUIContainer());
                System.out.println(size);
                Insets insets = parent.getInsets();
                return cache.prefSize = new Dimension((int)size.w+insets.left+insets.right, (int)size.h+insets.top+insets.bottom);
            } catch(RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            final ContainerImpl containerImpl = (ContainerImpl)((ContainerWrap)parent).getGenUIContainer();
            containerImpl.layoutDispatcher.dispatch(new EventDispatcher.Processor<LayoutListener, LayoutEvent>() {
                @Override
                public LayoutEvent create() {
                    return new LayoutEvent((Widget)containerImpl);
                }
                @Override
                public void dispatch(LayoutListener listener, LayoutEvent event) {
                    listener.layoutStart(event);
                }
            });
            layout.update(((ContainerWrap)parent).getGenUIContainer());
            containerImpl.layoutDispatcher.dispatch(new EventDispatcher.Processor<LayoutListener, LayoutEvent>() {
                @Override
                public LayoutEvent create() {
                    return new LayoutEvent((Widget)containerImpl);
                }
                @Override
                public void dispatch(LayoutListener listener, LayoutEvent event) {
                    listener.layoutFinished(event);
                }
            });
        }

        @Override
        public Dimension maximumLayoutSize(Container target) {
            return maxSize;
        }

        @Override
        public float getLayoutAlignmentX(Container target) {
            return 0; // Not sure what these are for
        }

        @Override
        public float getLayoutAlignmentY(Container target) {
            return 0; // Not sure what these are for
        }

        @Override
        public void invalidateLayout(Container target) {
            cacheMap.remove(target);
            ContainerImpl container = (ContainerImpl)((ContainerWrap)target).getGenUIContainer();
            container = (ContainerImpl)container.container();
            if(container != null)
                container.invalidate();
        }
        
    }

    private final ArrayList<Widget> children = new ArrayList();
    public ContainerImpl(SwingPlatform platform) {
        super(platform);
    }
    
    public void invalidate() {
        act(new Runnable() {
            @Override
            public void run() {
                component.invalidate();
                ContainerImpl parent = (ContainerImpl) container();
                if(parent != null)
                    parent.invalidate();
            }
        });
        
    }
    
    public void add(final Widget widget) {
        act(new Runnable() {
            @Override
            public void run() {
                if(children.contains(widget))
                    return;
                
                children.add(widget);
                component.add((Component)widget.internal());
                invalidate();
            }
        });
    }

    public void remove(final Widget widget) {
        act(new Runnable() {
            @Override
            public void run() {
                if(!children.contains(widget))
                    return;
                
                children.remove(widget);
                component.remove((Component)widget.internal());
                invalidate();
            }
        });
    }
    
    public final EventDispatcher<SwingPlatform, LayoutListener, LayoutEvent> layoutDispatcher = new DefaultEventDispatcher(platform());
    public void addLayoutListener(LayoutListener listener) {
        layoutDispatcher.add(listener);
    }
    public void removeLayoutListener(LayoutListener listener) {
        layoutDispatcher.remove(listener);
    }

    public Iterator<Widget> iterator() {
        return read(new Reader<Iterator<Widget>>() {
            @Override
            public Iterator<Widget> read() {
                return children.iterator();
            }
        });
    }
    
    public void setLayout(final Layout layout) {
        act(new Runnable() {
            @Override
            public void run() {
                if(layout != null) {
                    component.setLayout(new NativeLayout(layout));
                    invalidate();
                } else
                    component.setLayout(null);
                //ContainerImpl.this.layout = layout;
            }
        });
    }

    public Layout layout() {
        return read(new Reader<Layout>() {
            @Override
            public Layout read() {
                LayoutManager manager = component.getLayout();
                if(manager instanceof NativeLayout)
                    return ((NativeLayout)manager).layout;
                return null;
            }
        });
    }
    
    public int childCount() {
        return read(new Reader<Integer>() {
            @Override
            public Integer read() {
                return component.getComponentCount();
            }
        });
    }
    
    public Rect contentBounds() {
        return read(new Reader<Rect>() {

            @Override
            public Rect read() {
                Rect rect = bounds();
                Insets insets = component.getInsets();
                rect.topLeft.x += insets.left;
                rect.topLeft.y += insets.top;
                rect.size.w -= insets.left + insets.right;
                rect.size.h -= insets.top + insets.bottom;
                return rect;
            }
        });
    }
    
    public Point contentOffset() {
        return read(new Reader<Point>() {
            @Override
            public Point read() {
                Point pos = pos();
                Insets insets = component.getInsets();
                pos.x += insets.left;
                pos.y += insets.top;
                return pos;
            }
        });
    }
    
    public Size contentSize() {
        return read(new Reader<Size>() {
            @Override
            public Size read() {
                Size size = size();
                Insets insets = component.getInsets();
                size.w -= insets.left + insets.right;
                size.h -= insets.top + insets.bottom;
                return size;
            }
        });
                
    }

}
