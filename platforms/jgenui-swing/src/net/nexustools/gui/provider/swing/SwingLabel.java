/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import net.nexustools.gui.provider.swing.shared.WidgetImpl;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import net.nexustools.gui.Label;

/**
 *
 * @author katelyn
 */
public class SwingLabel extends WidgetImpl<JLabel> implements Label {

    public SwingLabel() {
        this(SwingPlatform.instance());
    }
    SwingLabel(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JLabel create() {
        return new JLabel() {
            @Override
            public void paint(Graphics g) {
                if(!customRender((Graphics2D)g))
                    super.paint(g);
            }
        };
    }

    @Override
    public String getText() {
        return component.getText();
    }

    @Override
    public void setText(String text) {
        component.setText(text);
    }
    
}
