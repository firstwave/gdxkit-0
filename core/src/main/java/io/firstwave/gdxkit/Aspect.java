/*
Copyright 2014 Oliver Bartley.
Copyright 2011 GAMADU.COM.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY GAMADU.COM ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GAMADU.COM OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of GAMADU.COM.
 */
package io.firstwave.gdxkit;
import java.util.BitSet;

/**
 * An Aspects is used by systems as a matcher against entities, to check if a system is
 * interested in an entity. Aspects define what sort of component types an entity must
 * possess, or not possess.
 *
 * This creates an aspect where an entity must possess A and B and C:
 * Aspect.getAspectForAll(A.class, B.class, C.class)
 *
 * This creates an aspect where an entity must possess A and B and C, but must not possess U or V.
 * Aspect.getAspectForAll(A.class, B.class, C.class).exclude(U.class, V.class)
 *
 * This creates an aspect where an entity must possess A and B and C, but must not possess U or V, but must possess one of X or Y or Z.
 * Aspect.getAspectForAll(A.class, B.class, C.class).exclude(U.class, V.class).one(X.class, Y.class, Z.class)
 *
 * You can create and compose aspects in many ways:
 * Aspect.getEmpty().one(X.class, Y.class, Z.class).all(A.class, B.class, C.class).exclude(U.class, V.class)
 * is the same as:
 * Aspect.getAspectForAll(A.class, B.class, C.class).exclude(U.class, V.class).one(X.class, Y.class, Z.class)
 *
 * @author Arni Arent and Oliver Bartley
 *
 */
public class Aspect {

	private BitSet allSet;
	private BitSet exclusionSet;
	private BitSet oneSet;

	private Aspect() {
		this.allSet = new BitSet();
		this.exclusionSet = new BitSet();
		this.oneSet = new BitSet();
	}

	/**
	 * Returns an aspect where an entity must possess all of the specified component types.
	 * @param type a required component type
	 * @param types a required component type
	 * @return an aspect that can be matched against entities
	 */
	public Aspect all(Class<? extends Component> type, Class<? extends Component>... types) {
		allSet.set(Component.typeIndex.forType(type));

		for (Class<? extends Component> t : types) {
			allSet.set(Component.typeIndex.forType(t));
		}

		return this;
	}

	/**
	 * Excludes all of the specified component types from the aspect. A system will not be
	 * interested in an entity that possesses one of the specified exclusion component types.
	 *
	 * @param type component type to exclude
	 * @param types component type to exclude
	 * @return an aspect that can be matched against entities
	 */
	public Aspect exclude(Class<? extends Component> type, Class<? extends Component>... types) {
		exclusionSet.set(Component.typeIndex.forType(type));

		for (Class<? extends Component> t : types) {
			exclusionSet.set(Component.typeIndex.forType(t));
		}
		return this;
	}

	/**
	 * Returns an aspect where an entity must possess one of the specified component types.
	 * @param type one of the types the entity must possess
	 * @param types one of the types the entity must possess
	 * @return an aspect that can be matched against entities
	 */
	public Aspect one(Class<? extends Component> type, Class<? extends Component>... types) {
		oneSet.set(Component.typeIndex.forType(type));

		for (Class<? extends Component> t : types) {
			oneSet.set(Component.typeIndex.forType(t));
		}
		return this;
	}

	/**
	 * Creates an aspect where an entity must possess all of the specified component types.
	 *
	 * @param type the type the entity must possess
	 * @param types the type the entity must possess
	 * @return an aspect that can be matched against entities
	 *
	 * @deprecated
	 * @see #getAspectForAll(Class, Class[])
	 */
	public static Aspect getAspectFor(Class<? extends Component> type, Class<? extends Component>... types) {
		return getAspectForAll(type, types);
	}

	/**
	 * Creates an aspect where an entity must possess all of the specified component types.
	 *
	 * @param type a required component type
	 * @param types a required component type
	 * @return an aspect that can be matched against entities
	 */
	public static Aspect getAspectForAll(Class<? extends Component> type, Class<? extends Component>... types) {
		Aspect aspect = new Aspect();
		aspect.all(type, types);
		return aspect;
	}

	/**
	 * Creates an aspect where an entity must possess one of the specified component types.
	 *
	 * @param type one of the types the entity must possess
	 * @param types one of the types the entity must possess
	 * @return an aspect that can be matched against entities
	 */
	public static Aspect getAspectForOne(Class<? extends Component> type, Class<? extends Component>... types) {
		Aspect aspect = new Aspect();
		aspect.one(type, types);
		return aspect;
	}

	/**
	 * Check if a given Component type is of potential interest to this Aspect.
	 * A Component is considered potentially relevant if the type has been specified in either the 'all' or 'one' sets, but not the 'exclude' set.
	 *
	 * @param type
	 * @return
	 */
	public boolean check(Class<? extends Component> type) {
		int i = Component.typeIndex.forType(type);
		return (allSet.get(i) || oneSet.get(i) && !exclusionSet.get(i));
	}

	/**
	 * Will check if the entity is of interest to this Aspect.
	 * @param e the Entity to check
	 * @return
	 */
	public boolean check(Entity e) {
		return check(e.manager.componentManager.getEntityComponentBits(e));
	}

	/**
	 * Will check if a put of component bits is of interest to this Aspect.
	 * @param componentBits
	 * @return
	 */
	boolean check(BitSet componentBits) {
		boolean interested = true; // possibly interested, let's try to prove it wrong.

		if (allSet.isEmpty() && exclusionSet.isEmpty() && oneSet.isEmpty()) return false;

		// Check if the entity possesses ALL of the components defined in the aspect.
		if(!allSet.isEmpty()) {
			for (int i = allSet.nextSetBit(0); i >= 0; i = allSet.nextSetBit(i+1)) {
				if(!componentBits.get(i)) {
					interested = false;
					break;
				}
			}
		}

		// Check if the entity possesses ANY of the exclusion components, if it does then the system is not interested.
		if(!exclusionSet.isEmpty() && interested) {
			interested = !exclusionSet.intersects(componentBits);
		}

		// Check if the entity possesses ANY of the components in the oneSet. If so, the system is interested.
		if(!oneSet.isEmpty() && interested) {
			interested = oneSet.intersects(componentBits);
		}

		return interested;
	}
}
