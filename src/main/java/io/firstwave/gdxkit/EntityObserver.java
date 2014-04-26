package io.firstwave.gdxkit;

/**
 * First version created on 4/26/14.
 */
public abstract class EntityObserver implements EntityManager.Observer, ComponentManager.Observer {

	public final EntityManager entityManager;

	public EntityObserver(EntityManager manager) {
		entityManager = manager;
		entityManager.addObserver(this);
		entityManager.componentManager.addObserver(this);
	}

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
