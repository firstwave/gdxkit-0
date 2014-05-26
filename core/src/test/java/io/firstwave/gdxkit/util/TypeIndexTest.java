package io.firstwave.gdxkit.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * First version created on 3/30/14.
 */
public class TypeIndexTest {

	@Test
	public void testTypeIndex() {
		int n = TypeIndex.globalIndexForType(Integer.class);
		int i = TypeIndex.globalIndexForType(Float.class);
		int l = TypeIndex.globalIndexForType(Number.class);
		assertEquals(0, n);
		assertEquals(1, i);
		assertEquals(2, l);
		TypeIndex<Number> ti = new TypeIndex<Number>();
		assertEquals(0, ti.forType(Number.class));
		assertEquals(1, ti.forType(Integer.class));
		assertEquals(0, ti.forType(Number.class));
	}
}
