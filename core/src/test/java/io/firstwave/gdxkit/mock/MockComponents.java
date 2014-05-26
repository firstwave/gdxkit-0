package io.firstwave.gdxkit.mock;

import io.firstwave.gdxkit.Component;

/**
 * First version created on 3/30/14.
 */
public interface MockComponents {

	public static final MockComponent1 C1 = new MockComponent1();
	public static final MockComponent2 C2 = new MockComponent2();
	public static final MockComponent3 C3 = new MockComponent3();
	public static final MockComponent4 C4 = new MockComponent4();

	public static final Class<? extends Component> C1_TYPE = MockComponents.MockComponent1.class;
	public static final Class<? extends Component> C2_TYPE = MockComponents.MockComponent2.class;
	public static final Class<? extends Component> C3_TYPE = MockComponents.MockComponent3.class;
	public static final Class<? extends Component> C4_TYPE = MockComponents.MockComponent4.class;

	public static final int C1_INDEX = Component.typeIndex.forType(C1_TYPE);
	public static final int C2_INDEX = Component.typeIndex.forType(C2_TYPE);
	public static final int C3_INDEX = Component.typeIndex.forType(C3_TYPE);
	public static final int C4_INDEX = Component.typeIndex.forType(C4_TYPE);

	public static class Data extends Component {
		public int value;
	}

	public static class MockComponent1 extends Component {
	}
	public static class MockComponent2 extends Component {
	}
	public static class MockComponent3 extends Component {
	}
	public static class MockComponent4 extends Component {
	}
}
