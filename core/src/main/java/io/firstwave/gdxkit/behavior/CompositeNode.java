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
		children.add(child);
		// ALWAYS attach after adding
		// when attach is called, indexOf will be called. If the child hasn't been added, then the path will be wrong.
		child.attach(this);
	}

	/**
	 * Returns an index of first occurrence of child in array or -1 if no such value exists
	 * @param child
	 * @return
	 */
	public int indexOf(Node child) {
		return children.indexOf(child, true);
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
	 * Remove all children from the list.
	 * This will COMPLETELY BREAK all deterministic behavior of a tree if it is invoked after evaluation has begun.
	 * Seriously, DO NOT DO THIS once you have evaluated this node. You will make me sad if you do.
	 */
	public void clear() {
		for (Node n : children) {
			n.detach();
		}
		children.clear();
	}
}
