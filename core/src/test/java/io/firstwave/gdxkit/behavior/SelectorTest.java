package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;
import io.firstwave.gdxkit.EntityFactory;
import io.firstwave.gdxkit.mock.TestBehaviors;
import io.firstwave.gdxkit.util.Log;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * First version created on 4/14/14.
 */
public class SelectorTest implements TestBehaviors {

	Entity e;
	IBlackboard b;

	@Before
	public void setUp() {
		e = EntityFactory.create(null, 0);
		b = new Blackboard();
	}

	@Test
	public void testEmpty() {
		Sequence s = new Sequence();
		assertEquals(Node.Status.SUCCESS, s.evaluate(e, b));
	}

	@Test
	public void testAllFailure() {
		Selector s = new Selector();
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());

		assertEquals(Node.Status.FAILURE, s.evaluate(e, b));
	}

	@Test
	public void testPartialSuccess() {
		Selector s = new Selector();
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new SuccessNode());

		assertEquals(Node.Status.SUCCESS, s.evaluate(e, b));
	}

	@Test
	public void testRunning() {
		Selector s = new Selector();
		final StringBuilder sb = new StringBuilder();
		s.add(new FailureNode("1", sb));
		s.add(new Node() {
			@Override
			public Status evaluate(Object o, IBlackboard bb) {
				sb.append("2");
				int i = bb.getInt(getNodePath(), 0);
				if (i < 2) {
					bb.putInt(getNodePath(), ++i);
					return Status.RUNNING;
				}
				return Status.FAILURE;
			}
		});
		s.add(new SuccessNode("3", sb));
		Node.Status status = null;
		do {
			status = s.evaluate(e, b);
			Log.v(status.toString());
		} while (status != Node.Status.SUCCESS);
		// the last 2 represents the SUCCESSful evaluation, so there should be one more than the number of RUNNING evals
		assertEquals("12223", sb.toString());
	}
}
