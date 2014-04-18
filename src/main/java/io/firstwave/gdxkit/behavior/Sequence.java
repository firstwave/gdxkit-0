package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;

/**
 * Evaluates children sequentially until one returns FAILURE
 * First version created on 4/13/14.
 */
public class Sequence extends CompositeNode {
	/**
	 * Evaluate all nodes until a FAILURE is encountered.
	 * Will evaluate SUCCESS if all children are evaluated, otherwise returns FAILURE.
	 * An empty child list will always evaluate to SUCCESS
	 * If a child evaluates to RUNNING, the next update will resume evaluation at that child.
	 * @param e
	 * @param a
	 * @return
	 */
	@Override
	public Status evaluate(Entity e, Agent a) {
		int cnt = count();
		int curr = a.blackboard.getInt(nodeId, 0);
		for (int i = curr; i < cnt; i++) {
			Status s = get(i).evaluate(e, a);
			if (s == Status.FAILURE) {
				a.blackboard.remove(nodeId);
				return Status.FAILURE;
			} else if (s == Status.RUNNING) {
				a.blackboard.putInt(nodeId, i);
				return s;
			}
		}
		return Status.SUCCESS;
	}
}
