/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import net.nexustools.gui.Body;
import net.nexustools.gui.Container;
import net.nexustools.gui.Menu;
import net.nexustools.gui.Toolbar;
import net.nexustools.gui.Widget;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.provider.swing.shared.ContainerImpl;
import net.nexustools.gui.render.StyleSheet;
import net.nexustools.gui.render.Transform;

/**
 *
 * @author katelyn
 */
public class SwingBody extends ContainerImpl<JFrame> implements Body {

    @Override
    public void addToolbar(Toolbar toolbar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeToolbar(Toolbar toolbar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static enum CloseMode {
        DontExitOnClose,
        ExitOnNoBodies,
        ExitOnNoWindows
    }
    
    private static CloseMode exitOnCloseMode = CloseMode.ExitOnNoWindows;
    
    private class NativeBody extends JFrame implements ContainerWrap {

        public NativeBody() {
            setName("Body");
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

        @Override
        public Container getGenUIContainer() {
            return SwingBody.this;
        }

    }

    private Widget mainWidget;
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
    public void add(Widget widget) {
        mainContainer().add(widget);
    }

    @Override
    public void remove(Widget widget) {
        mainContainer().remove(widget);
    }
    
    @Override
    public StyleSheet stylesheet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStylesheet(StyleSheet styleSheet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Layout layout() {
        return mainContainer().layout(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Layout layout) {
        mainContainer().setLayout(layout); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void invalidate() {
        act(new Runnable() {
            @Override
            public void run() {
                System.out.println("Invalidating Window");

                Insets inserts = component.getInsets();
                java.awt.Container container = component.getContentPane();

                Dimension size = container.getMinimumSize();
                size.height += inserts.top + inserts.bottom;
                size.width += inserts.left + inserts.right;
                component.setMinimumSize(size);

                size = container.getMaximumSize();
                size.height += inserts.top + inserts.bottom;
                size.width += inserts.left + inserts.right;
                component.setMaximumSize(size);

                size = container.getPreferredSize();
                size.height += inserts.top + inserts.bottom;
                size.width += inserts.left + inserts.right;
                component.setPreferredSize(size);
            }
        });
                
    }
    
    
    @Override
    public void setMainWidget(final Widget mainWidget) {
        act(new Runnable() {
            @Override
            public void run() {
                java.awt.Container container = component.getContentPane();
                
                SwingBody.this.mainWidget = mainWidget;
                if(mainWidget instanceof ContainerImpl)
                    container = (java.awt.Container) ((ContainerImpl)mainWidget).component;
                else
                    throw new UnsupportedOperationException("Not supported yet.");
                
                component.setContentPane(container);
            }
        });
    }
    @Override
    public Container mainContainer() {
        return read(new Reader<Container>() {
            @Override
            public Container read() {
                if(!(mainWidget instanceof Container))
                    setMainWidget(mainWidget = new SwingContainer());
                
                return (Container) mainWidget;
            }
        });
    }
    public Widget mainWidget() {
        return read(new Reader<Widget>() {
            @Override
            public Widget read() {
                if(mainWidget == null)
                    return mainContainer();
                
                return mainWidget;
            }
        });
    }
    
    @Override
    public void setVisible(boolean visible) {
        if(visible)
            SwingUtilities.updateComponentTreeUI(component);
        super.setVisible(visible);
    }

}
