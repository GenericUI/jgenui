/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;
import java.util.Iterator;
import net.nexustools.gui.Widget;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class ContainerImpl<J extends Container> extends WidgetImpl<J> {

    public ContainerImpl(SwingPlatform platform) {
        super(platform);
    }
    
    public void add(Widget widget) {
        component.add((Component)widget.internal());
    }

    public void remove(Widget widget) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Iterator<Widget> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
