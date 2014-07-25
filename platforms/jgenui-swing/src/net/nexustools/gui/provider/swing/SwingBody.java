/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.nexustools.gui.Body;
import net.nexustools.gui.Container;
import net.nexustools.gui.Menu;
import net.nexustools.gui.Widget;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.layout.Layout;
import net.nexustools.gui.provider.swing.shared.ContainerImpl;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;
import net.nexustools.gui.render.StyleSheet;

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
        public void pack() {
            setMinimumSize(null);
            doLayout();
            super.pack();
            setMinimumSize(getSize());
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
    
    public void setMainWidget(final Widget mainWidget) {
        act(new Runnable() {
            @Override
            public void run() {
                SwingBody.this.mainWidget = mainWidget;
                if(mainWidget instanceof ContainerImpl)
                    component.setContentPane((java.awt.Container) ((ContainerImpl)mainWidget).component);
                else
                    component.setContentPane(new JPanel() {
                        {
                            final Component component = ((WidgetImpl)mainWidget).component;
                            addComponentListener(new ComponentListener() {
                                @Override
                                public void componentResized(ComponentEvent e) {
                                    component.setSize(getSize());
                                }
                                @Override
                                public void componentMoved(ComponentEvent e) {}
                                @Override
                                public void componentShown(ComponentEvent e) {}
                                @Override
                                public void componentHidden(ComponentEvent e) {}
                            });
                            add(component);
                        }
                    });
            }
        });
    }
    public Container mainContainer() {
        return read(new Reader<Container>() {
            @Override
            public Container read() {
                if(!(mainWidget instanceof Container)) {
                    SwingContainer sContainer = new SwingContainer() {
                        @Override
                        public void setPreferredSize(Size prefSize) {
                            System.out.println("Updating Minimum Size");
                            System.out.println(prefSize);
                            
                            super.setPreferredSize(prefSize);
                            setMinimumSize(minimumSize().max(prefSize));
                            SwingBody.this.component.invalidate();
                        }
                    };
                    mainWidget = sContainer;
                    
                    component.setContentPane(sContainer.component);
                }
                
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
