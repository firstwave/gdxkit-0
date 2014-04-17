package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;
import io.firstwave.gdxkit.EntityFactory;
import io.firstwave.gdxkit.mock.TestBehaviors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * First version created on 4/14/14.
 */
public class SelectorTest implements TestBehaviors {

	Entity e;
	Agent a;

	@Before
	public void setUp() {
		e = EntityFactory.create(null, 0);
		a = new Agent(null);
	}

	@Test
	public void testEmpty() {
		Sequence s = new Sequence();
		assertEquals(Node.State.SUCCESS, s.evaluate(e, a));
	}

	@Test
	public void testAllFailure() {
		Selector s = new Selector();
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());

		assertEquals(Node.State.FAILURE, s.evaluate(e, a));
	}

	@Test
	public void testPartialSuccess() {
		Selector s = new Selector();
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new SuccessNode());

		assertEquals(Node.State.SUCCESS, s.evaluate(e, a));
	}

	@Test
	public void testRunning() {
		Selector s = new Selector();
		final StringBuilder sb = new StringBuilder();
		s.add(new FailureNode("1", sb));
		s.add(new Node() {
			@Override
			public State evaluate(Entity e, Agent a) {
				sb.append("2");
				int i = a.blackboard.getInt(nodeId, 0);
				if (i < 2) {
					a.blackboard.putInt(nodeId, ++i);
					return State.RUNNING;
				}
				return State.FAILURE;
			}
		});
		s.add(new SuccessNode("3", sb));
		Node.State state = null;
		do {
			state = s.evaluate(e, a);
			System.out.println(state.toString());
		} while (state != Node.State.SUCCESS);
		// the last 2 represents the SUCCESSful evaluation, so there should be one more than the number of RUNNING evals
		assertEquals("12223", sb.toString());
	}
}
