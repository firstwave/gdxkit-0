package io.firstwave.gdxkit;

import io.firstwave.gdxkit.util.Log;

/**
 * Created by obartley on 6/5/14.
 */
public class LoggingEntityObserver implements EntityObserver {

	private final String tag;

	public LoggingEntityObserver(String tag) {
		this.tag = tag;
	}

	@Override
	public void onEntityAdded(Entity e) {
		Log.d(tag, "onEntityAdded(%s)", e.toString());
	}

	@Override
	public void onEntityRemoved(Entity e) {
		Log.d(tag, "onEntityRemoved(%s)", e.toString());
	}

	@Override
	public void onComponentAdded(Entity e, Class<? extends Component> type) {
		Log.d(tag, "onComponentAdded(%s, %s)", e.toString(), type.getSimpleName());
	}

	@Override
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {
		Log.d(tag, "onBeforeComponentRemoved(%s, %s)", e.toString(), type.getSimpleName());
	}

	@Override
	public void onComponentRemoved(Entity e, Class<? extends Component> type) {
		Log.d(tag, "onComponentRemoved(%s, %s)", e.toString(), type.getSimpleName());
	}
}
