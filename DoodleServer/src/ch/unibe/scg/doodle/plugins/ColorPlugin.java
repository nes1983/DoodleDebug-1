package ch.unibe.scg.doodle.plugins;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.ColorRendering;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;

public class ColorPlugin extends AbstractPlugin {

	@Inject
	Provider<ColorRendering> colorRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Color.class);
		return hs;
	}

	@Override
	public void render(Object color, Tag tag) {
		colorRenderingProvider.get().render((Color) color, tag);
	}

	@Override
	public void renderSmall(Object color, Tag tag) {
		colorRenderingProvider.get().renderSmall((Color) color, tag);
	}

	@Override
	public String getCSS() {
		return ".ColorPlugin {padding:10pt;} "
				+ ".ColorPlugin.smallRendering {padding:4pt}";
	}

}
