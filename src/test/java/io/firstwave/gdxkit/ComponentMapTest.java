package io.firstwave.gdxkit;

import io.firstwave.gdxkit.mock.MockComponents;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * First version created on 3/31/14.
 */
public class ComponentMapTest implements MockComponents {

	Entity e;
	ComponentManager cm;
	ComponentMap<MockComponent1> map;

	@Before
	public void setUp() {
		e = new Entity(null, 2);
		cm = new ComponentManager();
		map = cm.getComponentMap(MockComponent1.class);
	}

	@Test
	public void testHas() throws Exception {
		assertFalse(map.has(e));
		cm.setEntityComponent(e, C1);
		assertTrue(map.has(e));
		cm.removeAllEntityComponents(e);
		assertFalse(map.has(e));
	}

	@Test
	public void testGet() throws Exception {
		assertNull(map.get(e));
		cm.setEntityComponent(e, C1);
		assertEquals(C1, map.get(e));
		cm.removeAllEntityComponents(e);
		assertNull(map.get(e));
	}

	@Test
	public void testGetSafe() throws Exception {
		assertNull(map.getSafe(e));
		cm.setEntityComponent(e, C1);
		assertEquals(C1, map.getSafe(e));
		cm.removeAllEntityComponents(e);
		assertEquals(C1, map.getSafe(e));
		cm.setEntityComponent(e, C2);
		cm.removeAllEntityComponents(e);
		assertNull(map.getSafe(e));
	}
}
