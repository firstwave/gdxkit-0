package io.firstwave.gdxkit;

/**
 * Slightly simplifies the code needed to fetch Component af a specific type.
 * Rather than having to type:
 * <tt>Entity#getComponent(Component.class)</tt>
 * you can simply create a typed map, initialize it with a component manager and type:
 * <tt>component.get(Entity)</tt>
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

}
