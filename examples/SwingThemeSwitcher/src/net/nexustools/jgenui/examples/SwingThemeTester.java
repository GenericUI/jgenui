/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.jgenui.examples;

import net.nexustools.gui.event.SelectionListener;
import net.nexustools.gui.layout.BoxLayout;
import net.nexustools.gui.provider.swing.SwingBody;
import net.nexustools.gui.provider.swing.SwingComboBox;

/**
 *
 * @author katelyn
 */
public class SwingThemeTester extends SwingBody {
    
    public static void main(String[] args) {
        (new SwingThemeTester()).show();
    }

    public SwingThemeTester() {
        super("Swing Theme Tester");
        setLayout(BoxLayout.Vertical);
        
        SwingComboBox<String> comboBox = new SwingComboBox();
        comboBox.addSelectionListener(new SelectionListener() {
            @Override
            public void selectionChanged(SelectionListener.SelectionEvent event) {
                platform().setLAF((String) event.selection[0]);
            }
        });
        comboBox.setOptions(platform().LAFs());
        add(comboBox);
    }
    
}
