package io.firstwave.gdxkit;

import io.firstwave.gdxkit.mock.MockComponents;
import io.firstwave.gdxkit.test.TestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * First version created on 3/30/14.
 */
@RunWith(TestRunner.class)
public class AspectTest implements MockComponents {
	@Test
	public void testCheck() throws Exception {
		EntityManager em = new EntityManager();
		Entity e = em.createEntity();

		em.componentManager.setEntityComponent(e, C1);
		em.componentManager.setEntityComponent(e, C2);

		Aspect a = Aspect.getAspectForAll(C1_TYPE, C2_TYPE);
		assertTrue(a.check(e));

		em.componentManager.removeEntityComponent(e, C2_TYPE);
		assertFalse(a.check(e));

		em.componentManager.setEntityComponent(e, C2);

		a = Aspect.getAspectForOne(C3_TYPE);
		assertFalse(a.check(e));

		a = Aspect.getAspectForOne(C1_TYPE, C3_TYPE);
		assertTrue(a.check(e));
		a.exclude(C2_TYPE);
		assertFalse(a.check(e));
	}
}
