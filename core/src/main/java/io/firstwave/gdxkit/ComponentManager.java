package io.firstwave.gdxkit;

import com.badlogic.gdx.utils.IntMap;
import io.firstwave.gdxkit.util.ImmutableBitSet;
import io.firstwave.gdxkit.util.Log;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages Component relationships with Entities.
 * First version created on 3/30/14.
 */
public class ComponentManager {

	private static final String TAG = ComponentManager.class.getSimpleName();
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
	 * This map uses Entity ids as keys. The inner BitSets indicate which components an entity has
	 */
	private final IntMap<BitSet> componentBits;

	private final Map<Class<? extends Component>, ComponentMap> componentMaps;
	private EntityObserverAdapter observers;
	public ComponentManager() {
		componentTable = new IntMap<IntMap<Component>>();
		componentBits = new IntMap<BitSet>();
		componentMaps = new HashMap<Class<? extends Component>, ComponentMap>();
		removed = new IntMap<Component>();
		observers = new EntityObserverAdapter();
	}

	private IntMap<Component> emptyMap() {
		return new IntMap<Component>();
	}

	/**
	 * Associates the given component with the given entity. An entity may only have one of each type of component,
	 * so if a component of the same type is already associated with the entity, the old component will be overwritten.
	 * A observers event will be sent even if the Entity already had an existing component of the same type.
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

		if (bits.get(i)) {
			//...a Component of this type is already present
			removeEntityComponent(e, c.getClass());
		}

		// now that we've removed the existing component, add the new one
		map.put(e.id, c);
		bits.set(i);

		Log.v(TAG, "onComponentAdded:" + e + " [" + c + "]");
		observers.onComponentAdded(e, c.getClass());
	}

	/**
	 * Return the Component of the given type that is associated with the given Entity.
	 * Returns null if no component matches the given type.
	 * @param e
	 * @param type
	 * @return the Component of the given type that is associated with the given Entity.
	 */
	public <T extends Component> T getEntityComponent(Entity e, Class<T> type) {
		IntMap<Component> map = componentTable.get(Component.typeIndex.forType(type), null);
		if (map == null) return null;
		return type.cast(map.get(e.id, null));
	}

	/**
	 * Disassociates the Component of the given type from the given Entity.
	 * A observer event will be sent in the event that a Component will be removed.
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
			// we need to notify the observer *before* actually removing the component from the table
			Log.v(TAG, "onBeforeComponentRemoved:" + e + " [" + type + "]");
			observers.onBeforeComponentRemoved(e, type);
			removed.put(e.id, map.get(e.id));
			map.put(e.id, null);
			bits.clear(i);
			Log.v(TAG, "onComponentRemoved:" + e + " [" + type + "]");
			observers.onComponentRemoved(e, type);
			return true;
		}
		return false;
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
	 * Return an array containing all of an entity's components. This can be a potentially costly operation, relatively speaking.
	 * You should usually prefer to get a specific component type instead.
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

	private static final ImmutableBitSet EMPTY_BIT_SET = new ImmutableBitSet();
	public ImmutableBitSet getEntityComponentBits(Entity e) {
		BitSet rv = componentBits.get(e.id);
		if (rv == null) {
			return EMPTY_BIT_SET;
		}
		return new ImmutableBitSet(rv);
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
	 * Add an object that will receive notifications of updates to the component table.
	 * Observers should only receive Component added/removed events.
	 * @param observer
	 */
	protected void addObserver(EntityObserver observer) {
		observers.addObserver(observer);
	}

	/**
	 * Unregister the given observer
	 * Observers should only receive Component added/removed events.
	 * @param observer
	 * @return true is the observer was found and unregistered
	 */
	protected boolean removeObserver(EntityObserver observer) {
		return observers.removeObserver(observer);
	}

}
