package hlk.com.hlkblog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import hlk.com.hlkblog.net.ConastantString;
import hlk.com.hlkblog.net.HttpUtils;
import hlk.com.hlkblog.view.ProgressWheel;
import okhttp3.Call;
import okhttp3.Response;

/**
 * hlk
 * 博客
 * Created by user on 2016/12/20.
 */

public class BlogFragment extends Fragment {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mRefresh;

    @Bind(R.id.loading)
    ProgressWheel mLoading;

    private ArrayList<BlogListInfo> infos = new ArrayList<>();
    private ArticleRecycleAdapter mAdatper;
    private int page = 1;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mRefresh.setRefreshing(true);
        loadData();
        registerListener();
    }

    boolean isScrolling;

    private void registerListener() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                infos.clear();
                loadData();
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();
                if (isScrolling && itemPosition >= itemCount - 3) {
                    isScrolling = false;
                    mLoading.setVisibility(View.VISIBLE);
                    page++;
                    loadData();
                }
            }

        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
    }

    private void loadData() {

        //http://blog.csdn.net/data_hlk/article/list/1
        HttpUtils.get(ConastantString.HTTP_BLOG_LIST + page, new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                pareHtml(response);
                return response;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isScrolling = false;
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Object response, int id) {
                mAdatper.notifyDataSetChanged();
                if (mRefresh.isRefreshing()) {
                    mRefresh.setRefreshing(false);
                }
                isScrolling = true;
                mLoading.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRefresh.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        mAdatper = new ArticleRecycleAdapter(getActivity(), infos);
        mRecyclerView.setAdapter(mAdatper);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

        //article_description
        List<String> descriptionList = new ArrayList<>();
        Elements article_description = document.getElementsByClass("article_description");
        for (Element element : article_description) {
            descriptionList.add(element.text());
        }

        for (int i = 0; i < titleList.size(); i++) {
            BlogListInfo info = new BlogListInfo();
            info.setTitle(titleList.get(i));
            info.setCommentCount(linkCountList.get(i));
            info.setReadCount(redCountList.get(i));
            info.setTime(timesList.get(i));
            info.setArticle_description(descriptionList.get(i));
            infos.add(info);
        }

    }
}
