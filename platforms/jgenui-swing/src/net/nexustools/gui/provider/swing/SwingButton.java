/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import net.nexustools.gui.Button;
import net.nexustools.gui.provider.swing.shared.ButtonImpl;

/**
 *
 * @author katelyn
 */
public class SwingButton extends ButtonImpl<JButton> implements Button {
    
    private class NativeButton extends JButton {
        
        public NativeButton() { // For consistency, buttons can expand infinitely by default
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("Button");
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
        
    }

    public SwingButton() {
        this(SwingPlatform.instance());
    }
    public SwingButton(String text) {
        this();
        setText(text);
    }
    SwingButton(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JButton create() {
        return new NativeButton();
    }
    
}
