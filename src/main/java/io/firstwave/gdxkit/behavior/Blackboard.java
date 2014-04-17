package io.firstwave.gdxkit.behavior;

import java.util.HashMap;
import java.util.Map;

/**
 * A Blackboard represents the state of an Agents behavior. Since behavior trees are stateless and potentially shared between agents,
 * using a Blackboard allows you to persist state on a per-agent basis.
 * Provides a mapping from String to various serializable types
 * First version created on 4/13/14.
 */
public class Blackboard {

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

	public boolean containsKey(String key) {
		return mMap.containsKey(key);
	}

	public Object get(String key) {
		return mMap.get(key);
	}

	public Object remove(String key) {
		return mMap.remove(key);
	}

	public void putBoolean(String key, boolean value) {
		mMap.put(key, value);
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

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

	public void putBooleanArray(String key, boolean[] value) {
		mMap.put(key, value);
	}
	
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

	public void putBlackboard(String key, Blackboard value) {
		mMap.put(key, value);
	}

	public Blackboard getBlackboard(String key) {
		Object rv = mMap.get(key);
		if (rv == null) return null;
		try {
			return (Blackboard) rv;
		} catch(ClassCastException e) {
			typeWarning(key, "Blackboard");
			return null;
		}
	}

	public void putByte(String key, byte value) {
		mMap.put(key, value);
	}
	
	public byte getByte(String key) {
		return getByte(key, (byte) 0);
	}

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

	public void putByteArray(String key, byte[] value) {
		mMap.put(key, value);
	}
	
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

	public void putChar(String key, char value) {
		mMap.put(key, value);
	}
	
	public char getChar(String key) {
		return getChar(key, (char) 0);
	}

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

	public void putCharArray(String key, char[] value) {
		mMap.put(key, value);
	}
	
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

	public void putCharSequence(String key, CharSequence value) {
		mMap.put(key, value);
	}
	
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

	public void putDouble(String key, double value) {
		mMap.put(key, value);
	}
	
	public double getDouble(String key) {
		return getDouble(key, 0.0);
	}

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

	public void putDoubleArray(String key, double[] value) {
		mMap.put(key, value);
	}
	
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

	public void putFloat(String key, float value) {
		mMap.put(key, value);
	}
	
	public float getFloat(String key) {
		return getFloat(key, 0.0f);
	}

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

	public void putFloatArray(String key, float[] value) {
		mMap.put(key, value);
	}
	
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

	public void putInt(String key, int value) {
		mMap.put(key, value);
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

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

	public void putIntArray(String key, int[] value) {
		mMap.put(key, value);
	}

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

	public void putLong(String key, long value) {
		mMap.put(key, value);
	}

	public long getLong(String key) {
		return getLong(key, 0);
	}

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

	public void putLongArray(String key, long[] value) {
		mMap.put(key, value);
	}

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

	public void putShort(String key, short value) {
		mMap.put(key, value);
	}

	public short getShort(String key) {
		return getShort(key, (short) 0);
	}

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

	public void putShortArray(String key, short[] value) {
		mMap.put(key, value);
	}

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

	public void putString(String key, String value) {
		mMap.put(key, value);
	}

	public String getString(String key) {
		return getString(key, null);
	}

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

	public void putStringArray(String key, String[] value) {
		mMap.put(key, value);
	}

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
