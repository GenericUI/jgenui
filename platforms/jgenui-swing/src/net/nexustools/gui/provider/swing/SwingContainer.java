/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import net.nexustools.gui.Container;
import net.nexustools.gui.event.LayoutListener;
import net.nexustools.gui.provider.swing.shared.ContainerImpl;

/**
 *
 * @author katelyn
 */
class SwingContainer extends ContainerImpl<java.awt.Container> implements net.nexustools.gui.Container {

    public class NativeContainer extends JPanel implements ContainerWrap {

        public NativeContainer() {
            setName("Container");
            setLayout(null);
        }
        
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
        
        @Override
        public Container getGenUIContainer() {
            return SwingContainer.this;
        }
        
    }
    
    SwingContainer(SwingPlatform platform) {
        super(platform);
    }
    public SwingContainer() {
        this(SwingPlatform.instance());
    }

    @Override
    protected java.awt.Container create() {
        return new NativeContainer();
    }

}
