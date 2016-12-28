package hlk.com.hlkblog.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import hlk.com.hlkblog.net.HttpUtils;

/**
 * hlk
 * Created by user on 2016/12/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.initHttp();
//        LogUtils.init("MyBlog");

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
