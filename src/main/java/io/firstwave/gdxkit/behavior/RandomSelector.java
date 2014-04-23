package io.firstwave.gdxkit.behavior;

import java.util.Random;

/**
 * First version created on 4/13/14.
 */
public class RandomSelector extends CompositeNode {
	Random random;

	public RandomSelector() {
		random = new Random();
	}

	@Override
	public Status evaluate(Object o, IBlackboard blackboard) {
		int i = random.nextInt(count());
		return get(i).evaluate(o, blackboard);
	}
}
