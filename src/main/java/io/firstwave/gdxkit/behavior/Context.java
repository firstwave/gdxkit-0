package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Engine;

/**
 * First version created on 4/15/14.
 */
public class Context {
	private final Engine engine;
	private final BehaviorLibrary library;
	Context(Engine engine, BehaviorLibrary library) {
		this.engine = engine;
		this.library = library;
	}

	public Engine getEngine() {
		return engine;
	}

	public Node getRootNode(String key) {
		return library.get(key);
	}

	/**
	 * Traverse a behavior tree apply the given Context to all nodes
	 * @param node
	 */
	public static void updateTree(Context context, Node node) {
		if (node instanceof CompositeNode) {
			CompositeNode comp = (CompositeNode) node;
			int cnt = comp.count();
			for (int i = 0; i < cnt; i++) {
				updateTree(context, comp.get(i));
			}
		} else {
			node.setContext(context);
		}
	}
}
