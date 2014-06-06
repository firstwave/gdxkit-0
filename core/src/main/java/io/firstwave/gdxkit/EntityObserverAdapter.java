package io.firstwave.gdxkit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Implements a one-to-many interface for a WeakReferenced set of EntityObservers listening to a single source of events
 * First version created on 5/24/14.
 */
public class EntityObserverAdapter implements EntityObserver {
	private final Set<EntityObserver> observers = new HashSet<EntityObserver>();

	public void addObserver(EntityObserver o) {
		observers.add(o);
	}

	public boolean removeObserver(EntityObserver o) {
		return observers.remove(o);
	}

	@Override
	public void onEntityAdded(Entity e) {
		for (EntityObserver o : observers) {
			o.onEntityAdded(e);
		}
	}

	@Override
	public void onEntityRemoved(Entity e) {
		for (EntityObserver o : observers) {
			o.onEntityRemoved(e);
		}
	}

	@Override
	public void onComponentAdded(Entity e, Class<? extends Component> type) {
		for (EntityObserver o : observers) {
			o.onComponentAdded(e, type);
		}
	}

	@Override
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {
		for (EntityObserver o : observers) {
			o.onBeforeComponentRemoved(e, type);
		}
	}

	@Override
	public void onComponentRemoved(Entity e, Class<? extends Component> type) {
		for (EntityObserver o : observers) {
			o.onComponentRemoved(e, type);
		}
	}

	public int getCount() {
		return observers.size();
	}
}
