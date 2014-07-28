/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import net.nexustools.gui.provider.swing.SwingPlatform;
import net.nexustools.gui.provider.swing.shared.ScrollDeligateImpl.Native;

/**
 *
 * @author katelyn
 */
public abstract class ScrollDeligateImpl<V extends java.awt.Component> extends WidgetImpl<Native<V>> {

    public static class Native<V extends java.awt.Component> extends JScrollPane {
        public final V view;
        protected Native(V view) {
            setName(view.getName());
            view.setName("View");
            
            setViewportView(view);
            this.view = view;
        }

        @Override
        public void setEnabled(boolean enabled) {
            view.setEnabled(enabled);
        }

        @Override
        public boolean isEnabled() {
            return view == null ? true : view.isEnabled();
        } 
    }
    
    public ScrollDeligateImpl(SwingPlatform platform) {
        super(platform);
    }

    protected abstract V createView();
    
    @Override
    protected final Native<V> create() {
        return new Native<V>(createView());
    }

    @Override
    protected Component menuTarget() {
        return component.view;
    }
    
}