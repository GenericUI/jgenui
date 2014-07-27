/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import net.nexustools.gui.List;
import net.nexustools.gui.MultiList;
import net.nexustools.gui.event.SelectionListener;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingMultiList<I> extends WidgetImpl<JList<I>> implements MultiList<I> {

    private class Native<I> extends JList<I> {

        public Native() {
            setName("List");
            setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        }
    }

    private I[] options;
    public SwingMultiList() {
        super(SwingPlatform.instance());
    }

    SwingMultiList(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JList<I> create() {
        return new Native<I>();
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
    public void setValue(I value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public I value() {
        return read(new Reader<I>() {
            @Override
            public I read() {
                return component.getSelectedValue();
            }
        });
    }

    @Override
    public I[] options() {
        return read(new Reader<I[]>() {
            @Override
            public I[] read() {
                return options;
            }
        });
    }

    @Override
    public void setOptions(final I... options) {
        act(new Runnable() {

            public void run() {
                component.setModel(new DefaultListModel<I>() {
                    {
                        for (I option : options) {
                            addElement(option);
                        }
                    }
                });
            }
        });

    }

    @Override
    public I[] selected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addSelectionListener(SelectionListener<I, MultiList> selectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSelectionListener(SelectionListener<I, MultiList> selectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isAllowingMultiple() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void allowMultiple(boolean yes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
