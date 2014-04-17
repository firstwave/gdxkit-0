package io.firstwave.gdxkit.util;

import com.badlogic.gdx.utils.ObjectIntMap;

/**
 * Provides a mapping of types to integers.
 * First version created on 3/29/14.
 */
public class TypeIndex<T> {
	// TODO: profile the impact of boxing/unboxing
	private final ObjectIntMap<Class<? extends T>> map;
	private int index;
	public TypeIndex() {
		map = new ObjectIntMap<Class<? extends T>>();
	}

	public int forType(Class<? extends T> type) {
		int rv = map.get(type, -1);
		if (rv == -1) {
			rv = index++;
			map.put(type, rv);
		}
		return rv;
	}

	private static final TypeIndex<Object> GLOBAL = new TypeIndex<Object>();
	public static int globalIndexForType(Class<?> type) {
		return GLOBAL.forType(type);
	}
}
