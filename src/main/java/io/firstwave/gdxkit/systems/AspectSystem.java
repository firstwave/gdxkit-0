package io.firstwave.gdxkit.systems;

import io.firstwave.gdxkit.*;
import io.firstwave.gdxkit.util.Signal;

/**
 * First version created on 3/30/14.
 */
public abstract class AspectSystem extends EntitySystem {
	private View aspectView;
	private final Aspect aspect;
	private final Signal.Listener<Entity> entityAddedListener;
	private final Signal.Listener<Entity> entityRemovedListener;

	public AspectSystem(EntityManager manager, Aspect aspect) {
		super(manager);
		this.aspect = aspect;
		entityAddedListener = new Signal.Listener<Entity>() {
			@Override
			public void onBroadcast(Entity message) {
				onEntityAdded(message);
			}
		};
		entityRemovedListener = new Signal.Listener<Entity>() {
			@Override
			public void onBroadcast(Entity message) {
				onEntityRemoved(message);
			}
		};
	}

	@Override
	protected void onRegistered() {
		aspectView = entityManager.getView(aspect);
		for (Entity e : aspectView) {
			onEntityAdded(e);
		}
		aspectView.entityAdded.register(entityAddedListener);
		aspectView.entityRemoved.register(entityRemovedListener);
	}

	@Override
	protected void onUnregistered() {
		aspectView.entityAdded.unregister(entityAddedListener);
		aspectView.entityRemoved.unregister(entityRemovedListener);
		for (Entity e : aspectView) {
			onEntityRemoved(e);
		}
	}

	@Override
	public void onUpdate(float delta) {
		for (Entity e : aspectView) {
			processEntity(e, delta);
		}
	}

	public void onEntityAdded(Entity e) {

	}

	public void onEntityRemoved(Entity e) {

	}

	public abstract void processEntity(Entity e, float delta);
}
