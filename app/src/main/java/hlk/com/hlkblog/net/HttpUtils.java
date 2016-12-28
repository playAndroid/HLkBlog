package hlk.com.hlkblog.net;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import hlk.com.hlkblog.util.LogUtils;
import okhttp3.OkHttpClient;

/**
 * hlk
 * Created by user on 2016/12/14.
 */

public class HttpUtils {

    /**
     * 默认初始化
     */
    public static void initHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    /**
     * 包含Cooke初始化
     *
     * @param application application context
     */
    public static void initHttp_cookJar(Context application) {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(application));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化包含Looger
     */
    public static void initHttp_Logger() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 设置可访问所有的https网站
     */
    public static void initHttp_Https() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    public static void get(String url, Callback callback) {
        LogUtils.e(url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(callback);
    }

    public static void post(String url, Map<String, String> params, Callback callback) {
        PostFormBuilder builder = OkHttpUtils
                .post()
                .url(url);
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                builder.addParams(entry.getKey(), entry.getValue());
            }
        }
        builder.build().execute(callback);
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) {
            return new Param[0];
        }

        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }

        return res;
    }


    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
