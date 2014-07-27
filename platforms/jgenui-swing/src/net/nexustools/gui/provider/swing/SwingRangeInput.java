/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JSpinner;
import net.nexustools.gui.RangeInput;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingRangeInput extends WidgetImpl<JSpinner> implements RangeInput {

    public SwingRangeInput() {
        super(SwingPlatform.instance());
    }
    
    private class Native extends JSpinner {
        public Native() {
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            setName("Input[Type=Number]");
        }
        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
    }

    @Override
    protected JSpinner create() {
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
    public void setValue(final Double value) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setValue(value);
            }
        });
    }

    @Override
    public Double value() {
        return read(new Reader<Double>() {
            @Override
            public Double read() {
                return (Double)component.getValue();
            }
        });
    }

    @Override
    public double step() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStep(double step) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double min() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMin(double min) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double max() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMax(double max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
