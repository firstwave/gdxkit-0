package io.firstwave.gdxkit;

import io.firstwave.gdxkit.util.Signal;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * First version created on 3/29/14.
 */
public class View implements Iterable<Entity> {
	public final Signal<Entity> entityAdded;
	public final Signal<Entity> entityRemoved;

	private final BitSet bits;
	private final EntityManager manager;
	private final Aspect aspect;

	View(EntityManager manager, Aspect aspect) {
		if (manager == null) throw new NullPointerException();
		entityAdded = new Signal<Entity>();
		entityRemoved = new Signal<Entity>();

		bits = new BitSet();
		this.manager = manager;
		this.aspect = aspect;
	}

	private View(EntityManager manager, BitSet bits) {
		if (manager == null) throw new NullPointerException();
		entityAdded = new Signal.VoidSignal<Entity>();
		entityRemoved = entityAdded;
		this.bits = bits;
		this.manager = manager;
		this.aspect = null;
	}

	/**
	 * Return a View that contains all entities that appear in either given View.
	 * The returned View does not have an associated Aspect, and will therefore not broadcast entityAdded or entityRemoved signals.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static View union(View v1, View v2) {
		BitSet bits = (BitSet) v1.bits.clone();
		bits.or(v2.bits);
		return new View(v1.manager, bits);
	}

	/**
	 * Return a View that contains all entities that appear in both given Views.
	 * The returned View does not have an associated Aspect, and will therefore not broadcast entityAdded or entityRemoved signals.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static View intersection(View v1, View v2) {
		BitSet bits = (BitSet) v1.bits.clone();
		bits.and(v2.bits);
		return new View(v1.manager, bits);
	}

	/**
	 * Return a View that contains all Entities in the given View that evaluate to true with the given {@link io.firstwave.gdxkit.View.Predicate}
	 * @param v the View to be evaluated
	 * @param p the Predicate to be used to evaluate Entities
	 * @return a View that contains all Entities in the given View that evaluate to true with the given {@link io.firstwave.gdxkit.View.Predicate}
	 */
	public static View filter(View v, Predicate p) {
		BitSet bits = new BitSet();
		for (int i = v.bits.nextSetBit(0); i >= 0; i = v.bits.nextSetBit(i+1)) {
			if (p.apply(v.manager.getEntity(i))) {
				bits.set(i);
			}
		}
		return new View(v.manager, bits);
	}

	/**
	 * Evaluate an entity's componentBits and determine if it's nodeId should be inserted or removed from this view.
	 * For internal use only
	 */
	void check(int id, BitSet componentBits) {
		if(aspect.check(componentBits)) {
			if (bits.get(id)) return;
			bits.set(id);
			entityAdded.broadcast(manager.getEntity(id));
		} else {
			// see if we should remove
			if (!bits.get(id)) return;
			bits.clear(id);
			entityRemoved.broadcast(manager.getEntity(id));
		}
	}

	/**
	 * Evaluate an entity's componentBits and determine if it's nodeId should be inserted or removed from this view.
	 * For internal use only
	 */
	void check(Entity e) {
		check(e.id, manager.componentManager.getEntityComponentBits(e));
	}

	/**
	 * Return the number of Entities in this View
	 * @return the number of Entities in this View
	 */
	public int count() {
		return bits.cardinality();
	}

	/**
	 * Check if the given entity is present in this view
	 * @param e
	 * @return
	 */
	public boolean contains(Entity e) {
		return bits.get(e.id);
	}

	@Override
	public Iterator<Entity> iterator() {
		return new ViewIterator(this);
	}

	/**
	 * Determines a true or false value for a given input.
	 */
	public static interface Predicate {
		/**
		 * Return the result of applying this predicate to the input Entity.
		 * This method is generally expected to not cause any observable side effects.
		 * @param e the Entity to be evaluated
		 * @return the result of applying this predicate to the input Entity.
		 */
		public boolean apply(Entity e);
	}

	public static class ViewIterator implements Iterator<Entity> {
		private final BitSet bits;
		private final EntityManager manager;
		private int index;
		public ViewIterator(View view) {
			bits = (BitSet) view.bits.clone();
			manager = view.manager;
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return (bits.nextSetBit(index) >= 0);
		}

		@Override
		public Entity next() {
			int i = bits.nextSetBit(index);
			if (i < 0) throw new NoSuchElementException();
			index = i + 1;
			return manager.getEntity(i);
		}

		/**
		 * Unsupported
		 * @throws java.lang.UnsupportedOperationException
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
