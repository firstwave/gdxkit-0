package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Component;
import io.firstwave.gdxkit.Entity;

/**
 * Decorates a node and only allows entities with a specific type to be evaluated.
  * First version created on 4/16/14.
 */
public class ComponentDecorator extends DecoratorNode {
	public final Class<? extends Component> type;
	public ComponentDecorator(Class<? extends Component> type, Node node) {
		super(node);
		this.type = type;
	}

	/**
	 * Evaluate the given entity with the given node only if the entity has the component specified at construction.
	 * If the entity does not have the correct component type, the decorated node will not be evaluated and FAILURE will be returned instead.
	 * @param n
	 * @param e
	 * @param a
	 * @return
	 */
	@Override
	protected Status evaluate(Node n, Entity e, Agent a) {
		if (e.hasComponent(type)) {
			return n.evaluate(e, a);
		} else {
			return Status.FAILURE;
		}
	}
}
