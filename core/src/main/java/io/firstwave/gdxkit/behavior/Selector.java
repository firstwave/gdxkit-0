package io.firstwave.gdxkit.behavior;

/**
 * Evaluates children sequentially until one return SUCCESS
 * First version created on 4/13/14.
 */
public class Selector extends CompositeNode {
	/**
	 * Evaluates all children until one returns SUCCESS.
	 * If all children Fail, then return FAILURE.
	 * @param o
	 * @param blackboard
	 * @return
	 */
	@Override
	public Status evaluate(Object o, IBlackboard blackboard) {
		int cnt = count();
		int curr = blackboard.getInt(getNodePath(), 0);
		for (int i = curr; i < cnt; i++) {
			Status s = get(i).evaluate(o, blackboard);
			if (s == Status.SUCCESS) {
				blackboard.remove(getNodePath());
				return Status.SUCCESS;
			} else if (s == Status.RUNNING) {
				blackboard.putInt(getNodePath(), i);
				return s;
			}
		}
		return Status.FAILURE;
	}
}
