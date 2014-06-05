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

	public ResourceContext(ResourceContext context) {
		this.resources = context.resources;
	}

	public void putResource(Object resource) {
		if (resource == null) {
			throw new NullPointerException();
		}
		resources.put(resource.getClass(), resource);
	}

	public <T> T getResource(Class<T> type) {
		Object rv = resources.get(type);
		if (rv == null) return null;
		return type.cast(rv);
	}

	public <T> T removeResource(Class<T> type) {
		Object rv = resources.remove(type);
		if (rv == null) {
			return null;
		}
		return type.cast(rv);
	}
}
