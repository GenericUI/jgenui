/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.jgenui.examples;

import net.nexustools.gui.layout.BoxLayout;
import net.nexustools.gui.provider.swing.SwingBody;
import net.nexustools.gui.provider.swing.SwingCheckBox;
import net.nexustools.gui.provider.swing.SwingComboBox;
import net.nexustools.gui.provider.swing.SwingContainer;
import net.nexustools.gui.provider.swing.SwingFrame;
import net.nexustools.gui.provider.swing.SwingLabel;
import net.nexustools.gui.provider.swing.SwingOnOffButton;
import net.nexustools.gui.provider.swing.SwingRadioButton;
import net.nexustools.gui.provider.swing.SwingRangeInput;
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
        SwingContainer topContainer = new SwingContainer(BoxLayout.Horizontal);
        
        // Start First Column
        SwingContainer container = new SwingFrame("Spinners and Buttons", BoxLayout.Vertical);
        SwingComboBox<String> comboBox = new SwingComboBox();
        comboBox.setOptions(stargateNames);
        comboBox.setEditable(true);
        container.add(comboBox);
        comboBox = new SwingComboBox();
        comboBox.setOptions(stargateNames);
        comboBox.setEditable(true);
        comboBox.setEnabled(false);
        container.add(comboBox);
        
        SwingStringInput swingLineInput = new SwingStringInput();
        container.add(swingLineInput);
        swingLineInput = new SwingStringInput();
        swingLineInput.setEnabled(false);
        container.add(swingLineInput);
        
        SwingContainer horContainer = new SwingContainer(BoxLayout.Horizontal);
        SwingLabel label = new SwingLabel("Label");
        horContainer.add(label);
        label = new SwingLabel("Label");
        label.setEnabled(false);
        horContainer.add(label);
        
        SwingRangeInput swingRangeInput = new SwingRangeInput();
        horContainer.add(swingRangeInput);
        swingRangeInput = new SwingRangeInput();
        swingRangeInput.setEnabled(false);
        horContainer.add(swingRangeInput);
        container.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        SwingCheckBox swingCheckBox = new SwingCheckBox("checkbox");
        horContainer.add(swingCheckBox);
        SwingRadioButton swingRadioButton = new SwingRadioButton("radiobutton");
        horContainer.add(swingRadioButton);
        container.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        swingCheckBox = new SwingCheckBox("checkbox");
        swingCheckBox.setSelected(true);
        horContainer.add(swingCheckBox);
        swingRadioButton = new SwingRadioButton("radiobutton");
        swingRadioButton.setSelected(true);
        horContainer.add(swingRadioButton);
        container.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        swingCheckBox = new SwingCheckBox("checkbox");
        horContainer.add(swingCheckBox);
        swingRadioButton = new SwingRadioButton("radiobutton");
        horContainer.add(swingRadioButton);
        horContainer.setEnabled(false);
        container.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        swingCheckBox = new SwingCheckBox("checkbox");
        swingCheckBox.setSelected(true);
        horContainer.add(swingCheckBox);
        swingRadioButton = new SwingRadioButton("radiobutton");
        swingRadioButton.setSelected(true);
        horContainer.add(swingRadioButton);
        horContainer.setEnabled(false);
        container.add(horContainer);
        
        horContainer = new SwingContainer(BoxLayout.Horizontal);
        SwingOnOffButton onOffButton = new SwingOnOffButton();
        horContainer.add(onOffButton);
        onOffButton = new SwingOnOffButton();
        onOffButton.setEnabled(false);
        horContainer.add(onOffButton);
        container.add(horContainer);
        // End First Column
        
        topContainer.add(container);
        add(topContainer);
    }
    
}
