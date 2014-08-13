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

package net.nexustools.gui.platform;

/**
 *
 * @author katelyn
 */
public interface MimeStorage {
	
	static enum ContentType {
		String,
		Number,
		Image,
		Audio
	}
	
	public String string();
	public void setString(String string);
	
	public double number();
	public void setNumber(double number);
	
	public boolean supports(ContentType type);
	
	public Object get(ContentType type);
	public void put(ContentType type, Object data);
	
}
