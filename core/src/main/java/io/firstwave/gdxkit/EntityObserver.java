package io.firstwave.gdxkit;

/**
 * Abstract class that implements observer interfaces for both EntityManager and ComponentManager
 * Note that this must be registered with both types to get all callbacks.
 * First version created on 4/26/14.
 */
public interface EntityObserver {

	/**
	 * Receive a notification when an Entity is created or otherwise added to an aspect View
	 * @param e
	 */
	public void onEntityAdded(Entity e);

	/**
	 * Receive a notification when an Entity is about to be destroyed or otherwise removed from an aspect View
	 * Note that this is called before all Components are removed.
	 *
	 * @param e
	 */
	public void onEntityRemoved(Entity e);

	/**
	 * This method is called AFTER the component has been added to the internal table.
	 * This will only be called if the entity did not have the component type prior to the change
	 * @param e
	 * @param type
	 */
	public void onComponentAdded(Entity e, Class<? extends Component> type);

	/**
	 * This method is called BEFORE the component is removed from the internal table.
	 * This allows a observers to perform cleanup that may be dependent on component data
	 * @param e
	 * @param type
	 */
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type);

	/**
	 * This method is called AFTER the component has been removed from the internal table.
	 * @param e
	 * @param type
	 */
	public void onComponentRemoved(Entity e, Class<? extends Component> type);
}
