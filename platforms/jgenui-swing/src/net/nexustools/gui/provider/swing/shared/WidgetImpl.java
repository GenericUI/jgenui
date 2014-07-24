/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.SwingUtilities;
import net.nexustools.gui.Container;
import net.nexustools.gui.event.FocusListener;
import net.nexustools.gui.event.FocusListener.FocusEvent;
import net.nexustools.gui.event.MoveListener;
import net.nexustools.gui.event.MoveListener.MoveEvent;
import net.nexustools.gui.event.SizeListener;
import net.nexustools.gui.event.SizeListener.SizeEvent;
import net.nexustools.gui.event.VisibilityListener;
import net.nexustools.gui.event.VisibilityListener.VisibilityEvent;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Shape;
import net.nexustools.gui.provider.swing.SwingPlatform;
import net.nexustools.gui.render.Painter;
import net.nexustools.gui.render.Renderer;

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

    protected Renderer renderer;
    protected final J component;
    protected final SwingPlatform platform;
    protected Painter.Instruction[] renderInstructions;
    protected final ArrayList<String> classes = new ArrayList();
    
    private final ListenerProp<ComponentListener> componentListener = new ListenerProp() {
        @Override
        public void connect() {
            ComponentListener eventListener = new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    sizeDispatcher.dispatch(null);
                }
                @Override
                public void componentMoved(ComponentEvent e) {
                    moveDispatcher.dispatch(null);
                }
                @Override
                public void componentShown(ComponentEvent e) {
                    visibilityDispatcher.dispatch(null);
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                    visibilityDispatcher.dispatch(null);
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
        show();
    }
    
    public J internal() {
        return component;
    }
    
    protected abstract J create();
    
    protected void act(Runnable run) {
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

    public Shape getShape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rect getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rect getVisibleBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Rect getTopLevelBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Container container() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public Shape shape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
