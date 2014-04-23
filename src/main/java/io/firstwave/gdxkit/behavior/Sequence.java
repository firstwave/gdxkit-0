package io.firstwave.gdxkit.behavior;

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
			if (s == Status.FAILURE) {
				blackboard.remove(getNodePath());
				return Status.FAILURE;
			} else if (s == Status.RUNNING) {
				blackboard.putInt(getNodePath(), i);
				return s;
			}
		}
		return Status.SUCCESS;
	}
}
