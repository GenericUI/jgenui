/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing;

import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.nexustools.gui.Body;
import net.nexustools.gui.Container;
import net.nexustools.gui.Label;
import net.nexustools.gui.Widget;
import net.nexustools.gui.platform.Platform;
import net.nexustools.gui.platform.PlatformException;
import net.nexustools.gui.platform.RenderTargetSupportedException;
import net.nexustools.gui.render.RenderTarget;
import nexustools.io.format.StreamTokenizer;

/**
 *
 * @author katelyn
 */
public class SwingPlatform extends Platform {
    
    public static SwingPlatform instance() {
        return (SwingPlatform)Platform.byClass(SwingPlatform.class);
    }
    
    public SwingPlatform() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {} catch (InstantiationException ex) {} catch (IllegalAccessException ex) {} catch (UnsupportedLookAndFeelException ex) {}
    }

    @Override
    public RenderTarget create(Class<? extends RenderTarget> type) throws RenderTargetSupportedException {
        if(type == Container.class)
            return new SwingContainer(this);
        else if(type == Label.class)
            return new SwingLabel(this);
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
    public void setLAF(String laf) {
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
            window.pack();
        }
    }

    @Override
    public String LAF() {
        return UIManager.getLookAndFeel().getName();
    }

    @Override
    public void invokeLater(Runnable run) {
        SwingUtilities.invokeLater(run);
    }

    @Override
    public void invokeAndWait(Runnable run) throws InvocationTargetException {
        try {
            SwingUtilities.invokeAndWait(run);
        } catch (InterruptedException ex) {
            Logger.getLogger(SwingPlatform.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
