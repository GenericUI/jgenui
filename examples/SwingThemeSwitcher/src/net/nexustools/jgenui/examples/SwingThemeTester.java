/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.jgenui.examples;

import net.nexustools.gui.event.ActionListener;
import net.nexustools.gui.event.SelectionListener;
import net.nexustools.gui.layout.BoxLayout;
import net.nexustools.gui.provider.swing.SwingBody;
import net.nexustools.gui.provider.swing.SwingButton;
import net.nexustools.gui.provider.swing.SwingComboBox;
import net.nexustools.gui.provider.swing.SwingContainer;
import net.nexustools.gui.provider.swing.SwingLabel;
import net.nexustools.gui.provider.swing.SwingStringInput;

/**
 *
 * @author katelyn
 */
public class SwingThemeTester extends SwingBody {
    
    public static final String[] stargateNames = new String[]{"Teal'c", "Daniel", "Jack", "Samantha"};
    
    public static void main(String[] args) {
        (new SwingThemeTester()).show();
    }

    public SwingThemeTester() {
        super("Swing Theme Tester");
        setLayout(BoxLayout.Vertical);
        
        SwingContainer container = new SwingContainer(BoxLayout.Horizontal);
        
        SwingComboBox<String> comboBox = new SwingComboBox();
        comboBox.setOptions(stargateNames);
        comboBox.setEditable(true);
        add(comboBox);
        comboBox = new SwingComboBox();
        comboBox.setOptions(stargateNames);
        comboBox.setEditable(true);
        comboBox.setEnabled(false);
        add(comboBox);
        
        SwingStringInput swingLineInput = new SwingStringInput();
        add(swingLineInput);
        swingLineInput = new SwingStringInput();
        swingLineInput.setEnabled(false);
        add(swingLineInput);
    }
    
}
