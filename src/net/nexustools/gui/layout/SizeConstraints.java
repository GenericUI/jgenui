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
    
    public Size min;
    public Size max;
    public Size pref;
    
    public SizeConstraints(Size min, Size max, Size pref) {
        this.min = min.clone();
        this.max = max.clone();
        this.pref = pref.clone();
    }
    public SizeConstraints() {}
    
}
