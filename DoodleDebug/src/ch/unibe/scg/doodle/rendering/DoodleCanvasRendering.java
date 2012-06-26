package ch.unibe.scg.doodle.rendering;

import java.util.List;

import javax.inject.Inject;

import ch.unibe.ch.scg.htmlgen.Tag;
import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.Scratch;
import ch.unibe.scg.doodle.api.DoodleCanvas;

public class DoodleCanvasRendering implements Rendering<DoodleCanvas> {

	@Inject
	Doodler doodler;

	@Override
	public void render(DoodleCanvas canvas, Tag tag) {
		List<List<List<Object>>> columns = canvas.getColumns();

		for (List<List<Object>> column : columns) {
			Tag div = new Tag("div", "class=column");
			this.renderColumn(column, div);
			tag.add(div);
		}
	}

	private void renderColumn(List<List<Object>> column, Tag tag) {
		for (List<Object> line : column) {
			Tag div = new Tag("div", "class=line");
			this.renderLine(line, div);
			tag.add(div);
		}
	}

	private void renderLine(List<Object> line, Tag tag) {
		for (Object o : line) {
			Tag span = new Tag("span");
			doodler.renderInlineIntoWithoutClassName(o, span);
			tag.add(span);
		}
	}
}