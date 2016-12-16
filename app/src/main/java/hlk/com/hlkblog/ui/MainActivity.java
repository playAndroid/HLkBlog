package hlk.com.hlkblog.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;
import hlk.com.hlkblog.adapter.ArticleRecycleAdapter;
import hlk.com.hlkblog.bean.BlogListInfo;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.srf_layout)
    SwipeRefreshLayout srf_layout;
    @Bind(R.id.recycler_view)
    RecyclerView recycler_view;

    private List<BlogListInfo> blogListInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecycleView();
        registerListener();
        initData();
    }

    private void registerListener() {
        srf_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srf_layout.setRefreshing(false);
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(manager);
    }

    private void initData() {
        OkHttpUtils
                .get()
                .url("http://blog.csdn.net/data_hlk/article/list/1")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                pareHtml(response);
                return response;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.e("hlk", "onError");
            }

            @Override
            public void onResponse(Object response, int id) {
                Logger.e("hlk", "onResponse");
                ArticleRecycleAdapter adapter = new ArticleRecycleAdapter(MainActivity.this, blogListInfos);
                recycler_view.setAdapter(adapter);
            }
        });

    }

    private void pareHtml(Response response) throws IOException {
        //子线程
        InputStream inputStream = response.body().byteStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String html = sb.toString();
        Document document = Jsoup.parse(html);
        List<String> titleList = new ArrayList<>();
        Elements linkTitle = document.getElementsByClass("link_title");
        for (Element element : linkTitle) {
            titleList.add(element.text());
        }
        //link_view
        List<String> redCountList = new ArrayList<>();
        Elements redCounts = document.getElementsByClass("link_view");
        for (Element element : redCounts) {
            redCountList.add(element.text());
        }
        //link_comments
        List<String> linkCountList = new ArrayList<>();
        Elements link_comments = document.getElementsByClass("link_comments");
        for (Element element : link_comments) {
            linkCountList.add(element.text());
        }
        List<String> timesList = new ArrayList<>();
        Elements link_postdates = document.getElementsByClass("link_postdate");
        for (Element element : link_postdates) {
            timesList.add(element.text());
        }

        for (int i = 0; i < titleList.size(); i++) {
            BlogListInfo info = new BlogListInfo();
            info.setTitle(titleList.get(i));
            info.setCommentCount(linkCountList.get(i));
            info.setReadCount(redCountList.get(i));
            info.setTime(timesList.get(i));
            blogListInfos.add(info);
        }
    }
}
