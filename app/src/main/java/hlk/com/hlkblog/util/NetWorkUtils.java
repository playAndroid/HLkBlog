package hlk.com.hlkblog.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 检查网络工具类
 * Created by user on 2017/1/18.
 */

public class NetWorkUtils {


    /**
     * 检测网络是否连接
     *
     * @param context
     * @return ture 是  false 否
     */
    public static boolean isNetWorkConnectied(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        result = networkInfo != null && networkInfo.isConnected();
        return result;
    }


    //    public static final int TYPE_WIFI = 1;//WIFI数据连接
//    public static final int MOBILE = 0;//移动网络数据连接
//    public static final int VPN = 17; //虚拟网络连接。需要Android5.0以上
//    public static final int NONE = 4; //设备处于离线状态
//    public static final int UNKNOWN = 5; // - 未知数据连接
    private static final String TYPE_WIFI = "wifi";
    private static final String TYPE_3G = "mobile";
    private static final String TYPE_GPRS = "gprs";

    /**
     * 获取网络连接类型
     *
     * @return -1表示没有网络
     */
    public static String getNetType(Context c) {
        ConnectivityManager conn = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn == null) {
            return "";
        }
        NetworkInfo info = conn.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return "";
        }

        int type = info.getType(); // MOBILE（GPRS）;WIFI
        if (type == ConnectivityManager.TYPE_WIFI) {
            return TYPE_WIFI;
        } else {
            TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            switch (tm.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return TYPE_GPRS;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return TYPE_GPRS;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return TYPE_GPRS;
                default:
                    return TYPE_3G;
            }
        }
    }


    /**
     * 是否联接 wifi
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetWorkWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo.getType() == 1;
    }
}
