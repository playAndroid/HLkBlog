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
//        Param[] params1 = map2Params(params);
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                builder.addParams(entry.getKey(), entry.getValue());
            }
        }
        builder.build().execute(callback);

//                .addParams("username", "hyman")
//                .addParams("password", "123")
//                .build()
//                .execute(callback);
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

//    private void registerListener() {
//        srf_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                srf_layout.setRefreshing(false);
//            }
//        });
//    }
//
//    private void initRecycleView() {
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler_view.setLayoutManager(manager);
//    }
//
//    private void initData() {
//        OkHttpUtils
//                .get()
//                .url("http://blog.csdn.net/data_hlk/article/list/1")
//                .build().execute(new Callback() {
//            @Override
//            public Object parseNetworkResponse(Response response, int id) throws Exception {
//                pareHtml(response);
//                return response;
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Logger.e("hlk", "onError");
//            }
//
//            @Override
//            public void onResponse(Object response, int id) {
//                Logger.e("hlk", "onResponse");
//                ArticleRecycleAdapter adapter = new ArticleRecycleAdapter(MainActivity.this, blogListInfos);
//                recycler_view.setAdapter(adapter);
//            }
//        });
//
//    }
//
//    private void pareHtml(Response response) throws IOException {
//        //子线程
//        InputStream inputStream = response.body().byteStream();
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder sb = new StringBuilder();
//        String line = "";
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        String html = sb.toString();
//        Document document = Jsoup.parse(html);
//        List<String> titleList = new ArrayList<>();
//        Elements linkTitle = document.getElementsByClass("link_title");
//        for (Element element : linkTitle) {
//            titleList.add(element.text());
//        }
//        //link_view
//        List<String> redCountList = new ArrayList<>();
//        Elements redCounts = document.getElementsByClass("link_view");
//        for (Element element : redCounts) {
//            redCountList.add(element.text());
//        }
//        //link_comments
//        List<String> linkCountList = new ArrayList<>();
//        Elements link_comments = document.getElementsByClass("link_comments");
//        for (Element element : link_comments) {
//            linkCountList.add(element.text());
//        }
//        List<String> timesList = new ArrayList<>();
//        Elements link_postdates = document.getElementsByClass("link_postdate");
//        for (Element element : link_postdates) {
//            timesList.add(element.text());
//        }
//
//        for (int i = 0; i < titleList.size(); i++) {
//            BlogListInfo info = new BlogListInfo();
//            info.setTitle(titleList.get(i));
//            info.setCommentCount(linkCountList.get(i));
//            info.setReadCount(redCountList.get(i));
//            info.setTime(timesList.get(i));
//            blogListInfos.add(info);
//        }
//    }
}
