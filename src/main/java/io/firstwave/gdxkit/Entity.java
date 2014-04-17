package io.firstwave.gdxkit;

/**
 * First version created on 3/29/14.
 */
public final class Entity {
	final EntityManager manager;
	public final int id;

	Entity(EntityManager manager, int id) {
		this.manager = manager;
		this.id = id;
	}

	/**
	 * Check whether this entity has a component of the given type
	 * @param type
	 * @return
	 */
	public boolean hasComponent(Class<? extends Component> type) {
		return manager.componentManager.entityHasComponent(this, type);
	}

	@Override
	public String toString() {
		return String.format("Entity[%d]", id);
	}

}
