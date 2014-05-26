package io.firstwave.gdxkit;

import io.firstwave.gdxkit.mock.MockComponents;
import io.firstwave.gdxkit.test.TestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * First version created on 3/30/14.
 */
@RunWith(TestRunner.class)
public class EntityManagerTest {

	private EntityManager em;

	@Before
	public void setUp() {
		em = new EntityManager();
		System.gc();
	}

	@Test
	public void testGetEntity() throws Exception {
		assertNull(em.getEntity(42));
		em.createEntity(); // entity 0
		assertNull(em.getEntity(42));
		assertNotNull(em.getEntity(0));
	}

	@Test
	public void testCreateEntity() throws Exception {
		Entity e = em.createEntity();
		assertEquals(0, e.id);
		e = em.createEntity();
		assertEquals(1, e.id);
		assertEquals(2, em.getEntityCount());
	}

	@Test
	public void testDestroyEntity() throws Exception {
		Entity e = em.createEntity();
		em.createEntity();
		em.destroyEntity(e); // entities without components put have not had an opportunity to properly initialize the component table, check for NPEs

		// make sure that disabled entities with components are properly reset
		e = em.createEntity();
		em.componentManager.setEntityComponent(e, new MockComponents.MockComponent1());
		em.setEntityEnabled(e, false);
		em.destroyEntity(e);
		e = em.createEntity();

		assertTrue(em.isEntityEnabled(e));
		assertEquals(0, em.componentManager.getAllEntityComponents(e).length);
		assertEquals(0, e.id);
		assertEquals(2, em.getEntityCount());
	}

	@Test
	public void testEntityEnabled() throws Exception {
		Entity e = em.createEntity();
		assertTrue(em.isEntityEnabled(e));
		em.setEntityEnabled(e, false);
		assertFalse(em.isEntityEnabled(e));
		assertEquals(1, em.getDisabledEntityCount());
	}

}
