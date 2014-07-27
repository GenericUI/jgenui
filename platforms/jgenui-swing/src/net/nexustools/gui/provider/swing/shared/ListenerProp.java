/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.shared;

import java.util.EventListener;
import nexustools.concurrent.Prop;

/**
 *
 * @author katelyn
 */
public abstract class ListenerProp<T extends EventListener> extends Prop<T> {
    
    public abstract void connect();
    public abstract void disconnect();
    
}
