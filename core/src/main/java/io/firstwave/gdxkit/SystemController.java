package io.firstwave.gdxkit;

import java.util.*;

/**
 * First version created on 3/30/14.
 */
public class SystemController {
	private final List<BaseSystem> systems;
	private final Map<Class<? extends BaseSystem>, BaseSystem> systemsByType;
	private float lastDelta;

	public SystemController() {
		systems = new ArrayList<BaseSystem>();
		systemsByType = new HashMap<Class<? extends BaseSystem>, BaseSystem>();
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

	public void initialize() {
		for (BaseSystem s : systems) {
			s.initialize();
		}
	}

	public void updateSystems(float delta) {
		lastDelta = delta;
		for (BaseSystem s : systems) {
			s.update(delta);
		}
	}

	public float getDelta() {
		return lastDelta;
	}
}
