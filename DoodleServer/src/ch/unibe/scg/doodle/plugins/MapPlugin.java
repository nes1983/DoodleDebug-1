package ch.unibe.scg.doodle.plugins;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.unibe.scg.doodle.htmlgen.Tag;
import ch.unibe.scg.doodle.rendering.DoodleRenderException;
import ch.unibe.scg.doodle.rendering.MapRendering;

public class MapPlugin extends AbstractPlugin {

	@Inject
	Provider<MapRendering> mapRenderingProvider;

	@Override
	public Set<Class<?>> getDrawableClasses() {
		HashSet<Class<?>> hs = new HashSet<Class<?>>();
		hs.add(Map.class);
		return hs;
	}

	@Override
	public void render(Object object, Tag tag) throws DoodleRenderException {
		mapRenderingProvider.get().render((Map<?, ?>) object, tag);
	}

	@Override
	public void renderSmall(Object object, Tag tag)
			throws DoodleRenderException {
		mapRenderingProvider.get().renderSmall((Map<?, ?>) object, tag);
	}

	@Override
	public String getCSS() {
		String all = ".MapPlugin .mappingKey, .MapPlugin .mappingArrow, .MapPlugin .mappingValue {display: inline-block; float: none;}";
		String key = ".MapPlugin .mappingKey {}";
		String arrow = ".MapPlugin .mappingArrow {position: relative; top: -4pt}";
		String value = ".MapPlugin .mappingValue {}";
		return all + key + arrow + value;
	}

	@Override
	public String getObjectTypeName(Object mapObject) {
		Map<?, ?> map = (Map<?, ?>) mapObject;
		if (CollectionPlugin.checkIfElementsSameType(map.keySet())
				&& !map.isEmpty())
			return super.getObjectTypeName(mapObject) + ": " + keyType(map)
					+ " &rarr; " + valueType(map);
		return super.getObjectTypeName(mapObject);
	}

	private String keyType(Map<?, ?> map) {
		Set<?> keySet = map.keySet();
		if (keySet.isEmpty())
			return "(unknown)";
		Object first = keySet.iterator().next();
		return first.getClass().getSimpleName();
	}

	private String valueType(Map<?, ?> map) {
		Collection<?> values = map.values();
		if (values.isEmpty())
			return "(unknown)";
		Object first = values.iterator().next();
		return first.getClass().getSimpleName();
	}
}
