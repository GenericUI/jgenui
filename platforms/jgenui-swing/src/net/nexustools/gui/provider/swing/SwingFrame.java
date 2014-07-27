/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import net.nexustools.gui.Frame;
import net.nexustools.gui.layout.Layout;

/**
 *
 * @author katelyn
 */
public class SwingFrame extends SwingContainer implements Frame {

    private String title;
    public SwingFrame(String title) {
        super();
        setTitle(title);
    }
    public SwingFrame(String title, Layout layout) {
        this(title);
        setLayout(layout);
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        Border borderStyle = BorderFactory.createEtchedBorder();
        component.setBorder(BorderFactory.createTitledBorder(borderStyle, title));
    }
    
}
