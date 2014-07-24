/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import net.nexustools.gui.provider.swing.shared.ContainerImpl;
import javax.swing.JPanel;

/**
 *
 * @author katelyn
 */
class SwingContainer extends ContainerImpl<JPanel> implements net.nexustools.gui.Container {

    SwingContainer(SwingPlatform platform) {
        super(platform);
    }
    public SwingContainer() {
        this(SwingPlatform.instance());
    }

    @Override
    protected JPanel create() {
        return new JPanel() {
            @Override
            public void paint(Graphics g) {
                if(!customRender((Graphics2D)g))
                    super.paint(g);
            }
        };
    }

}
