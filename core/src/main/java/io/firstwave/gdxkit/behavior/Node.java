package io.firstwave.gdxkit.behavior;

/**
 * First version created on 4/13/14.
 */
public abstract class Node<E> {
	public static enum Status {
		/**
		 * Indicate that the current evaluation should continue on the next update.
		 * Parent nodes (composite) should return evaluation to a RUNNING child on evaluation.
		 */
		RUNNING,
		/**
		 * Indicate the evaluation has succeeded.
		 */
		SUCCESS,
		/**
		 * Indicate failure.
		 */
		FAILURE
	}

	public static final String NODE_PATH_SEPARATOR = ":";
	public static final String ROOT_NODE_PATH = "~";

	private Node parent;
	private String nodePath;

	/**
	 * Construct a new Node instance.
	 */
	public Node() {}

	/**
	 * Attach this node to a parent CompositeNode.
	 * @param parent
	 */
	void attach(CompositeNode parent) {
		if (this.parent != null) {
			throw new IllegalStateException("this Node is already attached to a CompositeNode");
		} else {
			this.parent = parent;
			nodePath = parent.getNodePath() +
					NODE_PATH_SEPARATOR +
					Integer.toString(parent.indexOf(this));
		}
	}

	void detach() {
		parent = null;
		nodePath = null;
	}

	/**
	 * Return a unique path identifying this node within a tree. This path is unique to this node's position within a tree
	 * and can be used to store node-specific values in an evaluated blackboard.
	 * A node that has not been attached to a parent composite node will return ROOT_NODE_PATH
	 * @return
	 */
	public String getNodePath() {
		return (nodePath == null) ? ROOT_NODE_PATH : nodePath;
	}

	@Override
	public String toString() {
		return super.toString() + " [" + getNodePath() + "]";
	}

	/**
	 * The evaluate method contains the evaluation logic for each node.
	 */
	public abstract Status evaluate(E e, IBlackboard blackboard);


}
