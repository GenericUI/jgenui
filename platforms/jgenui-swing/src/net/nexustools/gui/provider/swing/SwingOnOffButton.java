/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import net.nexustools.gui.OnOffButton;
import net.nexustools.gui.SingleInput;
import net.nexustools.gui.event.ValueListener;
import net.nexustools.gui.provider.swing.shared.FakeContainerImpl;

/**
 *
 * @author katelyn
 */
public class SwingOnOffButton extends FakeContainerImpl implements OnOffButton {

    public SwingToggleButton on = new SwingToggleButton("On");
    public SwingToggleButton off = new SwingToggleButton("Off");
    public ButtonGroup buttonGroup = new ButtonGroup();
    
    public SwingOnOffButton() {
        super(SwingPlatform.instance());
        component.setLayout(new BoxLayout(component, BoxLayout.X_AXIS));

        on.setTag("On");
        off.setTag("Off");
        buttonGroup.add(on.internal());
        buttonGroup.add(off.internal());
        off.setSelected(true);
        
        add(on);
        add(off);
        setTag("OnOffButton");
    }

    @Override
    public void toggle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addValueListener(ValueListener<SingleInput<Boolean>, Boolean> selectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeValueListener(ValueListener<SingleInput<Boolean>, Boolean> selectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void setValue(Boolean value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean value() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setText(String on, String off) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String offText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String onText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}