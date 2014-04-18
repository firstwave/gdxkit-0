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
public class SequenceTest implements TestBehaviors {

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
		assertEquals(Node.Status.SUCCESS, s.evaluate(e, a));
	}

	@Test
	public void testAllFailure() {
		Sequence s = new Sequence();
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());
		s.add(new FailureNode());

		assertEquals(Node.Status.FAILURE, s.evaluate(e, a));
	}

	@Test
	public void testPartialFailure() {
		Sequence s = new Sequence();
		s.add(new SuccessNode());
		s.add(new SuccessNode());
		s.add(new SuccessNode());
		s.add(new FailureNode());

		assertEquals(Node.Status.FAILURE, s.evaluate(e, a));
	}

	@Test
	public void testRunning() {
		Sequence s = new Sequence();
		final StringBuilder sb = new StringBuilder();
		s.add(new SuccessNode("1", sb));
		s.add(new Node() {
			@Override
			public Status evaluate(Entity e, Agent a) {
				sb.append("2");
				int i = a.blackboard.getInt(nodeId, 0);
				if (i < 2) {
					a.blackboard.putInt(nodeId, ++i);
					return Status.RUNNING;
				}
				return Status.SUCCESS;
			}
		});
		s.add(new FailureNode("3", sb));
		Node.Status status = Node.Status.SUCCESS;
		do {
			status = s.evaluate(e, a);
			System.out.println(status.toString());
		} while (status != Node.Status.FAILURE);
		// the last 2 represents the SUCCESSful evaluation, so there should be one more than the number of RUNNING evals
		assertEquals("12223", sb.toString());
	}
}
