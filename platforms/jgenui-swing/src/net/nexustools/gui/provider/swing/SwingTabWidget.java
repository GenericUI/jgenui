/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import javax.swing.JTabbedPane;
import net.nexustools.gui.TabWidget;
import net.nexustools.gui.Widget;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingTabWidget extends WidgetImpl<javax.swing.JTabbedPane> implements TabWidget {

    public class Native extends JTabbedPane {
        public Native() {
            setName("TabWidget");
        }
    }
    
    public static enum Orientation {
        Top,
        Left,
        Right,
        Bottom
    }
    
    public SwingTabWidget() {
        super(SwingPlatform.instance());
    }
    SwingTabWidget(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JTabbedPane create() {
        return new Native();
    }

    @Override
    public void add(final Widget widget, final String name) {
        act(new Runnable() {
            @Override
            public void run() {
                component.add(name, ((WidgetImpl)widget).internal());
            }
        });
    }

    @Override
    public void remove(final Widget widget) {
        act(new Runnable() {
            @Override
            public void run() {
                component.remove(((WidgetImpl)widget).internal());
            }
        });
    }

    @Override
    public void remove(String tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setOrientation(Orientation orientation) {
        switch(orientation) {
            case Top:
                component.setTabPlacement(JTabbedPane.TOP);
                break;
            case Left:
                component.setTabPlacement(JTabbedPane.LEFT);
                break;
            case Right:
                component.setTabPlacement(JTabbedPane.RIGHT);
                break;
            case Bottom:
                component.setTabPlacement(JTabbedPane.BOTTOM);
                break;
        }
    }
    
}