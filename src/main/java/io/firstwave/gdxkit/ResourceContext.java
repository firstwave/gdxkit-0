package io.firstwave.gdxkit;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a mechanism to share resources between various engine components
 * First version created on 4/25/14.
 */
public class ResourceContext {
	private final Map<Class<?>, Object> resources;

	public ResourceContext() {
		resources = new HashMap<Class<?>, Object>();
	}

	public void putResource(Object resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		if (resources.containsKey(resource.getClass())) {
			throw new IllegalArgumentException("ResourceContext can't manage two resources of the same type!");
		}
		resources.put(resource.getClass(), resource);
	}

	public <T> T getResource(Class<T> type) {
		Object rv = resources.get(type);
		if (rv == null) return null;
		return type.cast(rv);
	}
}
