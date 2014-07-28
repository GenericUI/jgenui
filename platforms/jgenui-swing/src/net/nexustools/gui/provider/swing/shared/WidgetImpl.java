/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import net.nexustools.gui.AbstractAction;
import net.nexustools.gui.AbstractMenu;
import net.nexustools.gui.Action;
import net.nexustools.gui.Container;
import net.nexustools.gui.Editable;
import net.nexustools.gui.Menu;
import net.nexustools.gui.MenuItem;
import net.nexustools.gui.StyleRoot;
import net.nexustools.gui.Widget;
import net.nexustools.gui.event.EventDispatcher;
import net.nexustools.gui.event.EventListenerRedirect;
import net.nexustools.gui.event.FocusListener;
import net.nexustools.gui.event.FocusListener.FocusEvent;
import net.nexustools.gui.event.MoveListener;
import net.nexustools.gui.event.MoveListener.MoveEvent;
import net.nexustools.gui.event.SizeListener;
import net.nexustools.gui.event.SizeListener.SizeEvent;
import net.nexustools.gui.event.VisibilityListener;
import net.nexustools.gui.event.VisibilityListener.VisibilityEvent;
import net.nexustools.gui.geom.Point;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.provider.swing.SwingPlatform;
import net.nexustools.gui.provider.swing.shared.ContainerImpl.ContainerWrap;
import net.nexustools.gui.render.Painter;
import net.nexustools.gui.render.Renderer;
import net.nexustools.gui.render.Style;
import net.nexustools.gui.render.StyleSheet;

/**
 *
 * @author katelyn
 * @param <J> The type of swing component this works with
 */
public abstract class WidgetImpl<J extends Component> {

    protected static abstract class Reader<R> implements Runnable {

        R value;

        @Override
        public void run() {
            value = read();
        }

        public abstract R read();
    }

    protected Style style;
    protected final J component;
    protected final SwingPlatform platform;
    protected Painter.Instruction[] renderInstructions;
    protected final ArrayList<String> classes = new ArrayList();
    protected Renderer renderer;

    private final ListenerProp<ComponentListener> componentListener = new ListenerProp() {
        @Override
        public void connect() {
            ComponentListener eventListener = new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    sizeDispatcher.dispatch(new EventDispatcher.Processor<SizeListener, SizeEvent>() {

                        @Override
                        public SizeEvent create() {
                            return new SizeEvent(internal(), size());
                        }

                        @Override
                        public void dispatch(SizeListener listener, SizeEvent event) {
                            listener.sizeChanged(event);
                        }
                    });
                }

                @Override
                public void componentMoved(ComponentEvent e) {
                    moveDispatcher.dispatch(new EventDispatcher.Processor<MoveListener, MoveEvent>() {

                        @Override
                        public MoveEvent create() {
                            return new MoveEvent(internal(), pos());
                        }

                        @Override
                        public void dispatch(MoveListener listener, MoveEvent event) {
                            listener.positionChanged(event);
                        }
                    });
                }

                @Override
                public void componentShown(ComponentEvent e) {
                    visibilityDispatcher.dispatch(new EventDispatcher.Processor<VisibilityListener, VisibilityEvent>() {

                        @Override
                        public VisibilityEvent create() {
                            return new VisibilityEvent(internal(), true);
                        }

                        @Override
                        public void dispatch(VisibilityListener listener, VisibilityEvent event) {
                            listener.visibilityChanged(event);
                        }
                    });
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                    visibilityDispatcher.dispatch(new EventDispatcher.Processor<VisibilityListener, VisibilityEvent>() {

                        @Override
                        public VisibilityEvent create() {
                            return new VisibilityEvent(internal(), false);
                        }

                        @Override
                        public void dispatch(VisibilityListener listener, VisibilityEvent event) {
                            listener.visibilityChanged(event);
                        }
                    });
                }
            };

