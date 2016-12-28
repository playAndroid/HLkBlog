package hlk.com.hlkblog.util;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import hlk.com.hlkblog.BuildConfig;

/**
 * hlk
 * Created by user on 2016/12/27.
 */

public class LogUtils {


    private static final boolean IS_DEBUG = BuildConfig.LOG_DEBUG;

    /**
     * Change the settings with init. This should be called only once.
     * Best place would be in application class. All of them are optional.
     * You can just use the default settings if you don't init Logger.
     */
    public static void init(String tag) {
        Logger
                .init(tag)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2);      // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
    }


    public static void i(String message) {
        if (IS_DEBUG) {
            Logger.i(message);
        }
    }

    public static void v(String message) {
        if (IS_DEBUG) {
            Logger.v(message);
        }
    }

    public static void w(String message) {
        if (IS_DEBUG) {
            Logger.w(message);
        }
    }

    public static void d(String message) {
        if (IS_DEBUG) {
            Logger.d(message);
        }
    }

    public static void e(String message) {
        if (IS_DEBUG) {
            Logger.e(message);
        }
    }


    public static void i(String tag, String message) {
        if (IS_DEBUG) {
            Logger.t(tag).i(message);
        }
    }

    public static void d(String tag, String message) {
        if (IS_DEBUG)
            Logger.t(tag).d(message);
    }

    public static void w(String tag, String message) {
        if (IS_DEBUG)
            Logger.t(tag).w(message);
    }

    public static void e(String tag, String message) {
        if (IS_DEBUG)
            Logger.t(tag).e(message);
    }


    public static void json(String json) {
        if (IS_DEBUG) {
            Logger.json(json);
        }
    }


    public static void json(String tag, String json) {
        if (IS_DEBUG)
            Logger.t(tag).json(json);
    }

    public static void xml(String xml) {
        if (IS_DEBUG) {
            Logger.xml(xml);
        }
    }

    public static void xml(String tag, String xml) {
        if (IS_DEBUG)
            Logger.t(tag).xml(xml);
    }
}
