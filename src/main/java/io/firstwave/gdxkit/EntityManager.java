package io.firstwave.gdxkit;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * First version created on 3/29/14.
 */
public class EntityManager implements ComponentManager.Observer {

	private final IntMap<Entity> entities;
	private final IntSet disabled;
	private final EntityPool pool;
	private final Map<Aspect, WeakReference<View>> views;

	public final ComponentManager componentManager;

	public EntityManager() {
		this(new ComponentManager());
	}

	public EntityManager(ComponentManager componentManager) {
		entities = new IntMap<Entity>();
		disabled = new IntSet();
		pool = new EntityPool();
		views = new HashMap<Aspect, WeakReference<View>>();
		this.componentManager = componentManager;
		componentManager.addObserver(this);
	}

	public Entity getEntity(int id) {
		return entities.get(id, null);
	}

	public Entity createEntity() {
		Entity rv = pool.get();
		entities.put(rv.id, rv);
		return rv;
	}

	public void destroyEntity(Entity e) {
		componentManager.removeAllEntityComponents(e);
		disabled.remove(e.id);
		entities.remove(e.id);
		pool.release(e);
	}

	public void setEntityEnabled(Entity e, boolean enabled) {
		if (enabled) {
			disabled.remove(e.id);
		} else {
			disabled.add(e.id);
		}
	}

	public int getEntityCount() {
		return entities.size;
	}

	public int getDisabledEntityCount() {
		return disabled.size;
	}

	public boolean isEntityEnabled(Entity e) {
		return (!disabled.contains(e.id));
	}

	public View getView(Aspect aspect) {
		WeakReference<View> ref = views.get(aspect);
		View rv;
		if (ref == null || ref.get() == null) {
			rv = new View(this, aspect);
			views.put(aspect, new WeakReference<View>(rv));
		} else {
			rv = ref.get();
		}
		return rv;
	}

	@Override
	public void onComponentAdded(Entity e, Class<? extends Component> type) {
		Map.Entry<Aspect, WeakReference<View>> entry;
		for (Iterator<Map.Entry<Aspect, WeakReference<View>>> iter = views.entrySet().iterator(); iter.hasNext();) {
			entry = iter.next();
			if (entry.getValue() != null && entry.getValue().get() != null) {
				entry.getValue().get().check(e);
			} else {
				iter.remove();
			}
		}
	}

	@Override
	public void onBeforeComponentRemoved(Entity e, Class<? extends Component> type) {
		Map.Entry<Aspect, WeakReference<View>> entry;
		for (Iterator<Map.Entry<Aspect, WeakReference<View>>> iter = views.entrySet().iterator(); iter.hasNext();) {
			entry = iter.next();
			if (entry.getValue() != null && entry.getValue().get() != null) {
				// since we are receiving this event before the component is physically removed,
				// we need the view to check a bitset that has had the removed component manually removed
				BitSet bs = (BitSet) componentManager.getEntityComponentBits(e).clone();
				bs.clear(Component.typeIndex.forType(type));
				entry.getValue().get().check(e.id, bs);
			} else {
				iter.remove();
			}
		}
	}

	private class EntityPool {
		private final Array<Entity> pool;
		private int nextId;
		private EntityPool() {
			pool = new Array<Entity>(false, 64);
		}

		public Entity get() {
			if (pool.size == 0) return new Entity(EntityManager.this, nextId++);
			return pool.pop();
		}

		public void release(Entity e) {
			pool.add(e);
		}
	}
}
