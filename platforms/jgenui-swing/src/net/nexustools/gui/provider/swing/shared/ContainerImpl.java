/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import net.nexustools.gui.Widget;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class ContainerImpl<J extends Container> extends WidgetImpl<J> {
    
    public static interface ContainerWrap {
        public net.nexustools.gui.Container getGenUIContainer();
    }
    
    public static class NativeLayout implements LayoutManager {
        
        public final Layout layout;
        public NativeLayout(Layout layout) {
            this.layout = layout;
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
            try {
                System.out.println("preferredLayoutSize");
                Size size = layout.calculatePreferredSize(((ContainerWrap)parent).getGenUIContainer());
                System.out.println(size);
                return new Dimension((int)size.w, (int)size.h);
            } catch(RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            try {
                System.out.println("preferredLayoutSize");
                Size size = layout.calculateMinimumSize(((ContainerWrap)parent).getGenUIContainer());
                System.out.println(size);
                return new Dimension((int)size.w, (int)size.h);
            } catch(RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            layout.update(((ContainerWrap)parent).getGenUIContainer());
        }
        
    }

    private boolean layoutDirty = false;
    private final ArrayList<Widget> children = new ArrayList();
    public ContainerImpl(SwingPlatform platform) {
        super(platform);
    }
    
    public void invalidate() {
        if(layoutDirty)
            return;
        
        layoutDirty = true;
        component.invalidate();
        platform().onIdle(new Runnable() {
            @Override
            public void run() {
                component.validate();
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

    public Iterator<Widget> iterator() {
        return read(new Reader<Iterator<Widget>>() {
            @Override
            public Iterator<Widget> read() {
                return children.iterator();
            }
        });
    }
    
//    public void doLayoutUpdate() {
//        act(updateLayout);
//    }
//    
//    protected void scheduleLayoutUpdate() {
//        System.out.println("Scheduling Layout Update");
//        act(new Runnable() {
//
//            @Override
//            public void run() {
//                layoutDirty = true;
//                if(children.isEmpty() || !isVisible())
//                    return;
//                
//                platform().onIdle(updateLayout);
//            }
//        });
//    }
    
    
//    private SizeListener layoutSizeListener;
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

}
