package io.firstwave.gdxkit.systems;

import io.firstwave.gdxkit.BaseSystem;
import io.firstwave.gdxkit.EntityManager;

/**
 * First version created on 4/25/14.
 */
public abstract class EntitySystem extends BaseSystem {
	protected final EntityManager entityManager;

	public EntitySystem(EntityManager manager) {
		this.entityManager = manager;
	}
}
