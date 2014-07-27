/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nexustools.gui.provider.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import net.nexustools.gui.ComboBox;
import net.nexustools.gui.event.EventDispatcher;
import net.nexustools.gui.event.SelectionListener;
import net.nexustools.gui.geom.Size;
import net.nexustools.gui.provider.swing.shared.ListenerProp;
import net.nexustools.gui.provider.swing.shared.PropDispatcher;
import net.nexustools.gui.provider.swing.shared.WidgetImpl;

/**
 *
 * @author katelyn
 * @param <I>
 */
public class SwingComboBox<I> extends WidgetImpl<JComboBox> implements ComboBox<I> {

    private I[] options;
    public SwingComboBox() {
        super(SwingPlatform.instance());
    }
    public SwingComboBox(I[] options) {
        this();
        setOptions(options);
    }

    @Override
    protected JComboBox create() {
        return new JComboBox() {
            {
                setName("ComboBox");
            }
            @Override
            public void paint(Graphics g) {
                if (!customRender((Graphics2D) g)) {
                    super.paint(g);
                }
            }
        };
    }

    @Override
    public boolean isEditable() {
        return read(new Reader<Boolean>() {
            @Override
            public Boolean read() {
                return component.isEditable();
            }
        });
    }

    @Override
    public void setEditable(final boolean editable) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setEditable(editable);
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
            @Override
            public void run() {
                SwingComboBox.this.options = options;
                component.setModel(new DefaultComboBoxModel<I>(options));
            }
        });
    }

    @Override
    public I[] selected() {
        return read(new Reader<I[]>() {
            @Override
            public I[] read() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    @Override
    public I value() {
        return read(new Reader<I>() {
            @Override
            public I read() {
                return (I) component.getSelectedItem();
            }
        });
    }

    @Override
    public void setValue(final I value) {
        act(new Runnable() {
            @Override
            public void run() {
                component.setSelectedItem(value);
            }
        });
    }

    private final ListenerProp<ItemListener> selectionListener = new ListenerProp<ItemListener>() {
        @Override
        public void connect() {
            SwingComboBox.this.act(new Runnable() {
                @Override
                public void run() {
                    ItemListener eventListener = new ItemListener() {
                        private int index = component.getSelectedIndex();

                        @Override
                        public void itemStateChanged(final ItemEvent e) {
                            if (index == component.getSelectedIndex()) {
                                return;
                            }

                            index = component.getSelectedIndex();
                            selectionDispatcher.dispatch(new EventDispatcher.Processor<SelectionListener, SelectionListener.SelectionEvent>() {
                                @Override
                                public SelectionListener.SelectionEvent create() {
                                    return new SelectionListener.SelectionEvent(component, component.getSelectedObjects(), index, index);
                                }

                                @Override
                                public void dispatch(SelectionListener listener, SelectionListener.SelectionEvent event) {
                                    listener.selectionChanged(event);
                                }
                            });
                        }
                    };

                    component.addItemListener(eventListener);
                    set(eventListener);
                }
            });

        }

        @Override
        public void disconnect() {
            SwingComboBox.this.act(new Runnable() {
                @Override
                public void run() {
                    component.removeItemListener(clear());
                }
            });
        }
    };
    public final PropDispatcher<SelectionListener, SelectionListener.SelectionEvent> selectionDispatcher = new PropDispatcher(selectionListener, platform());

    @Override
    public void addSelectionListener(SelectionListener selectionListener) {
        selectionDispatcher.add(selectionListener);
    }

    @Override
    public void removeSelectionListener(SelectionListener selectionListener) {
        selectionDispatcher.remove(selectionListener);
    }

    @Override
    public String template() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTemplate(String template) {}
    
    

}
