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

package net.nexustools.gui;

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
	
	public void play();
	public void pause();
	public void stop();
	
	public boolean isPlaying();
	
	public int currentFrame();
	public int countFrames();

}
