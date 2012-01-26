package rendering;

import html_generator.Attributes;
import html_generator.Tag;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import view.DoodleCanvas;
import view.Rect;
import doodle.RealScratch;
import doodle.Scratch;

public class ScratchRendering implements Rendering<Scratch> {

	@Override
	public void render(Scratch scratch, Tag tag) {
		List<List<List<Scratch>>> columns = scratch.getColumns();
		
		for (List<List<Scratch>> column : columns) {
			Tag div = new Tag("div", "class=column");
			this.renderColumn(column, div);
			tag.add(div);
		}
	}

	private void renderColumn(List<List<Scratch>> column, Tag tag) {
		for (List<Scratch> line : column) {
			Tag div = new Tag("div");
			this.renderLine(line, div);
			tag.add(div);
		}
	}

	private void renderLine(List<Scratch> line, Tag tag) {
		for (Scratch scratch : line) {
			Tag span = new Tag("span");
			scratch.drawWhole(span);
			tag.add(span);
		}
	}
}
