package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;

import java.util.UUID;

/**
 * First version created on 4/13/14.
 */
public abstract class Node {
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

	/**
	 * Each node has access to a unique String id. This id can be used to store node-specific state on a per-agent
	 * basis in an agent's blackboard
	 */
	protected final String nodeId;

	private Context context;

	/**
	 * Construct a new Node instance.
	 */
	public Node() {
		nodeId = UUID.randomUUID().toString();
	}


	void setContext(Context context) {
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	/**
	 * The evaluate method contains the evaluation logic for each node.
	 * @param e
	 * @param a
	 * @return
	 */
	public abstract Status evaluate(Entity e, Agent a);


}
