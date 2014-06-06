package io.firstwave.gdxkit.systems;

import io.firstwave.gdxkit.*;
import io.firstwave.gdxkit.util.Log;
import io.firstwave.gdxkit.util.Signal;

/**
 * First version created on 3/30/14.
 */
public abstract class AspectSystem extends BaseSystem implements EntityObserver {
	private static final String TAG = AspectSystem.class.getSimpleName();
	private View aspectView;
	private final Aspect aspect;
	private boolean initialized = false;

	public AspectSystem(Aspect aspect) {
		this.aspect = aspect;
	}

	@Override
	protected void onInitialized() {
		aspectView = getController().getResource(EntityManager.class).getView(aspect);
		aspectView.addObserver(this);
		aspectView.addObserver(new LoggingEntityObserver(TAG));

		for (Entity e : aspectView) {
			onEntityAdded(e);
		}
		initialized = true;
	}

	@Override
	protected void onUnregistered() {
		for (Entity e : aspectView) {
			onEntityRemoved(e);
		}
		aspectView = null;
		initialized = false;
	}

	@Override
	public void onUpdate(float delta) {
		if (!initialized) {
			throw new IllegalStateException("You must call super.initialize() before updating this system!");
		}
		for (Entity e : aspectView) {
			processEntity(e, delta);
		}
	}

	public abstract void processEntity(Entity e, float delta);

	@Override
	public void onEntityAdded(Entity e) {

	}

	@Override
	public void onEntityRemoved(Entity e) {

	}

	@Override
	public void onComponentAdded(Entity e, Class<? extends Component> type) {

	}

	@Override
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {

	}

	@Override
	public void onComponentRemoved(Entity e, Class<? extends Component> type) {

	}
}
