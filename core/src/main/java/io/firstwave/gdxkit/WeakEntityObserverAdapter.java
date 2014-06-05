package io.firstwave.gdxkit;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Implements a one-to-many interface for a WeakReferenced set of EntityObservers listening to a single source of events
 * First version created on 5/24/14.
 */
public class WeakEntityObserverAdapter implements EntityObserver {
	private static final Object VALUE = new Object();
	private final Map<EntityObserver, Object> observers = new WeakHashMap<EntityObserver, Object>();

	public void addObserver(EntityObserver o) {
		observers.put(o, VALUE);
	}

	public boolean removeObserver(EntityObserver o) {
		return (observers.remove(o) == VALUE);
	}

	@Override
	public void onEntityAdded(Entity e) {
		for (EntityObserver o : observers.keySet()) {
			o.onEntityAdded(e);
		}
	}

	@Override
	public void onEntityRemoved(Entity e) {
		for (EntityObserver o : observers.keySet()) {
			o.onEntityRemoved(e);
		}
	}

	@Override
	public void onComponentAdded(Entity e, Class<? extends Component> type) {
		for (EntityObserver o : observers.keySet()) {
			o.onComponentAdded(e, type);
		}
	}

	@Override
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {
		for (EntityObserver o : observers.keySet()) {
			o.onBeforeComponentRemoved(e, type);
		}
	}

	@Override
	public void onComponentRemoved(Entity e, Class<? extends Component> type) {
		for (EntityObserver o : observers.keySet()) {
			o.onComponentRemoved(e, type);
		}
	}
}
