package io.firstwave.gdxkit.behavior;

import java.util.HashMap;
import java.util.Map;

/**
 * A Blackboard represents the state of an Agents behavior. Since behavior trees are stateless and potentially shared between agents,
 * using a Blackboard allows you to persist state on a per-agent basis.
 * Provides a mapping from String to various serializable types
 * First version created on 4/13/14.
 */
public class Blackboard implements IBlackboard {

	private final Map<String, Object> mMap;

	public Blackboard() {
		mMap = new HashMap<String, Object>();
	}

	public Blackboard(Blackboard toCopy) {
		mMap = toCopy.mMap;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Blackboard(this);
	}

	private void typeWarning(String key, String type) {
		System.out.println("Couldn't cast mapped value for '" + key + "' to " + type);
	}

	@Override
	public boolean containsKey(String key) {
		return mMap.containsKey(key);
	}

	@Override
	public Object get(String key) {
		return mMap.get(key);
	}

	@Override
	public Object remove(String key) {
		return mMap.remove(key);
	}

	@Override
	public void putBoolean(String key, boolean value) {
		mMap.put(key, value);
	}

	@Override
	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Boolean) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Boolean");
			return defaultValue;
		}
	}

	@Override
	public void putBooleanArray(String key, boolean[] value) {
		mMap.put(key, value);
	}
	
	@Override
	public boolean[] getBooleanArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (boolean[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "boolean[]");
			return null;
		}
	}

	@Override
	public void putBlackboard(String key, IBlackboard value) {
		mMap.put(key, value);
	}

	@Override
	public IBlackboard getBlackboard(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (IBlackboard) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Blackboard");
			return null;
		}
	}

	@Override
	public void putByte(String key, byte value) {
		mMap.put(key, value);
	}
	
	@Override
	public byte getByte(String key) {
		return getByte(key, (byte) 0);
	}

	@Override
	public byte getByte(String key, byte defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Byte) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Byte");
			return defaultValue;
		}
	}

	@Override
	public void putByteArray(String key, byte[] value) {
		mMap.put(key, value);
	}
	
	@Override
	public byte[] getByteArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (byte[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "byte[]");
			return null;
		}
	}

	@Override
	public void putChar(String key, char value) {
		mMap.put(key, value);
	}
	
	@Override
	public char getChar(String key) {
		return getChar(key, (char) 0);
	}

	@Override
	public char getChar(String key, char defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Character) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Character");
			return defaultValue;
		}
	}

	@Override
	public void putCharArray(String key, char[] value) {
		mMap.put(key, value);
	}
	
	@Override
	public char[] getCharArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (char[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "char[]");
			return null;
		}
	}

	@Override
	public void putCharSequence(String key, CharSequence value) {
		mMap.put(key, value);
	}
	
	@Override
	public CharSequence getCharSequence(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (CharSequence) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "CharSequence");
			return null;
		}
	}

	@Override
	public void putDouble(String key, double value) {
		mMap.put(key, value);
	}
	
	@Override
	public double getDouble(String key) {
		return getDouble(key, 0.0);
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Double) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Double");
			return defaultValue;
		}
	}

	@Override
	public void putDoubleArray(String key, double[] value) {
		mMap.put(key, value);
	}
	
	@Override
	public double[] getDoubleArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (double[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "double[]");
			return null;
		}
	}

	@Override
	public void putFloat(String key, float value) {
		mMap.put(key, value);
	}
	
	@Override
	public float getFloat(String key) {
		return getFloat(key, 0.0f);
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Float) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Float");
			return defaultValue;
		}
	}

	@Override
	public void putFloatArray(String key, float[] value) {
		mMap.put(key, value);
	}
	
	@Override
	public float[] getFloatArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (float[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "float[]");
			return null;
		}
	}

	@Override
	public void putInt(String key, int value) {
		mMap.put(key, value);
	}

	@Override
	public int getInt(String key) {
		return getInt(key, 0);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Integer) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Integer");
			return defaultValue;
		}
	}

	@Override
	public void putIntArray(String key, int[] value) {
		mMap.put(key, value);
	}

	@Override
	public int[] getIntArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (int[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "int[]");
			return null;
		}
	}

	@Override
	public void putLong(String key, long value) {
		mMap.put(key, value);
	}

	@Override
	public long getLong(String key) {
		return getLong(key, 0);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Long) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Long");
			return defaultValue;
		}
	}

	@Override
	public void putLongArray(String key, long[] value) {
		mMap.put(key, value);
	}

	@Override
	public long[] getLongArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (long[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "long[]");
			return null;
		}
	}

	@Override
	public void putShort(String key, short value) {
		mMap.put(key, value);
	}

	@Override
	public short getShort(String key) {
		return getShort(key, (short) 0);
	}

	@Override
	public short getShort(String key, short defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (Short) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Short");
			return defaultValue;
		}
	}

	@Override
	public void putShortArray(String key, short[] value) {
		mMap.put(key, value);
	}

	@Override
	public short[] getShortArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (short[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "short[]");
			return null;
		}
	}

	@Override
	public void putString(String key, String value) {
		mMap.put(key, value);
	}

	@Override
	public String getString(String key) {
		return getString(key, null);
	}

	@Override
	public String getString(String key, String defaultValue) {
		Object rv = mMap.get(key);
		if (rv == null) return defaultValue;
		try {
			return (String) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "String");
			return defaultValue;
		}
	}

	@Override
	public void putStringArray(String key, String[] value) {
		mMap.put(key, value);
	}

	@Override
	public String[] getStringArray(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (String[]) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "String[]");
			return null;
		}
	}
}
