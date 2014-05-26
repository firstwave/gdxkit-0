package io.firstwave.gdxkit;

import io.firstwave.gdxkit.mock.MockComponents;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * First version created on 4/3/14.
 */
public class ViewTest implements MockComponents {
	private EntityManager em;
	private View v;

	@Before
	public void setUp() {
		em = new EntityManager();
	}

	@Test
	public void testEmpty() {
		v = em.getView(Aspect.getAspectForOne(C1_TYPE));
		int j = 0;
		for (Entity e: v) {
			j++;
		}
		assertEquals(0, j);
	}


	@Test
	public void testInsert() {
		v = em.getView(Aspect.getAspectForOne(C1_TYPE));
		Entity e;
		for (int i = 0; i < 10; i++) {
			e = em.createEntity();
			em.componentManager.setEntityComponent(e, C1);
		}
		int j = 0;
		for (int i = 0; i < 10; i++) {
			e = em.createEntity();
			em.componentManager.setEntityComponent(e, C2);
			j++;
		}
		assertEquals(10, v.count());
		assertEquals(j, v.count());
	}

	@Test
	public void testInsertOnIterate() {
		v = em.getView(Aspect.getAspectForOne(C1_TYPE));
		Entity e;
		for (int i = 0; i < 10; i++) {
			e = em.createEntity();
			em.componentManager.setEntityComponent(e, C1);
		}
		for (Entity ent: v) {
			e = em.createEntity();
			em.componentManager.setEntityComponent(e, C1);
		}
		assertEquals(20, v.count());
	}

	@Test
	public void testRemove() {
		v = em.getView(Aspect.getAspectForOne(C1_TYPE));
		Entity e;
		for (int i = 0; i < 10; i++) {
			e = em.createEntity();
			em.componentManager.setEntityComponent(e, C1);
		}

		for (Entity ent: v) {
			em.componentManager.removeAllEntityComponents(ent);
		}
		assertEquals(0, v.count());
	}

}
