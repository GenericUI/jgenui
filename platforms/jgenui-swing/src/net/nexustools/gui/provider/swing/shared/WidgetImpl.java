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
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.SwingUtilities;
import net.nexustools.gui.Container;
import net.nexustools.gui.Menu;
import net.nexustools.gui.StyleContainer;
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
    public final J component;
    public final SwingPlatform platform;
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
                            return new SizeEvent(component, size());
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
                            return new MoveEvent(component, pos());
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
                            return new VisibilityEvent(component, true);
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
                            return new VisibilityEvent(component, false);
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
    
    public WidgetImpl(SwingPlatform platform) {
        this.platform = platform;
        this.component = create();
    }
    
    public J internal() {
        return component;
    }
    
    protected abstract J create();
    
    protected void act(Runnable run) {
        if(EventQueue.isDispatchThread())
            run.run();
        else
            while(true) {
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

    public void optimize(ListIterator<Painter.Instruction> instructions) {}
    
    public void pushRedraw(Painter.Instruction[] instructions) {
        this.renderInstructions = instructions.length > 0 ? instructions : null;
        component.repaint();
    }
    
    public boolean customRender(Graphics2D g) {
        if(this.renderInstructions != null) {
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
                return new Rect(r.x, r.y, r.width, r.height);
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
        while(container != null) {
            if(container instanceof ContainerWrap)
                return ((ContainerWrap)container).getGenUIContainer();
            
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

    public Menu contextMenu() {
        return null;
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
        while(container != null && !(container instanceof StyleContainer))
            container = container.container();
        
        if(container instanceof StyleContainer)
            return container.activeStyleSheet();
        
        return null;
    }

    public void move(Point pos) {
        final java.awt.Point location = new java.awt.Point((int)pos.x, (int)pos.y);
        
        act(new Runnable() {
            @Override
            public void run() {
                component.setLocation(location);
            }
        });
    }

    public void resize(Size size) {
        final Dimension dimension = new Dimension((int)size.w, (int)size.h);
        
        act(new Runnable() {
            @Override
            public void run() {
                component.setSize(dimension);
            }
        });
    }

    public void setBounds(Rect geom) {
        Size size = geom.size();
        Point topLeft = geom.topLeft();
        final Rectangle rectangle = new Rectangle((int)topLeft.x, (int)topLeft.y, (int)size.w, (int)size.h);
        
        act(new Runnable() {
            @Override
            public void run() {
                component.setBounds(rectangle);
            }
        });
    }
    
    public void setPreferredSize(final Size prefSize) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setMinimumSize(new Dimension((int)prefSize.w, (int)prefSize.h));
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
        return read(new Reader<Size>() {
            @Override
            public Size read() {
                Dimension dim = component.getMinimumSize();
                return new Size(dim.width, dim.height);
            }
        });
    }
    public void setMinimumSize(final Size size) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setMinimumSize(new Dimension((int)size.w, (int)size.h));
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
                component.setMaximumSize(new Dimension((int)size.w, (int)size.h));
            }
        });
    }
    
}
