package io.firstwave.gdxkit;

import java.util.BitSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * First version created on 3/29/14.
 */
public class View implements Iterable<Entity> {

	private final BitSet bits;
	private final EntityManager manager;
	private final Aspect aspect;
	private final EntityObserverAdapter observers;

	View(EntityManager manager, Aspect aspect) {
		this(manager, aspect, new BitSet());
		if (aspect == null) throw new NullPointerException();
	}

	private View(EntityManager manager, Aspect aspect, BitSet bits) {
		if (manager == null) throw new NullPointerException();
		observers = new EntityObserverAdapter();
		this.bits = bits;
		this.manager = manager;
		if (aspect != null) {
			manager.addObserver(new EntityManagerObserver());
		}
		this.aspect = aspect;
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
		return new View(v1.manager, null, bits);
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
		return new View(v1.manager, null, bits);
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
		return new View(v.manager, null, bits);
	}

	/**
	 * Evaluate an entity's componentBits and determine if it's relevant to the Aspect given at construction.
	 * Return true if the Entity is considered relevant to this View's aspect
	 * For internal use only
	 */
	boolean check(BitSet componentBits) {
		return aspect.check(componentBits);
	}

	/**
	 * Evaluate an entity's componentBits and determine if it's relevant to the Aspect given at construction.
	 * Return true if the Entity is considered relevant to this View's aspect
	 * For internal use only
	 */
	boolean check(Entity e) {
		return check(manager.componentManager.getEntityComponentBits(e));
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
	 * listen to all events coming from the EntityManager and determine their relevance to this View
 	 */
	private class EntityManagerObserver implements EntityObserver {
		@Override
		public void onEntityAdded(Entity e) {
			// since this event is fired before an Entity has any components, we can ignore it
		}

		@Override
		public void onEntityRemoved(Entity e) {
			// since this event is fired after an Entity has had all of its components removed, we can ignore it
		}

		@Override
		public void onComponentAdded(Entity e, Class<? extends Component> type) {
			if (check(e)) {
				if (!bits.get(e.id)) {
					bits.set(e.id);
					// we send an Entity added event if the Entity is new to this View
					observers.onEntityAdded(e);
				}
				observers.onComponentAdded(e, type);
			}
		}

		@Override
		public void onComponentUpdated(Entity e, Class<? extends Component> type) {
			if (check(e)) observers.onComponentUpdated(e, type);
		}

		@Override
		public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {
			if (check(e)) observers.onBeforeComponentRemoved(e, type);
		}

		@Override
		public void onComponentRemoved(Entity e, Class<? extends Component> type) {
			// this event is fired after the component has been removed
			if (bits.get(e.id)) {
				// if the Entity was in this View, we notify observers
				observers.onComponentRemoved(e, type);
				if (!check(e)) {
					// if the Entity is no longer relevant, we also notify that it has been removed
					bits.clear(e.id);
					observers.onEntityRemoved(e);
				}
			}
		}

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
