/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Container;
import net.nexustools.gui.Widget;
import net.nexustools.gui.provider.swing.SwingPlatform;

/**
 *
 * @author katelyn
 */
public abstract class FakeContainerImpl<J extends Container> extends WidgetImpl<J> {

    public FakeContainerImpl(SwingPlatform platform) {
        super(platform);
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        // TODO: Improve to act as an override, and not just to set all the children
        act(new Runnable() {
            @Override
            public void run() {
                setDeep(component);
            }
            public void setDeep(Container parent) {
                for(Component component : parent.getComponents()) {
                    if(component instanceof Container)
                        setDeep((Container)component);
                    else
                        component.setEnabled(enabled);
                }
            }
        });
    }
    
}
