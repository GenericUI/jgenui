/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import net.nexustools.gui.provider.swing.SwingPlatform;
import net.nexustools.gui.provider.swing.shared.FakeContainerImpl.FakeContainer;

/**
 *
 * @author katelyn
 */
public abstract class FakeContainerImpl extends AbstractContainerImpl<FakeContainer> {
    
    protected class FakeContainer extends JPanel {
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    public FakeContainerImpl(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected FakeContainer create() {
        return new FakeContainer();
    }
    
}
