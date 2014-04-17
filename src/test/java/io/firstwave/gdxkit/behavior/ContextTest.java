package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;
import io.firstwave.gdxkit.mock.TestBehaviors;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * First version created on 4/15/14.
 */
public class ContextTest implements TestBehaviors {

	@Test
	public void testUpdateContext() {
		Sequence seq = new Sequence();
		seq.add(new FailureNode());

		Selector sel = new Selector();
		sel.add(new SuccessNode());

		Node n = new Node() {
			@Override
			public State evaluate(Entity e, Agent a) {
				return null;
			}
		};
		sel.add(n);
		seq.add(sel);

		Context c = new Context(null, null);

		Context.updateTree(c, seq);
		assertTrue(c == n.getContext());

	}
}
