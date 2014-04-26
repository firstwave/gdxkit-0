package io.firstwave.gdxkit;

/**
 * First version created on 3/29/14.
 */
public final class Entity {
	final EntityManager manager;
	public final int id;

	Entity(EntityManager manager, int id) {
		this.manager = manager;
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Entity[%d]", id);
	}

}
