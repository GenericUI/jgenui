/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JCheckBox;
import net.nexustools.gui.CheckBox;
import net.nexustools.gui.provider.swing.shared.ToggleButtonImpl;

/**
 *
 * @author katelyn
 */
public class SwingCheckBox extends ToggleButtonImpl<JCheckBox> implements CheckBox {
    
    private class NativeButton extends JCheckBox {
        
        public NativeButton() { // For consistency, buttons can expand infinitely by default
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("CheckBox");
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
        
    }

    public SwingCheckBox() {
        this(SwingPlatform.instance());
    }
    public SwingCheckBox(String text) {
        this();
        setText(text);
    }
    SwingCheckBox(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JCheckBox create() {
        return new NativeButton();
    }
    
}