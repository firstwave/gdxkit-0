package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Component;

/**
 * An Agent component is evaluated by nodes in a behavior tree.
 * First version created on 4/13/14.
 */
public final class Agent extends Component {
	public final String rootName;
	public final Blackboard blackboard;
	public Agent(String rootSelector) {
		this.rootName = rootSelector;
		blackboard = new Blackboard();
	}
}
