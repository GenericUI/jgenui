/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.layout;

import net.nexustools.gui.geom.Size;

/**
 *
 * @author katelyn
 */
public class SizeConstraints {
    
    public final Size min;
    public final Size max;
    public final Size pref;
    
    public SizeConstraints(Size min, Size max, Size pref) {
        this.min = min;
        this.max = max;
        this.pref = pref;
    }
    
}
