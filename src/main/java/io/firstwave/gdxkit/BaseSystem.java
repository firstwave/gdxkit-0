package io.firstwave.gdxkit;

/**
 * First version created on 3/30/14.
 */
public abstract class BaseSystem implements Comparable<BaseSystem> {
	protected int priority;
	protected Engine engine;

	public BaseSystem() {
		// default no-arg constructor
	}

	final void register(Engine engine) {
		this.engine = engine;
		onRegistered(engine);
	}

	protected void onRegistered(Engine engine) {
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
		this.engine = null;
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
