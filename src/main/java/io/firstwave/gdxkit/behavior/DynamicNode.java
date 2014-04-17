package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;

/**
 * Represents a section of a tree that is evaluated by a separate tree. The root of the subtree is determined by
 * checking the content's of an agent's blackboard, using the key supplied at construction. The key will then be used
 * to lookup a tree in the behavior library given by the current tree's context.
 * First version created on 4/16/14.
 */
public class DynamicNode extends Node {
	public final String blackboardKey;
	public DynamicNode(String blackboardKey) {
		this.blackboardKey = blackboardKey;
	}

	@Override
	public State evaluate(Entity e, Agent a) {
		Node root = getContext().getRootNode(a.blackboard.getString(blackboardKey));
		if (root == null) {
			return State.FAILURE;
		}
		if (root.getContext() != getContext()) {
			Context.updateTree(getContext(), root);
		}
		return root.evaluate(e, a);
	}
}
