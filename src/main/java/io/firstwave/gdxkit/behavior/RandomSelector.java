package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.Entity;

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
	public State evaluate(Entity e, Agent a) {
		int i = random.nextInt(count());
		return get(i).evaluate(e, a);
	}
}
