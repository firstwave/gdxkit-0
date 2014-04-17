package io.firstwave.gdxkit;

import java.util.*;

/**
 * First version created on 3/30/14.
 */
public class Engine {
	public final EntityManager entityManager;
	private final List<BaseSystem> systems;
	private final Map<Class<? extends BaseSystem>, BaseSystem> systemsByType;
	private float lastDelta;

	private final Map<Class<?>, Object> resources;

	public Engine() {
		this(new EntityManager());
	}

	public Engine(EntityManager entityManager) {
		this.entityManager = entityManager;
		systems = new ArrayList<BaseSystem>();
		systemsByType = new HashMap<Class<? extends BaseSystem>, BaseSystem>();
		resources = new HashMap<Class<?>, Object>();
		lastDelta = 0.0f;
	}

	public void registerSystem(BaseSystem system) {
		if (systemsByType.containsKey(system.getClass())) {
			throw new IllegalStateException("Cannot register two systems of the same type!");
		}
		systemsByType.put(system.getClass(), system);
		systems.add(system);
		Collections.sort(systems);
		system.register(this);
	}

	public void unregisterSystem(Class<? extends BaseSystem> type) {
		BaseSystem system = systemsByType.get(type);
		if (system != null) {
			systemsByType.remove(type);
			system.unregister();
			systems.remove(system);
		} else {
			throw new NoSuchElementException("No system registered for type:" + type.getSimpleName());
		}
	}

	public <T extends BaseSystem> T getSystem(Class<T> type) {
		BaseSystem rv = systemsByType.get(type);
		if (rv == null) return null;
		return type.cast(rv);
	}

	public void updateSystems(float delta) {
		lastDelta = delta;
		for (BaseSystem s : systems) {
			s.update(delta);
		}
	}

	public void putResource(Object resource) {
		if (resources.containsKey(resources.getClass())) {
			throw new IllegalStateException("Cannot set two resources of the same type!");
		}
		resources.put(resource.getClass(), resource);
	}

	public <T> T getResource(Class<T> type) {
		Object rv = resources.get(type);
		if (rv == null) return null;
		return type.cast(rv);
	}

	public float getDelta() {
		return lastDelta;
	}
}
