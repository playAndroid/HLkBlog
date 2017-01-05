package hlk.com.hlkblog.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Intent启动 工具类
 */
public class IntentUtils {


    /**
     * 打开浏览器
     *
     * @param url
     * @param context
     */
    public static void openWebPage(String url, Context context) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
