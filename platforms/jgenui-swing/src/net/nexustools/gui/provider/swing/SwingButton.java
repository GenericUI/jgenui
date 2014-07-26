/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import net.nexustools.gui.Button;
import net.nexustools.gui.Shortcut;
import net.nexustools.gui.event.ActionListener;
import net.nexustools.gui.event.ActionListener.ActionEvent;
import net.nexustools.gui.event.EventDispatcher;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 */
public class SwingButton extends WidgetImpl<JButton> implements Button {
    
    private class NativeButton extends JButton {

        @Override
        public void paint(Graphics g) {
            if(!customRender((Graphics2D)g))
                super.paint(g);
        }
        
    }

    public SwingButton() {
        this(SwingPlatform.instance());
    }
    public SwingButton(String text) {
        this();
        setText(text);
    }
    SwingButton(SwingPlatform platform) {
        super(platform);
    }

    @Override
    protected JButton create() {
        return new NativeButton();
    }

    @Override
    public void setText(final String text) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setText(text);
            }
        });
    }

    @Override
    public String text() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.getText();
            }
        });
    }

    @Override
    public Shortcut shortcut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setShortcut(Shortcut shortcut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public final EventDispatcher<SwingPlatform, ActionListener, ActionEvent> actionDispatcher = new EventDispatcher(platform()) {

        private java.awt.event.ActionListener actionListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                activate0();
            }
        };
        @Override
        public void connect() {
            component.addActionListener(actionListener);
        }

        @Override
        public void disconnect() {
            component.removeActionListener(actionListener);
        }
    };

    @Override
    public void addActionListener(ActionListener actionListener) {
        actionDispatcher.add(actionListener);
    }

    @Override
    public void removeActionListener(ActionListener actionListener) {
        actionDispatcher.remove(actionListener);
    }

    protected void activate0() {
        actionDispatcher.dispatch(new EventDispatcher.Processor<ActionListener, ActionEvent>() {
            @Override
            public ActionEvent create() {
                return new ActionEvent(component);
            }
            @Override
            public void dispatch(ActionListener listener, ActionEvent event) {
                listener.activated(event);
            }
        });
    }
    
    @Override
    public void activate() {
        act(new Runnable() {
            @Override
            public void run() {
                activate0();
            }
        });
    }
    
}
