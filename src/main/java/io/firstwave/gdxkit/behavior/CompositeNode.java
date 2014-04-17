package io.firstwave.gdxkit.behavior;

import com.badlogic.gdx.utils.Array;

/**
 * First version created on 4/13/14.
 */
public abstract class CompositeNode extends Node {
	private Array<Node> children  = new Array<Node>();;

	public int count() {
		return children.size;
	}

	/**
	 * Append the given child to the list of child nodes
	 * @param child
	 */
	public void add(Node child) {
		for (Node n : children) {
			if (child.nodeId.equals(n.nodeId)) {
				throw new IllegalArgumentException("Duplicate node id '" + child.nodeId);
			}
		}
		children.add(child);
	}

	/**
	 * Return the child node at the given index
	 * @param index
	 * @return
	 */
	public Node get(int index) {
		return children.get(index);
	}

	/**
	 * Remove all children from the list
	 */
	public void clear() {
		children.clear();
	}
}
