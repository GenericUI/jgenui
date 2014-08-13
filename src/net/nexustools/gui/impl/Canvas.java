/*
 * jgenui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 or any later version.
 * 
 * jgenui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with jgenui.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.nexustools.gui.impl;

import net.nexustools.gui.render.Renderer;

/**
 *
 * @author katelyn
 */
public interface Canvas extends Widget {
	
	// Common layers
	public static final int OVERLAY = 500;
	
	public static final int GUI = 1000;
	public static final int GUI_DIALOGS = GUI+1;
	public static final int GUI_NOTIFICATIONS = GUI+2;
	public static final int GUI_MENUS = GUI+3;
	
	public Renderer layerRenderer(int layer);
	public void setLayerRenderer(Renderer renderer, int layer);
	
}
