package hlk.com.hlkblog.util;

import android.content.Context;
import android.widget.Toast;

/**
 * hlk
 * Created by user on 2016/12/27.
 */

public class ToastUtils {
    private static Toast toast;

    public static void toast_short(Context context,CharSequence charSequence) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT);
        }
        toast.setText(charSequence);
        toast.show();
    }

    public static void toast_long(Context context,CharSequence charSequence) {
        if (toast == null) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_LONG);
        }
        toast.setText(charSequence);
        toast.show();
    }
}
