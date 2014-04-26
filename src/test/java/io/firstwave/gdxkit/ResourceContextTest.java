package io.firstwave.gdxkit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * First version created on 4/25/14.
 */
public class ResourceContextTest {

	ResourceContext rc;

	@Before
	public void setUp() {
		rc = new ResourceContext();
	}

	@Test
	public void testPutGetResource() throws Exception {
		Object o = new Object();
		String foo = "foo";
		TestInterface ti = new TestInterfaceImpl();
		Number n = new Number() {
			@Override
			public int intValue() {
				return 0;
			}

			@Override
			public long longValue() {
				return 0;
			}

			@Override
			public float floatValue() {
				return 0;
			}

			@Override
			public double doubleValue() {
				return 0;
			}
		};

		rc.putResource(o);
		rc.putResource(foo);
		rc.putResource(ti);
		rc.putResource(n);

		assertEquals(o, rc.getResource(Object.class));
		assertEquals(foo, rc.getResource(String.class));
		assertEquals(ti, rc.getResource(TestInterfaceImpl.class));

		// given class MUST match the exact type, not a superclass or interface
		assertEquals(null, rc.getResource(TestInterface.class));
		// anonymous classes will similarly not work
		assertEquals(null, rc.getResource(Number.class));
	}

	@Test(expected = NullPointerException.class)
	public void testPutNullResource() {
		rc.putResource(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateTypes() {
		rc.putResource("foo");
		rc.putResource("bar");
	}

	private static interface TestInterface {

	}

	private static class TestInterfaceImpl implements TestInterface {

	}
}
