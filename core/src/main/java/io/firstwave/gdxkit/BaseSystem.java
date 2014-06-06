package io.firstwave.gdxkit;

import io.firstwave.gdxkit.util.Log;

/**
 * First version created on 3/30/14.
 */
public abstract class BaseSystem {
	private SystemController systemController;
	private String name;

	public BaseSystem() {
		// default no-arg constructor
	}

	public SystemController getController() {
		return systemController;
	}

	final void register(SystemController systemController) {
		this.systemController = systemController;
		Log.v(toString(), "registering system");
		onRegistered();
		Log.v(toString(), "system registered");
	}

	protected void onRegistered() {
		// no default behavior
	}

	final void initialize() {
		Log.v(toString(), "initializing system");
		onInitialized();
		Log.v(toString(), "system initialized");
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
		Log.v(toString(), "unregistering system");
		onUnregistered();
		Log.v(toString(), "system unregistered");
	}

	protected void onUnregistered() {
		// no default behavior
	}


	@Override
	public String toString() {
		if (name == null) {
			name = getClass().getSimpleName();
		}
		return name;
	}
}
