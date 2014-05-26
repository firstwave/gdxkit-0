package io.firstwave.gdxkit;

/**
 * Enable tests to create entities on the fly using the package private Entity constructor.
 * First version created on 4/16/14.
 */
public class EntityFactory {
	public static Entity create(EntityManager mgr, int id) {
		return new Entity(mgr, id);
	}
}
