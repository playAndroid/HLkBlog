package hlk.com.hlkblog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import hlk.com.hlkblog.adapter.AndroidRecycleAdapter;
import hlk.com.hlkblog.bean.GankBean;
import hlk.com.hlkblog.net.HttpUtils;
import okhttp3.Call;

/**
 * Android
 * Created by user on 2017/1/3.
 */

public class AndroidFragment extends BaseFragment {
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    protected GankBean gankBeen = new GankBean();
    protected List<GankBean.Results> resultses = new ArrayList<>();
    protected AndroidRecycleAdapter adapter;
    protected LinearLayoutManager layoutManager;
    protected int page = 1;
    protected boolean isScrool = false;
    private boolean isiOs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isiOs = this instanceof IOSFragment;
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
        registerListener();
    }

    protected void registerListener() {
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
                int curtPostion = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();
                if (isScrool && curtPostion >= itemCount - 3) {
                    isScrool = false;
                    page++;
                    initData();
                }

            }
        });
    }

    protected void initData() {

        mRefreshLayout.setRefreshing(true);
        String project = isiOs ? "iOS" : "Android";
        HttpUtils.get("http://gank.io/api/data/" + project + "/10/" + page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isScrool = true;
                closeRefresh();
            }

            @Override
            public void onResponse(String response, int id) {
                if (page == 1) {
                    resultses.clear();
                }
                isScrool = true;
                gankBeen = JSON.parseObject(response, GankBean.class);
                resultses.addAll(gankBeen.results);
                closeRefresh();
                adapter.upData(resultses);
            }
        });
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_light);
        adapter = new AndroidRecycleAdapter(getContext(), resultses, isiOs ? AndroidRecycleAdapter.ProType.IOS : AndroidRecycleAdapter.ProType.ANDROID);
        mRecyclerView.setAdapter(adapter);
    }


    protected void closeRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
