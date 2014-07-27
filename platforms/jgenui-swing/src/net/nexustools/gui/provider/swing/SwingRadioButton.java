/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JRadioButton;
import net.nexustools.gui.RadioButton;
import net.nexustools.gui.provider.swing.shared.ToggleButtonImpl;

/**
 *
 * @author katelyn
 */
public class SwingRadioButton extends ToggleButtonImpl<JRadioButton> implements RadioButton {
    
    private class NativeButton extends JRadioButton {
        
        public NativeButton() { // For consistency, buttons can expand infinitely by default
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("RadioButton");
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
        
    }

    public SwingRadioButton() {
        this(SwingPlatform.instance());
    }
    public SwingRadioButton(String text) {
        this();
        setText(text);
    }
    SwingRadioButton(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JRadioButton create() {
        return new NativeButton();
    }
    
}
