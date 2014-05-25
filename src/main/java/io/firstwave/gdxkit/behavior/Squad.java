package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Component;

/**
 * Component representing an Entity's association with a squad.
 * This is immutable to allow any changes to be monitored with a {@link io.firstwave.gdxkit.ComponentManager.Observer}
 * First version created on 4/25/14.
 */
public class Squad extends Component {
	/**
	 * The entity of this Squad's squad leader.
	 */
	public final int leaderId;

	public Squad(int leaderId) {
		this.leaderId = leaderId;
	}

}