            component.addComponentListener(eventListener);
            set(eventListener);
        }

        @Override
        public void disconnect() {
            clear();
        }
    };
    private final ListenerProp<java.awt.event.FocusListener> focusListener = new ListenerProp() {
        @Override
        public void connect() {
            java.awt.event.FocusListener eventListener = new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    focusDispatcher.dispatch(null);
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    focusDispatcher.dispatch(null);
                }
            };

            component.addFocusListener(eventListener);
            set(eventListener);
        }

        @Override
        public void disconnect() {
            clear();
        }
    };
    public final PropDispatcher<MoveListener, MoveEvent> moveDispatcher = new PropDispatcher(componentListener, platform());
    public final PropDispatcher<SizeListener, SizeEvent> sizeDispatcher = new PropDispatcher(componentListener, platform());
    public final PropDispatcher<VisibilityListener, VisibilityEvent> visibilityDispatcher = new PropDispatcher(componentListener, platform());
    public final PropDispatcher<FocusListener, FocusEvent> focusDispatcher = new PropDispatcher(focusListener, platform());

    private Menu contextMenu;

    public WidgetImpl(SwingPlatform platform) {
        this.platform = platform;
        component = create();
        component.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            private void doPop(MouseEvent e) {
                Menu contextMenu = contextMenu();
                if (contextMenu != null) {

                }
            }

            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

    protected void inherit(Widget from) {
        setTag(from.tag());
        setEnabled(from.enabled());
        setFocusable(from.isFocusable());
        setRenderer(from.renderer());
        setVisible(from.isVisible());
    }

    public J internal() {
        return component;
    }

    public boolean enabled() {
        return read(new Reader<Boolean>() {
            @Override
            public Boolean read() {
                return component.isEnabled();
            }
        });
    }

    public void setEnabled(final boolean enabled) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setEnabled(enabled);
            }
        });
    }

    protected abstract J create();

    protected void act(Runnable run) {
        if (EventQueue.isDispatchThread()) {
            run.run();
        } else {
            while (true) {
                try {
                    SwingUtilities.invokeAndWait(run);
                    break;
                } catch (InterruptedException ex) {
                } catch (InvocationTargetException ex) {
                    ex.getCause().printStackTrace();
                    break;
                }
            }
        }
    }

    protected <R> R read(Reader<R> run) {
        act(run);
        return run.value;
    }

    public SwingPlatform platform() {
        return platform;
    }

    public String tag() {
        return read(new Reader<String>() {
            @Override
            public String read() {
                return component.getName();
            }
        });

    }

    public void setTag(final String name) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setName(name);
            }
        });
    }

    public boolean isVisible() {
        return read(new Reader<Boolean>() {

            @Override
            public Boolean read() {
                return component.isVisible();
            }
        });
    }

    public boolean isOnscreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setVisible(final boolean visible) {
        act(new Runnable() {

            @Override
            public void run() {
                component.setVisible(visible);
            }
        });
    }

    public boolean hasFocus() {
        return component.hasFocus();
    }

    public void requestFocus() {
        act(new Runnable() {

            @Override
            public void run() {
                component.requestFocus();
            }
        });
    }

    public boolean isFocusable() {
        return component.isFocusable();
    }

    public void setFocusable(final boolean focusable) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setFocusable(focusable);
            }
        });
    }

    public void optimize(ListIterator<Painter.Instruction> instructions) {
    }

    public void pushRedraw(Painter.Instruction[] instructions) {
        this.renderInstructions = instructions.length > 0 ? instructions : null;
        component.repaint();
    }

    public boolean customRender(Graphics2D g) {
        if (this.renderInstructions != null) {
            // TODO: Render
        }
        return false;
    }

    public Point pos() {
        return bounds().topLeft();
    }

    public Size size() {
        return bounds().size();
    }

    public Shape shape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rect bounds() {
        return read(new Reader<Rect>() {
            @Override
            public Rect read() {
                Rectangle r = component.getBounds();
                return (new Rect(r.x, r.y, r.width, r.height)).minus(parentOffset());
            }
        });
    }

    public Rect visibleBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rect topBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Container container() {
        java.awt.Container container = component.getParent();
        while (container != null) {
            if (container instanceof ContainerWrap) {
                return ((ContainerWrap) container).getGenUIContainer();
            }

            container = container.getParent();
        }

        return null;
    }

    public Container topLevel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Renderer renderer() {
        // TODO: Implement a renderer that will render the native component by default
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void render(Painter p) {
        renderer.render(p);
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public void addMoveListener(MoveListener moveListener) {
        moveDispatcher.add(moveListener);
    }

    public void addSizeListener(SizeListener sizeListener) {
        sizeDispatcher.add(sizeListener);
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        visibilityDispatcher.add(visibilityListener);
    }

    public void addFocusListener(FocusListener focusListener) {
        focusDispatcher.add(focusListener);
    }

    public void removeMoveListener(MoveListener moveListener) {
        moveDispatcher.remove(moveListener);
    }

    public void removeSizeListener(SizeListener sizeListener) {
        sizeDispatcher.remove(sizeListener);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        visibilityDispatcher.remove(visibilityListener);
    }

    public void removeFocusListener(FocusListener focusListener) {
        focusDispatcher.remove(focusListener);
    }

    public void addClass(String clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean hasClass(String clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeClass(String clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private MouseListener mouseListener;

    public void setContextMenu(final Menu menu) {
        act(new Runnable() {
            public JMenu build(AbstractMenu menu) {
                JMenu jmenu = new JMenu(menu.text());
                for (final MenuItem menuItem : menu) {
                    if (menuItem instanceof Menu.Separator) {
                        jmenu.addSeparator();
                    } else if (menuItem instanceof AbstractAction) {
                        jmenu.add(((AbstractAction) menuItem).text()).addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ((AbstractAction) menuItem).activate();
                            }
                        });
                    } else if (menuItem instanceof AbstractMenu) {
                        jmenu.add(build((AbstractMenu) menuItem));
                    }
                }
                return jmenu;
            }

            public void run() {
                contextMenu = menu;
                if (menu == null) {
                    if (mouseListener != null) {
                        menuTarget().removeMouseListener(mouseListener);
                        mouseListener = null;
                    }
                } else if (mouseListener == null) {
                    mouseListener = new MouseListener() {
                        public void show(int x, int y) {
                            JPopupMenu popupMenu = new JPopupMenu();
                            for (final MenuItem menuItem : contextMenu) {
                                if (menuItem instanceof Menu.Separator)
                                    popupMenu.addSeparator();
                                else if (menuItem instanceof AbstractAction) {
                                    popupMenu.add(((AbstractAction) menuItem).text()).addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            ((AbstractAction) menuItem).activate();
                                        }
                                    });
                                } else if (menuItem instanceof AbstractMenu)
                                    popupMenu.add(build((AbstractMenu) menuItem));
                            }
                            popupMenu.show(menuTarget(), x, y);
                        }

                        public void mouseClicked(MouseEvent e) {
                            if(e.isPopupTrigger())
                                show(e.getX(), e.getY());
                        }

                        public void mousePressed(MouseEvent e) {
                            if(e.isPopupTrigger())
                                show(e.getX(), e.getY());
                        }

                        public void mouseReleased(MouseEvent e) {
                            if(e.isPopupTrigger())
                                show(e.getX(), e.getY());
                        }

                        public void mouseEntered(MouseEvent e) {}

                        public void mouseExited(MouseEvent e) {}
                    };
                    menuTarget().addMouseListener(mouseListener);
                }
            }
        });
    }
    
    protected java.awt.Component menuTarget() {
        return component;
    }

    public Menu contextMenu() {
        return read(new Reader<Menu>() {
            @Override
            public Menu read() {
                return contextMenu;
            }
        });
    }

    public void uninstallRedirect(EventListenerRedirect redirect) {

    }

    public void installRedirect(EventListenerRedirect redirect) {

    }

    public void setStyle(final Style style) {
        act(new Runnable() {
            @Override
            public void run() {
                WidgetImpl.this.style = style;
            }
        });
    }

    public Style style() {
        return read(new Reader<Style>() {
            @Override
            public Style read() {
                return style;
            }
        });
    }

    public StyleSheet activeStyleSheet() {
        Container container = container();
        while (container != null && !(container instanceof StyleRoot)) {
            container = container.container();
        }

        if (container instanceof StyleRoot) {
            return container.activeStyleSheet();
        }

        return null;
    }

    public void move(final Point pos) {
        act(new Runnable() {
            @Override
            public void run() {
                pos.plus(parentOffset());
                component.setLocation(new java.awt.Point((int) pos.x, (int) pos.y));
            }
        });
    }

    public void resize(final Size size) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setSize(new Dimension((int) size.w, (int) size.h));
            }
        });
    }

    public void setBounds(final Rect geom) {
        act(new Runnable() {
            @Override
            public void run() {
                geom.plus(parentOffset());
                component.setBounds(new Rectangle((int) geom.topLeft.x, (int) geom.topLeft.y, (int) geom.size.w, (int) geom.size.h));
            }
        });
    }

    public void setPreferredSize(final Size prefSize) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setMinimumSize(new Dimension((int) prefSize.w, (int) prefSize.h));
            }
        });
    }

    public Size preferredSize() {
        return read(new Reader<Size>() {
            @Override
            public Size read() {
                Dimension dim = component.getPreferredSize();
                return new Size(dim.width, dim.height);
            }
        });
    }

    public Size minimumSize() {
        try {
            return read(new Reader<Size>() {
                @Override
                public Size read() {
                    Dimension dim = component.getMinimumSize();
                    return new Size(dim.width, dim.height);
                }
            });
        } catch (NullPointerException e) {
            return new Size(0, 0);
        }
    }

    public void setMinimumSize(final Size size) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setMinimumSize(new Dimension((int) size.w, (int) size.h));
            }
        });
    }

    public Size maximumSize() {
        return read(new Reader<Size>() {
            @Override
            public Size read() {
                Dimension dim = component.getMaximumSize();
                return new Size(dim.width, dim.height);
            }
        });
    }

    public void setMaximumSize(final Size size) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setMaximumSize(new Dimension((int) size.w, (int) size.h));
            }
        });
    }

    public Renderer defaultRenderer() {
        return new Renderer() {
            @Override
            public void render(Painter painter) {
                // TODO: Paint component into painter
            }
        };
    }

    protected Point parentOffset() {
        java.awt.Container par = component.getParent();
        if (par == null) {
            return new Point(0, 0);
        }
        Insets insets = par.getInsets();
        if (insets == null) {
            return new Point(0, 0);
        }
        return new Point(insets.left, insets.top);
    }

    public void showMenu(AbstractMenu menu, Point at) {
        menu.show((Widget)this, at);
    }
    
    public Menu buildEditMenu(Editable editable) {
        Menu fileCopyMenu = new Menu();
        Action action = new Action("Cut");
        fileCopyMenu.add(editable.cutAction());
        action = new Action("Copy");
        fileCopyMenu.add(editable.copyAction());
        action = new Action("Paste");
        fileCopyMenu.add(editable.pasteAction());
        return fileCopyMenu;
    }

}
