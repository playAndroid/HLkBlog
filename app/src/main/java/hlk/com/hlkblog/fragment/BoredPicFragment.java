package hlk.com.hlkblog.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;
import hlk.com.hlkblog.adapter.YoungerSisterRecyclerAdapter;
import hlk.com.hlkblog.bean.JianDanSisterInfo;
import hlk.com.hlkblog.net.HttpUtils;
import okhttp3.Call;

/**
 * 无聊图
 * Created by user on 2017/1/6.
 */

public class BoredPicFragment extends BaseFragment {
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private JianDanSisterInfo sisterInfo = new JianDanSisterInfo();
    private List<JianDanSisterInfo.Comments> resultses = new ArrayList<>();
    private YoungerSisterRecyclerAdapter adapter;
    private int page = 1;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    private void initData() {
//        http://gank.io/api/random/data/Android/20
//        http://gank.io/api/data/福利/10/1

        HttpUtils.get("http://jandan.net/?oxwlxojflwblxbsapi=jandan.get_pic_comments&page=" + page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                isScrolling = true;
            }

            @Override
            public void onResponse(String response, int id) {
                if (page == 1) {
                    resultses.clear();
                }
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                isScrolling = true;
                sisterInfo = JSON.parseObject(response, JianDanSisterInfo.class);
                resultses.addAll(sisterInfo.comments);
                adapter.setSisterBean(resultses);
//                mRecyclerView.setVerticalScrollbarPosition(gankBeen.results.size() - 1);
            }
        });
    }

    private boolean isScrolling;

    private void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        mRefreshLayout.setRefreshing(true);
        adapter = new YoungerSisterRecyclerAdapter(getActivity(), resultses);
        mRecyclerView.setAdapter(adapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
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
                if (isScrolling && itemPosition == itemCount - 1) {
                    isScrolling = true;
//                    mRefreshLayout.setRefreshing(true);
//                    page++;
//                    initData();
                    Snackbar.make(mRecyclerView, "没有数据啦o(╯□╰)o", Snackbar.LENGTH_SHORT).show();
                }
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
