package io.firstwave.gdxkit.behavior;

/**
 * First version created on 4/23/14.
 */
public interface IBlackboard {
	boolean containsKey(String key);

	public Object get(String key);

	public Object remove(String key);

	public void putBoolean(String key, boolean value);

	public boolean getBoolean(String key);

	public boolean getBoolean(String key, boolean defaultValue);

	public void putBooleanArray(String key, boolean[] value);

	public boolean[] getBooleanArray(String key);

	public void putBlackboard(String key, IBlackboard value);

	public IBlackboard getBlackboard(String key);

	public 	void putByte(String key, byte value);

	public 	byte getByte(String key);

	public byte getByte(String key, byte defaultValue);

	public 	void putByteArray(String key, byte[] value);

	public byte[] getByteArray(String key);

	public 	void putChar(String key, char value);

	public char getChar(String key);

	public 	char getChar(String key, char defaultValue);

	public 	void putCharArray(String key, char[] value);

	public char[] getCharArray(String key);

	public void putCharSequence(String key, CharSequence value);

	public CharSequence getCharSequence(String key);

	public 	void putDouble(String key, double value);

	public 	double getDouble(String key);

	public double getDouble(String key, double defaultValue);

	public 	void putDoubleArray(String key, double[] value);

	public double[] getDoubleArray(String key);

	public void putFloat(String key, float value);

	public float getFloat(String key);

	public float getFloat(String key, float defaultValue);

	public 	void putFloatArray(String key, float[] value);

	public 	float[] getFloatArray(String key);

	public 	void putInt(String key, int value);

	public int getInt(String key);

	public 	int getInt(String key, int defaultValue);

	public void putIntArray(String key, int[] value);

	public 	int[] getIntArray(String key);

	public void putLong(String key, long value);

	public 	long getLong(String key);

	public 	long getLong(String key, long defaultValue);

	public 	void putLongArray(String key, long[] value);

	public 	long[] getLongArray(String key);

	public 	void putShort(String key, short value);

	public 	short getShort(String key);

	public short getShort(String key, short defaultValue);

	public 	void putShortArray(String key, short[] value);

	public short[] getShortArray(String key);

	public 	void putString(String key, String value);

	public 	String getString(String key);

	public 	String getString(String key, String defaultValue);

	public void putStringArray(String key, String[] value);

	public String[] getStringArray(String key);
}
