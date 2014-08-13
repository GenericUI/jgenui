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

import java.io.InputStream;
import net.nexustools.gui.render.Renderer;
import net.nexustools.io.Stream;

/**
 *
 * @author katelyn
 */
public interface Image extends Widget {
	
	public static enum Mode {
		/**
		 * Render the image exactly as it exists,
		 * ignore the viewport entirely.
		 */
		Original,
		/**
		 * Scale the image to fit the viewport,
		 * ensuring all of it is on screen.
		 */
		ScaleToFit,
		/**
		 * Crop the image to fill the viewport,
		 * ensuring the entire viewport has pixels.
		 */
		CropToFit,
		/**
		 * Stretch the image to fill the viewport,
		 * ensuring the entire viewport has pixels.
		 */
		Stretch
	}
	
	public Mode mode();
	public void setMode(Mode mode);
	
	public float speed();
	public void setSpeed(float speed);
	
	public void load(String uri);
	public void load(Stream stream);
	public void load(InputStream inStream);
	
	public void setLoadRenderer(Renderer renderer);
	public Renderer loadRenderer();
	
	public int currentFrame();
	public void setCurrentFrame(int frame);
	public int countFrames();

}
