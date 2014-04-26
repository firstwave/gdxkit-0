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
	 * Check if this Entity is enabled or not
	 * @return true if the entity is enabled
	 */
	public boolean isEnabled() {
		return manager.isEntityEnabled(this);
	}

	/**
	 * Set if this Entity is enabled or not
	 * @param enabled true to enable, false to disable
	 */
	public void setEnabled(boolean enabled) {
		manager.setEntityEnabled(this, enabled);
	}

	/**
	 * Check if this Entity has been destroyed
	 * @return true if the Entity has been destroyed
	 */
	public boolean isDestroyed() {
		return manager.isEntityDestroyed(this);
	}

	/**
	 * Destroy this entity and remove all components
	 */
	public void destroy() {
		manager.destroyEntity(this);
	}

	/**
	 * Check if this Entity has a Component of the given type. It's usually a better idea to
	 * simply getComponent and then check for null.
	 * @param type
	 * @return
	 */
	public boolean hasComponent(Class<? extends Component> type) {
		return manager.componentManager.entityHasComponent(this, type);
	}

	/**
	 * Return the Component of the given type. Will return null if the Entity does not have the given component
	 * @param type
	 * @param <T>
	 * @return
	 */
	public <T extends Component> T getComponent(Class<T> type) {
		return manager.componentManager.getEntityComponent(this, type);
	}

	/**
	 * Add or Update an Entity Component
	 * @param component
	 */
	public void setComponent(Component component) {
		manager.componentManager.setEntityComponent(this, component);
	}

	@Override
	public String toString() {
		return String.format("Entity[%d]", id);
	}

}
