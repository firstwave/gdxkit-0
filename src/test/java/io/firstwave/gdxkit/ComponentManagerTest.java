package io.firstwave.gdxkit;

import io.firstwave.gdxkit.mock.MockComponents;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * First version created on 3/30/14.
 */
public class ComponentManagerTest implements MockComponents{


	Entity e;
	ComponentManager cm;

	@Before
	public void setUp() {
		e = new Entity(null, 0);
		cm = new ComponentManager();
	}

	@Test
	public void testSetRemoveComponent() {
		cm.setEntityComponent(e, C1);
		assertTrue(cm.getEntityComponentBits(e).get(C1_INDEX));
		cm.removeEntityComponent(e, C1_TYPE);
		assertFalse(cm.getEntityComponentBits(e).get(C1_INDEX));
	}

	@Test(expected = NullPointerException.class)
	public void testSetComponentNull() {
		cm.setEntityComponent(e, null);
	}

	@Test
	public void testHasComponent() {
		cm.setEntityComponent(e, C1);
		cm.setEntityComponent(e, C3);
		assertTrue(cm.entityHasComponent(e, C1_TYPE));
		assertFalse(cm.entityHasComponent(e, C2_TYPE));
		assertTrue(cm.entityHasComponent(e, C3_TYPE));
		assertFalse(cm.entityHasComponent(e, C4_TYPE));
	}

	@Test
	public void testRemoveAll() {
		cm.setEntityComponent(e, C1);
		cm.setEntityComponent(e, C3);
		assertTrue(cm.removeAllEntityComponents(e));
		assertFalse(cm.entityHasComponent(e, C1_TYPE));
		assertFalse(cm.entityHasComponent(e, C2_TYPE));
		assertFalse(cm.removeAllEntityComponents(e));
	}


	@Test
	public void testMapIdentity() {
		ComponentMap<MockComponent1> map1 = cm.getComponentMap(MockComponent1.class);
		ComponentMap<MockComponent1> map2 = cm.getComponentMap(MockComponent1.class);
		assertTrue(map1 == map2);

	}


	@Test
	public void testObserverEvents() {
		final StringBuilder sb = new StringBuilder();
		StringBuilder rec = new StringBuilder();
		DefaultEntityObserver l = new DefaultEntityObserver() {
			@Override
			public void onComponentAdded(Entity e, Class<? extends Component> type) {
				sb.append("a");
				sb.append(e.id);
				sb.append(Component.typeIndex.forType(type));
				assertTrue(cm.entityHasComponent(e, type));
			}

			@Override
			public void onComponentUpdated(Entity e, Class<? extends Component> type) {
				sb.append("u");
				sb.append(e.id);
				sb.append(Component.typeIndex.forType(type));
				assertTrue(cm.entityHasComponent(e, type));
			}

			@Override
			public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {
				sb.append("r");
				sb.append(e.id);
				sb.append(Component.typeIndex.forType(type));
				assertTrue(cm.entityHasComponent(e, type));
			}

			@Override
			public void onComponentRemoved(Entity e, Class<? extends Component> type) {
				sb.append("R");
				assertFalse(cm.entityHasComponent(e, type));
			}
		};
		cm.addObserver(l);

		Entity e2 = new Entity(null, 7);

		cm.setEntityComponent(e, C1);
		rec.append("a");
		rec.append(e.id);
		rec.append(C1_INDEX);

		
		cm.setEntityComponent(e, C1);
		rec.append("u");
		rec.append(e.id);
		rec.append(C1_INDEX);


		cm.setEntityComponent(e, C2);
		rec.append("a");
		rec.append(e.id);
		rec.append(C2_INDEX);


		cm.setEntityComponent(e2, C2);
		rec.append("a");
		rec.append(e2.id);
		rec.append(C2_INDEX);


		cm.removeEntityComponent(e2, C1_TYPE);
		// don't record anything because we shouldn't receive a notification

		cm.removeAllEntityComponents(e2);
		rec.append("r");
		rec.append(e2.id);
		rec.append(C2_INDEX);
		rec.append("R");

		cm.removeEntityComponent(e, C1_TYPE);
		rec.append("r");
		rec.append(e.id);
		rec.append(C1_INDEX);
		rec.append("R");

		assertEquals(rec.toString(), sb.toString());
	}

}
