package io.firstwave.gdxkit.util;

import java.util.BitSet;

/**
 * Implementation of a BitSet that will throw an UnsupportedOperationException when modifications are attempted.
 * First version created on 5/25/14.
 */
public class ImmutableBitSet extends BitSet {
	private static final String ERR_MSG = "ImmutableBitSet cannot be modified";

	/**
	 * Construct an ImmutableBitSet with no set bits
	 */
	public ImmutableBitSet() {
		// empty constructor
	}

	/**
	 * Copy constructor to make an immutable copy of a BitSet
	 * @param toCopy
	 */
	public ImmutableBitSet(BitSet toCopy) {
		super.or(toCopy);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void flip(int bitIndex) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void flip(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void set(int bitIndex) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void set(int bitIndex, boolean value) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void set(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void set(int fromIndex, int toIndex, boolean value) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void clear(int bitIndex) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void clear(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void and(BitSet set) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void or(BitSet set) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void xor(BitSet set) {
		throw new UnsupportedOperationException(ERR_MSG);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException
	 */
	@Override
	public void andNot(BitSet set) {
		throw new UnsupportedOperationException(ERR_MSG);
	}
}
