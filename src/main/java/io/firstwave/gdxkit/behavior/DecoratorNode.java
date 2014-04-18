package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;

/**
 * First version created on 4/13/14.
 */
public abstract class DecoratorNode extends Node {
	private final Node node;
	public DecoratorNode(Node node) {
		this.node = node;
	}

	@Override
	public final Status evaluate(Entity e, Agent a) {
		return evaluate(node, e, a);
	}

	protected abstract Status evaluate(Node n, Entity e, Agent a);
}
