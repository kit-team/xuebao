package cn.net.yto.utils;
import android.util.Log;

/**
 * Main class for log.
 *
 */
public class LogUtils {

    /** Application tag prefix for LogCat. **/
    private static final String APP_NAME_PREFIX = "yto";

    /** Stores the enabled state of the LogUtils function. **/
    private static Boolean mEnabled = true;

    /**
     * Write info log string.
     * 
     * @param data String containing data to be logged.
     */
    public static void logI(final String data) {
        if (mEnabled) {
            Thread currentThread = Thread.currentThread();
            Log.i(APP_NAME_PREFIX, "[" + currentThread.getName() + "] " + data);
        }
    }

    /**
     * Write debug log string.
     * 
     * @param data String containing data to be logged.
     */
    public static void logD(final String data) {
        if (mEnabled) {
            Thread currentThread = Thread.currentThread();
            Log.d(APP_NAME_PREFIX, "[" + currentThread.getName() + "] " + data);
        }
    }

    /**
     * Write warning log string.
     * 
     * @param data String containing data to be logged.
     */
    public static void logW(final String data) {
        if (mEnabled) {
            Thread currentThread = Thread.currentThread();
            Log.w(APP_NAME_PREFIX, "[" + currentThread.getName() + "] " + data);
        }
    }

    /**
     * Write error log string.
     * 
     * @param data String containing data to be logged.
     */
    public static void logE(final String data) {

        if (mEnabled) {
            Thread currentThread = Thread.currentThread();
            // temporary fix to avoid crash SMS.
            Log.w(APP_NAME_PREFIX, "[" + currentThread.getName() + "] " + data);
        }
    }

    /**
     * Write verbose log string.
     * 
     * @param data String containing data to be logged.
     */
    public static void logV(final String data) {
        if (mEnabled) {
            Thread currentThread = Thread.currentThread();
            Log.v(APP_NAME_PREFIX, "[" + currentThread.getName() + "] " + data);
        }
    }

    /**
     * Write info log string with specific component name.
     * 
     * @param name String name string to prepend to log data.
     * @param data String containing data to be logged.
     */
    public static void logWithName(final String name, final String data) {
        if (mEnabled) {
            Log.v(APP_NAME_PREFIX + name, data);
        }
    }

    /**
     * Write error log string with Exception thrown.
     * 
     * @param data String containing data to be logged.
     * @param exception Exception associated with error.
     */
    public static void logE(final String data, final Throwable exception) {

        // exception.getClass().toString());
        if (mEnabled) {
            Thread currentThread = Thread.currentThread();
            // temporary fix to avoid crash SMS.
            Log.w(APP_NAME_PREFIX, "[" + currentThread.getName() + "] " + data, exception);
        }
    }

    /***
     * Returns if the logging feature is currently enabled.
     * 
     * @return TRUE if logging is enabled, FALSE otherwise.
     */
    public static Boolean isEnabled() {
        return mEnabled;
    }
    
	private static boolean sLogEnable = true;

	public static void setLogEnable(boolean enable) {
		sLogEnable = enable;
	}
	
	public static void v(String tag, String msg) {
		if (sLogEnable) {
			Log.v(tag, msg);
		}
	}
	
	public static void v(String tag, Exception e) {
		if (sLogEnable) {
			Log.v(tag, "" + e);
		}
	}
	
	public static void v(String tag, String msg, Exception e) {
		if (sLogEnable) {
			Log.v(tag, msg + " " + e);
		}
	}
	
	public static void i(String tag, String msg) {
		if (sLogEnable) {
			Log.i(tag, msg);
		}
	}
	
	public static void i(String tag, Exception e) {
		if (sLogEnable) {
			Log.i(tag, "" + e);
		}
	}
	
	public static void i(String tag, String msg, Exception e) {
		if (sLogEnable) {
			Log.i(tag, msg + " " + e);
		}
	}
	
	public static void d(String tag, String msg) {
		if (sLogEnable) {
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, Exception e) {
		if (sLogEnable) {
			Log.d(tag, "" + e);
		}
	}
	
	public static void d(String tag, String msg, Exception e) {
		if (sLogEnable) {
			Log.d(tag, msg + " " + e);
		}
	}

	public static void w(String tag, String msg) {
		if (sLogEnable) {
			Log.w(tag, msg);
		}
	}
	
	public static void w(String tag, Exception e) {
		if (sLogEnable) {
			Log.w(tag, "" + e);
		}
	}
	
	public static void w(String tag, String msg, Exception e) {
		if (sLogEnable) {
			Log.w(tag, msg + " " + e);
		}
	}
	
	public static void e(String tag, String msg) {
		if (sLogEnable) {
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, Exception e) {
		if (sLogEnable) {
			Log.e(tag, "" + e);
		}
	}
	
	public static void e(String tag, String msg, Exception e) {
		if (sLogEnable) {
			Log.e(tag, msg + " " + e);
		}
	}

}
