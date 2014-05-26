package io.firstwave.gdxkit.util;

/**
 * Provides an interface to allow clients the ability to direct log messages
 */
public interface LogHandler {
	public void handleMessage(int level, String tag, String message, Throwable t);

	/**
	 * Provides a basic implementation of a LogHandler that writes messages to std out/err
	 */
	public static final LogHandler STD = new LogHandler() {
		private final String[] LEVELS = {"NONE", "ERROR", "WARNING", "INFO", "DEBUG", "VERBOSE"};
		private final String DELIM = " \t";
		@Override
		public void handleMessage(int level, String tag, String message, Throwable t) {
			String line = "[" + System.currentTimeMillis() + " " + LEVELS[level] + "]" +
					DELIM + tag + DELIM + message;
			if (level == Log.ERROR) {
				System.err.println(line);
			} else {
				System.out.println(line);
			}
			if (t != null) {
				t.printStackTrace();
			}
		}
	};
}
