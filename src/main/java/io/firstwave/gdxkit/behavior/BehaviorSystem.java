package io.firstwave.gdxkit.behavior;

import io.firstwave.gdxkit.*;
import io.firstwave.gdxkit.systems.AspectSystem;

/**
 * First version created on 4/13/14.
 */
public class BehaviorSystem extends AspectSystem {

	public final BehaviorLibrary library;
	private ComponentMap<Agent> agents;
	private Context context;

	public BehaviorSystem() {
		super(Aspect.getAspectForOne(Agent.class));
		library = new BehaviorLibrary();
	}

	@Override
	protected void onRegistered(Engine engine) {
		super.onRegistered(engine);
		agents = engine.entityManager.componentManager.getComponentMap(Agent.class);
		context = new Context(engine, library);
	}

	@Override
	protected void onUnregistered() {
		super.onUnregistered();
		context = null;
	}

	@Override
	public void processEntity(Entity e, float delta) {
		Agent a = agents.get(e);
		Node root = library.get(a.rootName, VOID_NODE);
		if (root.getContext() != context) {
			Context.updateTree(context, root);
		}
		library.get(a.rootName).evaluate(e, a);
	}

	private static final Node VOID_NODE = new Node() {
		@Override
		public State evaluate(Entity e, Agent a) {
			return State.SUCCESS;
		}
	};

}
