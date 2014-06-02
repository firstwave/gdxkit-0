package io.firstwave.gdxkit.systems;

import io.firstwave.gdxkit.BaseSystem;

/**
 * Created by obartley on 6/2/14.
 */
public abstract class FixedRateSystem extends BaseSystem {
	private float rate;
	private double accumulator;


	public FixedRateSystem(float rate) {
		setRate(rate);
		accumulator = 0;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		if (rate <= 0) {
			throw new IllegalArgumentException("rate must be a positive value");
		}
		accumulator = 0;
		this.rate = rate;
	}

	@Override
	protected boolean checkUpdate(float delta) {
		accumulator += delta;
		return (accumulator > rate);
	}

	@Override
	protected void onUpdate(float delta) {
		while (accumulator > rate) {
			onFixedUpdate();
			accumulator -= rate;
		}
	}

	protected abstract void onFixedUpdate();
}
