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
	
	protected static class PushFrame {
		public final Shape baseClip;
		public Renderable background;
		public Renderable foreground;
		public Transform transform;
		public Shape clipShape;
		
		public PushFrame(Shape base) {
			baseClip = base;
		}
		public PushFrame(Shape base, PushFrame other) {
			this(base);
			transform = other.transform;
			background = other.background;
			foreground = other.foreground;
			clipShape = other.clipShape.mask(base);
		}
		public PushFrame(PushFrame other) {
			this(other.baseClip, other);
		}
	}
	
	private final Renderable target;
	private final ArrayList<Shape> clipStack = new ArrayList();
	private final ArrayList<Transform> transformStack = new ArrayList();
	private final ArrayList<Instruction> instructions = new ArrayList();
	private final ArrayList<PushFrame> pushStack = new ArrayList();
	private PushFrame current;
	public Painter(Renderable renderable) {
		current = new PushFrame(renderable.shape());
		target = renderable;
	}
	
	public void push() {
		push(current.baseClip);
	}
	
	public void push(Shape shape) {
		shape = shape.mask(current.baseClip);
		current = new PushFrame(shape, current);
		pushStack.add(current);
	}
	
	public void pop() {
		current = pushStack.remove(pushStack.size()-1);
	}
	
	public void translate(float x, float y) {
	//	transform.translate(x, y);
	}

	public Instruction[] peekInstructions() {
		return instructions.toArray(new Instruction[instructions.size()]);
	}

	public Instruction[] takeInstructions() {
		try {
			return peekInstructions();
		} finally {
			instructions.clear();
		}
	}
	
	public void optimize() {
		target.optimize(instructions.listIterator());
	}

	public void send(boolean optimize) {
		if(optimize)
			optimize();
		
		target.pushRedraw(takeInstructions());
	}

	public void send() {
		send(true);
	}

	@Override
	protected void finalize() throws Throwable {
		if(!instructions.isEmpty())
			send();
	}
	
}
