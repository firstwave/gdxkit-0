package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;

/**
 * Evaluates children sequentially until one return SUCCESS
 * First version created on 4/13/14.
 */
public class Selector extends CompositeNode {
	/**
	 * Evaluates all children until one returns SUCCESS.
	 * If all children Fail, then return FAILURE.
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
			if (s == State.SUCCESS) {
				a.blackboard.remove(nodeId);
				return State.SUCCESS;
			} else if (s == State.RUNNING) {
				a.blackboard.putInt(nodeId, i);
				return s;
			}
		}
		return State.FAILURE;
	}
}
