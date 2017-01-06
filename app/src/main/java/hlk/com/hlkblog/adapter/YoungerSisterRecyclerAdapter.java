package hlk.com.hlkblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hlk.com.hlkblog.R;
import hlk.com.hlkblog.bean.JianDanSisterInfo;
import hlk.com.hlkblog.ui.LookPictureActivity;

/**
 * younger sister
 * Created by user on 2017/1/6.
 */

public class YoungerSisterRecyclerAdapter extends RecyclerView.Adapter<YoungerSisterRecyclerAdapter.ViewHolder> {
    private List<JianDanSisterInfo.Comments> mList;
    private Context mContext;

    public YoungerSisterRecyclerAdapter(Context context, List<JianDanSisterInfo.Comments> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setSisterBean(List<JianDanSisterInfo.Comments> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public YoungerSisterRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weal, parent, false);
        return new YoungerSisterRecyclerAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(YoungerSisterRecyclerAdapter.ViewHolder holder, int position) {
        final String url = mList.get(position).pics.get(0);
        Picasso.with(mContext).load(url).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LookPictureActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });
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

        private ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_weal);
        }
    }
}
