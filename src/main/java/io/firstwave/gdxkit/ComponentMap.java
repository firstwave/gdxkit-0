package io.firstwave.gdxkit;

/**
 * First version created on 3/30/14.
 */
public class ComponentMap<T extends Component> {
	private final ComponentManager manager;
	private final Class<T> type;

	ComponentMap(ComponentManager manager, Class<T> type) {
		this.manager= manager;
		this.type = type;
	}

	public boolean has(Entity e) {
		return manager.entityHasComponent(e, type);
	}

	@SuppressWarnings("unchecked")
	public T get(Entity e) {
		return type.cast(manager.getEntityComponent(e, type));
	}

	/**
	 * The main difference between this and get is that safeGet will check the recently removed cache if the component is not
	 * found in the main component table. The component manager caches the last component removed from an entity, so use
	 * this if you need to do cleanup in a component removed event.
	 * @param e
	 * @return
	 */
	public T getSafe(Entity e) {
		Component rv = manager.getEntityComponent(e, type);
		if (rv != null) return type.cast(rv);
		// now we check the cache to see if there's one hanging around in the cache
		rv = manager.getLastRemovedEntityComponent(e);
		if (rv != null && type.isInstance(rv)) {
			return type.cast(rv);
		}
		return null;
	}
}
