/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.nexustools.gui.provider.swing.render;

import java.awt.Graphics2D;
import java.util.List;
import java.util.ListIterator;
import net.nexustools.gui.render.Painter;

/**
 *
 * @author katelyn
 */
public abstract class SwingInstruction implements Painter.Instruction {
    
    public static SwingInstruction compile(Painter.Instruction instruction) {
        if(instruction instanceof SwingInstruction)
            return ((SwingInstruction)instruction);
        return null;
    }
    
    public static void optimize(ListIterator<Painter.Instruction> instructions) {
    }
    
    public abstract void run(Graphics2D graphics);
    
}
