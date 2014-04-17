package io.firstwave.gdxkit.util;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Signal represents an event or message broadcast to multiple listeners.
 * Objects implementing the {@link io.firstwave.gdxkit.util.Signal.Listener} interface are registered via the
 * {@link #register(io.firstwave.gdxkit.util.Signal.Listener)}.
 * {@link java.lang.ref.WeakReference} is used to retain references to registered listeners so that it is unnecessary
 * to manually unregister them, unless you wish to stop receiving broadcast while still keeping a reference yourself.
 */
public class Signal<T> {
	private final LinkedList<WeakReference<Listener<T>>> listeners;
	public Signal() {
		listeners = new LinkedList<WeakReference<Listener<T>>>();
	}

	public void register(Listener<T> listener) {
		for (WeakReference<Listener<T>> ref : listeners) {
			if (ref.get() == listener) return;
		}
		listeners.add(new WeakReference<Listener<T>>(listener));
	}

	public void unregister(Listener<T> listener) {
		Iterator<WeakReference<Listener<T>>> iter = listeners.iterator();
		while (iter.hasNext()) {
			if (iter.next().get() == listener) {
				iter.remove();
			}
		}
	}

	public void broadcast(T message) {
		Iterator<WeakReference<Listener<T>>> iter = listeners.iterator();
		Listener<T> listener;
		while (iter.hasNext()) {
			listener = iter.next().get();
			if (listener == null) {
				iter.remove();
			} else {
				listener.onBroadcast(message);
			}
		}
	}

	/**
	 * A Void implementation of a Signal. Use this instead of null to avoid NullPointerExceptions.
	 * All superclass methods are overridden and do nothing.
	 * @param <T>
	 */
	public static class VoidSignal<T> extends Signal<T> {
		@Override
		public void register(Listener<T> listener) {
			// do nothing
		}

		@Override
		public void unregister(Listener<T> listener) {
			// do nothing
		}

		@Override
		public void broadcast(T message) {
			// do nothing
		}
	}

	public static interface Listener<T> {
		public void onBroadcast(T message);
	}
}
