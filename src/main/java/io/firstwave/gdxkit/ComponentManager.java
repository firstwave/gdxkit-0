package io.firstwave.gdxkit;

import com.badlogic.gdx.utils.IntMap;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages Component relationships with Entities.
 * First version created on 3/30/14.
 */
public class ComponentManager {
	/**
	 * The outer map uses Component indices and the inner map uses Entity indices.
	 * This results in each inner map containing the same type of component for each entity
	 */
	private final IntMap<IntMap<Component>> componentTable;

	/**
	 * Contains a map of entities to the last component that was removed.
	 */
	private final IntMap<Component> removed;

	/**
	 * This map uses Entity ids as keys. The inner BotSets indicate which components an entity has
	 */
	private final IntMap<BitSet> componentBits;

	private final Map<Class<? extends Component>, ComponentMap> componentMaps;
	private Listener listener = VOID_LISTENER;
	public ComponentManager() {
		componentTable = new IntMap<IntMap<Component>>();
		componentBits = new IntMap<BitSet>();
		componentMaps = new HashMap<Class<? extends Component>, ComponentMap>();
		removed = new IntMap<Component>();
	}

	private IntMap<Component> emptyMap() {
		return new IntMap<Component>();
	}

	/**
	 * Associates the given component with the given entity. An entity may only have one of each type of component,
	 * so if a component of the same type is already associated with the entity, the old component will be overwritten.
	 * A listener event will be sent even if the Entity already had an existing component of the same type.
	 * null values are not permitted.
	 * @param e the Entity to associate the Component with
	 * @param c the Component to associate with the Entity
	 */
	public void setEntityComponent(Entity e, Component c) {
		int i = Component.typeIndex.forType(c.getClass());
		IntMap<Component> map = componentTable.get(i, null);
		BitSet bits = componentBits.get(e.id, null);
		if (map == null) {
			map = emptyMap();
			componentTable.put(i, map);
		}
		if (bits == null) {
			bits = new BitSet();
			componentBits.put(e.id, bits);
		}
		map.put(e.id, c);
		bits.set(i);
		listener.onComponentAdded(e, c.getClass());
	}

	/**
	 * Return the Component of the given type that is associated with the given Entity.
	 * Returns null if no component matches the given type.
	 * @param e
	 * @param type
	 * @return the Component of the given type that is associated with the given Entity.
	 */
	public Component getEntityComponent(Entity e, Class<? extends Component> type) {
		IntMap<Component> map = componentTable.get(Component.typeIndex.forType(type), null);
		if (map == null) return null;
		return map.get(e.id, null);
	}

	/**
	 * Disassociates the Component of the given type from the given Entity.
	 * A listener event will be sent in the event that a Component will be removed.
	 * @param e
	 * @param type
	 * @return true if a Component was removed.
	 */
	public boolean removeEntityComponent(Entity e, Class<? extends Component> type) {
		int i = Component.typeIndex.forType(type);
		IntMap<Component> map = componentTable.get(i, null);
		BitSet bits = componentBits.get(e.id, null);
		if (map == null) return false;
		if (bits.get(i)) {
			// we need to notify the listener *before* actually removing the component from the table
			listener.onComponentRemoved(e, type);
			removed.put(e.id, map.get(e.id));
			map.put(e.id, null);
			bits.clear(i);
			return true;
		}
		return false;
	}

	/**
	 * Returns the Component most recently removed from the given entity. This is useful if you need to do cleanup after
	 * receiving a entityRemoved signal. This is generally used to make recently removed components available to a ComponentMap
	 * @param e
	 * @return
	 */
	Component getLastRemovedEntityComponent(Entity e) {
		return removed.get(e.id);
	}

	/**
	 * Calls {@link #removeEntityComponent(Entity, Class)} for all components associated with the given entity.
	 * @param e
	 * @return true if any components were removed
	 */
	public boolean removeAllEntityComponents(Entity e) {
		Component[] cs = getAllEntityComponents(e);
		for (Component c : cs) {
			removeEntityComponent(e, c.getClass());
		}
		return (cs.length > 0);
	}

	/**
	 * Return an array containing all of an entity's components.
	 * @return
	 */
	private static final Component[] EMPTY_COMPONENT_ARRAY = new Component[0];
	public Component[] getAllEntityComponents(Entity e) {
		BitSet bs = componentBits.get(e.id);
		if (bs == null) return EMPTY_COMPONENT_ARRAY; // an entity that has never been assigned any components will not have a bit put
		Component[] rv = new Component[bs.cardinality()];
		int j = 0;
		for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
			rv[j++] = componentTable.get(i).get(e.id);
		}
		return rv;
	}

	/**
	 * Check if the given Entity has a Component of the given type
	 * @param e
	 * @param type
	 * @return true if the given Entity has a Component of the given type
	 */
	public boolean entityHasComponent(Entity e, Class<? extends Component> type) {
		BitSet bits = componentBits.get(e.id, null);
		if (bits == null) return false;
		return bits.get(Component.typeIndex.forType(type));
	}

	BitSet getEntityComponentBits(Entity e) {
		return componentBits.get(e.id);
	}

	/**
	 * Return a {@link io.firstwave.gdxkit.ComponentMap} for the specified type
	 * @param type
	 * @param <T>
	 * @return a {@link io.firstwave.gdxkit.ComponentMap} for the specified type
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> ComponentMap<T> getComponentMap(Class<T> type) {
		ComponentMap rv = componentMaps.get(type);
		if (rv == null) {
			rv = new ComponentMap<T>(this, type);
			componentMaps.put(type, rv);
		}
		return rv;
	}

	/**
	 * Set the object that will receive notifications of updates to the component table
	 * @param listener
	 */
	protected void setListener(Listener listener) {
		if (listener == null) {
			this.listener = VOID_LISTENER;
		} else {
			this.listener = listener;
		}
	}

	/**
	 * Easier than checking for null.
	 */
	private static final Listener VOID_LISTENER = new Listener() {
		@Override
		public void onComponentAdded(Entity e, Class<? extends Component> type) {}

		@Override
		public void onComponentRemoved(Entity e, Class<? extends Component> type) {}
	};

	/**
	 * Allows listening to changes to the internal table.
	 * Note that for each method, the indicated component type is already/still present in the internal table.
	 */
	public static interface Listener {
		/**
		 * This method is called AFTER the component has been added to the internal table.
		 * As such, the component has already been added to the lookup table.
		 * @param e
		 * @param type
		 */
		public void onComponentAdded(Entity e, Class<? extends Component> type);

		/**
		 * This method is called BEFORE the component is removed from the internal table.
		 * As such, the component has not yet been removed from the internal table.
		 * This allows a listener to perform cleanup that may be dependent on component data
		 * @param e
		 * @param type
		 */
		public void onComponentRemoved(Entity e, Class<? extends Component> type);
	}
}
