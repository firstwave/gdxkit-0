package io.firstwave.gdxkit;

/**
 * First version created on 3/30/14.
 */
public abstract class BaseSystem implements Comparable<BaseSystem> {
	protected int priority;
	private SystemController systemController;

	public BaseSystem() {
		// default no-arg constructor
	}

	public SystemController getController() {
		return systemController;
	}

	final void register(SystemController systemController) {
		this.systemController = systemController;
		onRegistered();
	}

	protected void onRegistered() {
		// no default behavior
	}

	final void initialize() {
		onInitialized();
	}

	protected void onInitialized() {
		// no default behavior
	}

	final void update(float delta) {
		if (checkUpdate(delta)) {
			onUpdate(delta);
		}
	}

	protected boolean checkUpdate(float delta) {
		return true;
	}

	protected abstract void onUpdate(float delta);

	final void unregister() {
		this.systemController = null;
		onUnregistered();
	}

	protected void onUnregistered() {
		// no default behavior
	}

	@Override
	public final int compareTo(BaseSystem o) {
		return Integer.compare(priority, o.priority);
	}
}
