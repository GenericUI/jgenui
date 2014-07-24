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

package net.nexustools.gui.render;

import java.util.ArrayList;
import net.nexustools.gui.geom.Rect;
import net.nexustools.gui.geom.Shape;

/**
 *
 * @author katelyn
 */
public class Painter {

	public static class Instruction {
		public Instruction() {
		}
	}
	
	private final RenderTarget target;
	private final ArrayList<Shape> clipStack = new ArrayList();
	private final ArrayList<Transform> transformStack = new ArrayList();
	private final ArrayList<Instruction> instructions = new ArrayList();
	private Shape clipShape = new Rect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
	private Transform transform = new Transform();
	public Painter(RenderTarget renderTarget) {
		target = renderTarget;
	}

	public Instruction[] takeInstructions() {
		try {
			return instructions.toArray(new Instruction[instructions.size()]);
		} finally {
			instructions.clear();
		}
	}
	
	public void optimize() {
		target.optimize(instructions.listIterator());
	}

	public void send(boolean optimize) {
		optimize();
	}

	public void send() {
		send(true);
	}
	
}
