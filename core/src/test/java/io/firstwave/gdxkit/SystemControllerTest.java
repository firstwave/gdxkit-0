package io.firstwave.gdxkit;

import org.junit.Test;

import static org.junit.Assert.*;

public class SystemControllerTest {

	@Test
	public void testUpdate() {
		SystemController ctrl = new SystemController();
		TestSystem sys = new TestSystem();

		ctrl.registerSystem(sys);

		ctrl.initialize();

		ctrl.updateSystems(1.0f);
		ctrl.updateSystems(1.0f);

		sys.check = false;
		ctrl.updateSystems(1.0f);

		ctrl.unregisterSystem(TestSystem.class);

		assertEquals(2, sys.updates);
		assertEquals("RICCCU", sys.record.toString());
	}

	@Test
	public void testNoAutoUpdate() {
		SystemController ctrl = new SystemController();
		TestSystem sys = new TestSystem();

		ctrl.registerSystem(sys, false);

		ctrl.initialize();

		ctrl.updateSystems(1.0f);
		ctrl.updateSystems(1.0f);

		sys.check = false;
		ctrl.updateSystems(1.0f);

		ctrl.unregisterSystem(TestSystem.class);

		assertEquals(0, sys.updates);
		assertEquals("RIU", sys.record.toString());
	}

	public static class TestSystem extends BaseSystem {
		public int updates = 0;
		public StringBuilder record = new StringBuilder();
		public boolean check = true;


		@Override
		protected void onRegistered() {
			assertNotNull(getController());
			record.append("R");
		}

		@Override
		protected void onInitialized() {
			record.append("I");
		}

		@Override
		protected boolean checkUpdate(float delta) {
			record.append("C");
			return check;
		}

		@Override
		protected void onUnregistered() {
			assertNull(getController());
			record.append("U");
		}

		@Override
		protected void onUpdate(float delta) {
			updates++;
		}
	}

}