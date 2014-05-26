package io.firstwave.gdxkit.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * First version created on 3/30/14.
 */
public class SignalTest {

	private Signal<String> sig;
	private StringBuilder sb;

	@Before
	public void setUp() {
		sig = new Signal<String>();
		sb = new StringBuilder();
	}

	@Test
	public void testRegistration() throws Exception {

		sig.register(new Signal.Listener<String>() {
			@Override
			public void onBroadcast(String message) {
				sb.append(message);
			}
		});
		Signal.Listener<String> l = new Signal.Listener<String>() {
			@Override
			public void onBroadcast(String message) {
				sb.append(message);
			}
		};
		sig.register(l);

		sig.broadcast("a");
		sig.broadcast("b");
		sig.unregister(l);
		sig.broadcast("c");
		assertEquals("aabbc", sb.toString());
	}

	@Test
	public void testBroadcast() throws Exception {
		sig.register(new Signal.Listener<String>() {
			@Override
			public void onBroadcast(String message) {
				sb.append(message);
			}
		});
		sig.broadcast("foo");
		assertEquals("foo", sb.toString());
	}

	@Test
	public void testLeakProof() {
		registerLocalListenersAndBroadcast("foo");
		System.gc();
		sig.register(new InnerListener());
		sig.broadcast("bar");
		assertEquals("foofoobar", sb.toString());
	}

	private void registerLocalListenersAndBroadcast(String msg) {
		Signal.Listener<String> l = new Signal.Listener<String>() {
			@Override
			public void onBroadcast(String message) {
				sb.append(message);
			}
		};
		sig.register(l);
		sig.register(new InnerListener());
		sig.broadcast(msg);
	}

	private class InnerListener implements Signal.Listener<String> {
		@Override
		public void onBroadcast(String message) {
			sb.append(message);
		}
	}
}
