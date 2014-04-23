package io.firstwave.gdxkit.behavior;

/**
 * First version created on 4/13/14.
 */
public abstract class DecoratorNode<E> extends Node<E> {
	private final Node node;
	public DecoratorNode(Node node) {
		this.node = node;
	}

	@Override
	public final Status evaluate(E e, IBlackboard blackboard) {
		return evaluate(node, e, blackboard);
	}

	protected abstract Status evaluate(Node n, E e, IBlackboard blackboard);
}
