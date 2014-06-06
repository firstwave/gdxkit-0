package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.*;
import io.firstwave.gdxkit.systems.AspectSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * First version created on 4/26/14.
 */
public class BehaviorSystem extends AspectSystem {

//	private final Map<String, Node> roots;
//	private final ComponentMap<Agent> agents;
//	private final ComponentMap<SquadLeader> squadLeaders;
//	private View squads;

	public BehaviorSystem() {
		super(Aspect.getAspectForOne(Agent.class, SquadLeader.class));
//		agents = entityManager.componentManager.getComponentMap(Agent.class);
//		squadLeaders = entityManager.componentManager.getComponentMap(SquadLeader.class);
//		roots = new HashMap<String, Node>();
	}

	@Override
	public void processEntity(Entity e, float delta) {

	}
//
//	public void putRootNode(String name, Node root) {
//		roots.put(name, root);
//	}
//
//	@Override
//	protected void onInitialized() {
//		super.onInitialized();
//		squads = entityManager.getView(Aspect.getAspectForOne(Squad.class));
//	}
//
//	@Override
//	protected void onUnregistered() {
//		super.onUnregistered();
//		squads = null;
//	}
//
//	@Override
//	public void onUpdate(float delta) {
//
//
//		super.onUpdate(delta);
//	}
//
//	@Override
//	public void processEntity(Entity e, float delta) {
//		SquadLeader sl = squadLeaders.get(e);
//		Agent a = agents.get(e);
//
//		if (sl != null) {
//			// ???
//		}
//
//		if (a != null) {
//			Node root = roots.get(a.rootName);
//			if (root != null) {
//				root.evaluate(e, a.blackboard);
//			}
//		}
//	}

}
