/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextField;
import net.nexustools.gui.StringInput;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingStringInput extends WidgetImpl<JTextField> implements StringInput {

    public SwingStringInput() {
        super(SwingPlatform.instance());
    }
    
    private class Native extends JTextField {
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    @Override
    protected JTextField create() {
        return new Native();
    }

    @Override
    public String template() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTemplate(String template) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValue(final String value) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setText(value);
            }
        });
    }

    @Override
    public String value() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.getText();
            }
        });
    }
    
}
