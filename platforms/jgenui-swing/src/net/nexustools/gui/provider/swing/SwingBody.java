/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import net.nexustools.gui.Body;
import net.nexustools.gui.Menu;
import net.nexustools.gui.provider.swing.shared.ContainerImpl;

/**
 *
 * @author katelyn
 */
public class SwingBody extends ContainerImpl<JFrame> implements Body {
    
    public static enum CloseMode {
        DontExitOnClose,
        ExitOnNoBodies,
        ExitOnNoWindows
    }
    
    private static CloseMode exitOnCloseMode = CloseMode.ExitOnNoWindows;
    
    private class NativeBody extends JFrame {

        public NativeBody() {
            addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent e) {}

                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println(exitOnCloseMode.toString());
                    
                    boolean canExit = true;
                    switch(exitOnCloseMode) {
                        case ExitOnNoBodies:
                            for(Window win : Window.getWindows()) {
                                if(win instanceof NativeBody && win != component && win.isVisible()) {
                                    canExit = false;
                                    break;
                                }
                            }
                        break;
                        
                        case ExitOnNoWindows:
                            for(Window win : Window.getWindows()) {
                                if(win instanceof NativeBody && win != component && win.isVisible()) {
                                    canExit = false;
                                    break;
                                }
                            }
                        break;
                            
                        case DontExitOnClose:
                            canExit = false;
                            break;
                    }
                    if(canExit)
                        System.exit(0);
                }

                @Override
                public void windowClosed(WindowEvent e) {}

                @Override
                public void windowIconified(WindowEvent e) {}

                @Override
                public void windowDeiconified(WindowEvent e) {}

                @Override
                public void windowActivated(WindowEvent e) {}

                @Override
                public void windowDeactivated(WindowEvent e) {}
            });
        }

        @Override
        public void paint(Graphics g) {
            if(!customRender(((Graphics2D)g)))
                super.paint(g); //To change body of generated methods, choose Tools | Templates.
        }

    }

    SwingBody(SwingPlatform platform) {
        super(SwingPlatform.instance());
    }
    public SwingBody(String title) {
        this(SwingPlatform.instance());
        setTitle(title);
    }

    @Override
    protected JFrame create() {
        return new NativeBody();
    }

    @Override
    public String title() {
        return component.getTitle();
    }

    @Override
    public void setTitle(String title) {
        component.setTitle(title);
    }

    @Override
    public Menu menu(String pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addMenu(String pos, Menu menu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show() {
        component.pack();
        super.show();
    }

}
