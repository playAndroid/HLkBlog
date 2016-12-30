package hlk.com.hlkblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hlk.com.hlkblog.R;
import hlk.com.hlkblog.bean.GankBean;

/**
 * 福利适配器
 * Created by user on 2016/12/30.
 */

public class WealRecycleAdapter extends RecyclerView.Adapter<WealRecycleAdapter.ViewHolder> {


    private List<GankBean.Results> mList;
    private Context mContext;

    public WealRecycleAdapter(Context context, List<GankBean.Results> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setGankBean(List<GankBean.Results> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public WealRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weal, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(WealRecycleAdapter.ViewHolder holder, int position) {
        Picasso.with(mContext).load(mList.get(position).url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_weal);
        }
    }
}
