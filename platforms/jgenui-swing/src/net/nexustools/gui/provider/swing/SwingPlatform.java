/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.AWTEvent;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Window;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.nexustools.gui.Base;
import net.nexustools.gui.Body;
import net.nexustools.gui.Button;
import net.nexustools.gui.Container;
import net.nexustools.gui.Label;
import net.nexustools.gui.Widget;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.platform.PlatformException;
import net.nexustools.gui.platform.RenderTargetSupportedException;
import net.nexustools.gui.provider.swing.shared.ContainerImpl;
import net.nexustools.gui.provider.swing.shared.ContainerImpl.ContainerWrap;
import nexustools.io.format.StreamTokenizer;

/**
 *
 * @author katelyn
 */
public class SwingPlatform extends Platform {
    
    public static class SwingEventQueue extends EventQueue {
        private final ArrayList<Runnable> idleEvents = new ArrayList();
        private Thread thisThread;
        
        public boolean onThread() {
            return thisThread == Thread.currentThread();
        }
        
        @Override
        protected void dispatchEvent(AWTEvent event) {
            thisThread = Thread.currentThread();
            super.dispatchEvent(event);
            testIdle();
        }
        
        public void testIdle() {
            if (peekEvent() == null) {
                ListIterator<Runnable> li = idleEvents.listIterator(idleEvents.size());

                while(li.hasPrevious()) {
                    try {
                        li.previous().run();
                    } catch(RuntimeException ex) {
                        ex.printStackTrace();
                    }
                }
                idleEvents.clear();
            }
        }

        private void onIdle(final Runnable run) {
            idleEvents.add(run);
            testIdle();
        }
    }
    public static final SwingEventQueue eventQueue = new SwingEventQueue();
    
    public static SwingPlatform instance() {
        return (SwingPlatform)Platform.byClass(SwingPlatform.class);
    }
    
    public SwingPlatform() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {} catch (InstantiationException ex) {} catch (IllegalAccessException ex) {} catch (UnsupportedLookAndFeelException ex) {}
    }

    @Override
    public Base create(Class<? extends Base> type) throws RenderTargetSupportedException {
        if(type == Container.class)
            return new SwingContainer(this);
        else if(type == Label.class)
            return new SwingLabel(this);
        else if(type == Button.class)
            return new SwingButton(this);
        else if(type == Body.class)
            return new SwingBody(this);
        
        throw new RenderTargetSupportedException();
    }

    @Override
    public Widget parse(StreamTokenizer processor) throws PlatformException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean supports(Feature feature) {
        switch(feature) {
            case MultipleBodies:
            case ComplexDrawing:
            case FullPainter:
                return true;
        }
        return false;
    }

    @Override
    public String[] LAFs() {
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        String[] styles = new String[lookAndFeels.length+1];
        for(int i=0; i<lookAndFeels.length; i++) {
            styles[i] = lookAndFeels[i].getName();
        }
        styles[lookAndFeels.length] = "Blank";
        return styles;
    }
    
    @Override
    public void setLAF(final String laf) {
        try {
            act(new Runnable() {
                
                @Override
                public void run() {
                    boolean foundLAF = false;
                    UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
                    for(int i=0; i<lookAndFeels.length; i++) {
                        if(lookAndFeels[i].getName().equals(laf))
                            try {
                                UIManager.setLookAndFeel(lookAndFeels[i].getClassName());
                                foundLAF = true;
                                break;
                            } catch (ClassNotFoundException ex) {} catch (InstantiationException ex) {} catch (IllegalAccessException ex) {} catch (UnsupportedLookAndFeelException ex) {}
                    }
                    if(!foundLAF)
                        return;
                    
                    for(Window window : Window.getWindows()) {
                        SwingUtilities.updateComponentTreeUI(window);
                        if(window instanceof ContainerWrap)
                            ((ContainerImpl)((ContainerWrap)window).getGenUIContainer()).invalidate();
                    }
                }
            });
        } catch (InvocationTargetException ex) {
            ex.getCause().printStackTrace();
        }  
    }

    @Override
    public String LAF() {
        return UIManager.getLookAndFeel().getName();
    }

    @Override
    public void onIdle(final Runnable run) {
        try {
            act(new Runnable() {
                @Override
                public void run() {
                    eventQueue.onIdle(run);
                }
            });
        } catch (InvocationTargetException ex) {
            ex.getCause().printStackTrace();
        }
    }

    @Override
    public void act(Runnable run) throws InvocationTargetException {
        try {
            if(EventQueue.isDispatchThread())
                run.run();
            else
                eventQueue.invokeAndWait(run);
        } catch (InterruptedException ex) {
            Logger.getLogger(SwingPlatform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void open(String url) {
        try {
            open(new URI(url));
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void open(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            throw new UnsupportedOperationException();
    }
    
}
