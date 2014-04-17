package io.firstwave.gdxkit.util;

import com.badlogic.gdx.utils.IntArray;

/**
 * First version created on 3/30/14.
 */
public class IdPool {
	private int next = 0;
	private final IntArray pool;

	public IdPool() {
		pool = new IntArray(false, 64);
	}

	public int get() {
		if (pool.size == 0) return next++;
		return pool.pop();
	}

	public void release(int i) {
		if (i >= next) throw new IllegalArgumentException(i + " is outside the current range of this pool [0-" + (next - 1) + "]");
		pool.add(i);
	}
}
