/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import net.nexustools.gui.Widget;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class AbstractContainerImpl<J extends Container> extends WidgetImpl<J> {

    protected final ArrayList<Widget> children = new ArrayList();
    public AbstractContainerImpl(SwingPlatform platform) {
        super(platform);
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        // TODO: Improve to act as an override, and not just to set all the children
        act(new Runnable() {
            @Override
            public void run() {
                for(Widget child : children)
                    child.setEnabled(enabled);
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

    public Iterator<Widget> iterator() {
        return read(new Reader<Iterator<Widget>>() {
            @Override
            public Iterator<Widget> read() {
                return children.iterator();
            }
        });
    }
    
}