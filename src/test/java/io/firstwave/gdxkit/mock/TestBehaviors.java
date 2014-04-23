package io.firstwave.gdxkit.mock;

import io.firstwave.gdxkit.behavior.IBlackboard;
import io.firstwave.gdxkit.behavior.Node;

/**
 * First version created on 4/14/14.
 */
public interface TestBehaviors {

	public static class FailureNode extends Node {
		StringBuilder sb;
		String s;
		public FailureNode() {
			super();
		}

		public FailureNode(String s, StringBuilder sb) {
			this.s = s;
			this.sb = sb;
		}

		@Override
		public Status evaluate(Object o, IBlackboard bb) {
			if (sb == null) {
				System.out.println(getNodePath() + ":FAILURE");
			} else {
				sb.append(s);
			}
			return Status.FAILURE;
		}
	}

	public static class SuccessNode extends Node {
		StringBuilder sb;
		String s;

		public SuccessNode() {
			super();
		}

		public SuccessNode(String s, StringBuilder sb) {
			this.s = s;
			this.sb = sb;
		}

		@Override
		public Status evaluate(Object o, IBlackboard bb) {
			if (sb == null) {
				System.out.println(getNodePath() + ":SUCCESS");
			} else {
				sb.append(s);
			}
			return Status.SUCCESS;
		}
	}
}
