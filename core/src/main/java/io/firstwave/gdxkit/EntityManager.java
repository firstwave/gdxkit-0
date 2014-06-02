package io.firstwave.gdxkit;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import io.firstwave.gdxkit.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * First version created on 3/29/14.
 */
public class EntityManager {

	private static final String TAG = EntityManager.class.getSimpleName();

	private final IntMap<Entity> entities;
	private final IntSet disabled;
	private final EntityPool pool;
	private final Map<Aspect, WeakReference<View>> viewMap;
	private final EntityObserverAdapter observers;

	public final ComponentManager componentManager;

	public EntityManager() {
		this(new ComponentManager());
	}

	public EntityManager(ComponentManager componentManager) {
		entities = new IntMap<Entity>();
		disabled = new IntSet();
		pool = new EntityPool();
		viewMap = new HashMap<Aspect, WeakReference<View>>();

		this.componentManager = componentManager;
		observers = new EntityObserverAdapter();
		componentManager.addObserver(observers);
	}

	public Entity getEntity(int id) {
		return entities.get(id, null);
	}

    public Iterator<Entity> getEntities() {
        return entities.values();
    }

	public Entity createEntity() {
		Entity rv = pool.get();
		entities.put(rv.id, rv);
		Log.v(TAG, "onEntityAdded:" + rv);
		observers.onEntityAdded(rv);
		return rv;
	}

	public void destroyEntity(Entity e) {
		componentManager.removeAllEntityComponents(e);
		// Notify all observers after removing components
		Log.v(TAG, "onEntityRemoved:" + e);
		observers.onEntityRemoved(e);
		disabled.remove(e.id);
		entities.remove(e.id);
		pool.release(e);
	}

	public void setEntityEnabled(Entity e, boolean enabled) {
		Log.v(TAG, "setEntityEnabled:" + e + " [" + enabled + "]");
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

	public boolean isEntityDestroyed(Entity e) {
		return (!entities.containsKey(e.id));
	}

	public View getView(Aspect aspect) {
		WeakReference<View> ref = viewMap.get(aspect);
		View rv;
		if (ref == null || ref.get() == null) {
			rv = new View(this, aspect);
			viewMap.put(aspect, new WeakReference<View>(rv));
		} else {
			rv = ref.get();
		}
		return rv;
	}



	/**
	 * Add an EntityObserver that gets notification of Entity lifecycle AND Component events.
	 * There is no need to register the given Observer with the ComponentManager as well.
	 * @param o
	 */
	public void addObserver(EntityObserver o) {
		observers.addObserver(o);
	}

	/**
	 * Remove a previously registered EntityObserver
	 * @param o
	 * @return
	 */
	public boolean removeObserver(EntityObserver o) {
		return observers.removeObserver(o);
	}

	private class EntityPool {
		private final Array<Entity> pool;
		private int nextId;
		private EntityPool() {
			pool = new Array<Entity>(false, 64);
		}

		public Entity get() {
			Entity rv;
			if (pool.size == 0) {
				rv = new Entity(EntityManager.this, nextId++);
			} else {
				rv = pool.pop();
			}
			Log.v("EntityPool", "get: " + rv + " size = " + pool.size);
			return rv;
		}

		public void release(Entity e) {
			// TODO: ensure Entities are correctly reset
			Log.v("EntityPool", "release: " + e + " size = " + pool.size);
			pool.add(e);
		}
	}
}
