package io.firstwave.gdxkit;

/**
 * Abstract class that implements observer interfaces for both EntityManager and ComponentManager
 * Note that this must be registered with both types to get all callbacks.
 * First version created on 4/26/14.
 */
public abstract class EntityComponentObserver implements EntityManager.Observer, ComponentManager.Observer {

	@Override
	public void onComponentAdded(Entity e, Class<? extends Component> type) {

	}

	@Override
	public void onComponentUpdated(Entity e, Class<? extends Component> type) {

	}

	@Override
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {

	}

	@Override
	public void onComponentRemoved(Entity e, Class<? extends Component> type) {

	}

	@Override
	public void onEntityCreated(Entity e) {

	}

	@Override
	public void onEntityDestroyed(Entity e) {

	}
}
