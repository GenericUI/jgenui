/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import net.nexustools.gui.OnOffButton;
import net.nexustools.gui.SingleInput;
import net.nexustools.gui.event.ValueListener;
import net.nexustools.gui.provider.swing.shared.FakeContainerImpl;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingOnOffButton extends FakeContainerImpl<JPanel> implements OnOffButton {
    
    private class Native extends JPanel {
        public final JToggleButton on = new JToggleButton("On");
        public final JToggleButton off = new JToggleButton("Off");
        public final ButtonGroup buttonGroup = new ButtonGroup();
        
        public Native() {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(on);
            add(off);
            buttonGroup.add(on);
            buttonGroup.add(off);
            off.setSelected(true);
        }
    }

    public SwingOnOffButton() {
        super(SwingPlatform.instance());
    }

    @Override
    protected JPanel create() {
        return new Native();
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
