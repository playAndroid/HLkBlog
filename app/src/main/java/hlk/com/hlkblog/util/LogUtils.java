package hlk.com.hlkblog.util;

import com.orhanobut.logger.Logger;

/**
 * hlk
 * Created by user on 2016/12/27.
 */

public class LogUtils {


    private static final boolean IS_DEBUG = true;

    public static void i(String tag, String message) {
        if (IS_DEBUG) {
            Logger.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (IS_DEBUG)
            Logger.d(tag, message);
    }

    public static void w(String tag, String message) {
        if (IS_DEBUG)
            Logger.w(tag, message);
    }

    public static void e(String tag, String message) {
        if (IS_DEBUG)
            Logger.e(tag, message);
    }
}
