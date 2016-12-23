package hlk.com.hlkblog.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

//    @Bind(R.id.srf_layout)
//    SwipeRefreshLayout srf_layout;
//    @Bind(R.id.recycler_view)
//    RecyclerView recycler_view;

//    private List<BlogListInfo> blogListInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTitle();
//        initRecycleView();
//        registerListener();
//        initData();
    }

    private void initTitle() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        TextUtils.isEmpty("");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
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
