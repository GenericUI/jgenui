package net.nexustools.gui.impl;

import net.nexustools.gui.geom.Point;
import net.nexustools.utils.Creator;

public interface CellWidget<I> extends Widget {
	
	public static interface Cell {
		public Point pos();
	}
	
	public Widget header();
	public void setHeader(Widget header);
	
	public Widget footer();
	public void setFooter(Widget header);
	
	public Creator<Cell, I> cellCreator();
	public void setCellCreator(Creator<Cell, I> cellCreator);

}
