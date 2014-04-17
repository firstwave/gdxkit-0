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
	public State evaluate(Entity e, Agent a) {
		int cnt = count();
		int curr = a.blackboard.getInt(nodeId, 0);
		for (int i = curr; i < cnt; i++) {
			State s = get(i).evaluate(e, a);
			if (s == State.FAILURE) {
				a.blackboard.remove(nodeId);
				return State.FAILURE;
			} else if (s == State.RUNNING) {
				a.blackboard.putInt(nodeId, i);
				return s;
			}
		}
		return State.SUCCESS;
	}
}
