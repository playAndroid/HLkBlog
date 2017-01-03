package hlk.com.hlkblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import hlk.com.hlkblog.bean.GankBean;

/**
 * Android 适配器
 * Created by user on 2017/1/3.
 */

public class AndroidRecycleAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<GankBean.Results> mList;

    public AndroidRecycleAdapter(Context context, List<GankBean.Results> list) {
        this.mContext = context;
        this.mList = list;
    }


    public void upData(List<GankBean.Results> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }
}
