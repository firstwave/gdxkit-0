package io.firstwave.gdxkit.behavior;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Provides an interface to a map of Strings to root Nodes in a behavior tree.
 * First version created on 4/16/14.
 */
public class BehaviorLibrary {
	private final ObjectMap<String, Node> roots;

	public BehaviorLibrary() {
		roots = new ObjectMap<String, Node>();
	}

	public void put(String key, Node node) {
		roots.put(key, node);
	}

	public Node get(String key) {
		return get(key, null);
	}

	public Node get(String key, Node defaultValue) {
		return roots.get(key, defaultValue);
	}

	public boolean containsKey(String key) {
		return roots.containsKey(key);
	}

	public int count() {
		return roots.size;
	}

	protected void clear() {
		roots.clear();
	}
}
