package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Component;

/**
 * First version created on 4/25/14.
 */
public class SquadLeader extends Component {
	public final String rootName;
	public final Blackboard blackboard;
	public SquadLeader(String rootSelector) {
		this.rootName = rootSelector;
		blackboard = new Blackboard();
	}
}
