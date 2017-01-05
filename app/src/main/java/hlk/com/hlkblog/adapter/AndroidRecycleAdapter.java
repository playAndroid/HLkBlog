package hlk.com.hlkblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hlk.com.hlkblog.R;
import hlk.com.hlkblog.bean.GankBean;
import hlk.com.hlkblog.util.IntentUtils;
import hlk.com.hlkblog.util.StringUtils;

/**
 * Android 适配器
 * Created by user on 2017/1/3.
 */

public class AndroidRecycleAdapter extends RecyclerView.Adapter<AndroidRecycleAdapter.ViewHolder> {

    private ProType mType = ProType.ANDROID;
    private Context mContext;
    private List<GankBean.Results> mList;

    public AndroidRecycleAdapter(Context context, List<GankBean.Results> list, ProType type) {
        this.mContext = context;
        this.mList = list;
        this.mType = type;
    }

    public enum ProType {
        ANDROID, IOS
    }


    public void upData(List<GankBean.Results> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public AndroidRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_android_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidRecycleAdapter.ViewHolder holder, int position) {
        final GankBean.Results results = mList.get(position);
        holder.dest.setText(results.desc);
        holder.who.setText(results.who);
        holder.time.setText(formatTime(results.createdAt));
//        Picasso.with(mContext).load(results.images.get(0)).into(holder.image);
        switch (mType) {
            case ANDROID:
                holder.image.setImageResource(R.drawable.android_bg);
                break;
            case IOS:
                holder.image.setImageResource(R.drawable.ios_bg);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isNullOrEmpty(results.url)) {
                    IntentUtils.openWebPage(results.url, mContext);
                }
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


    private String formatTime(String time) {
        if (StringUtils.isNullOrEmpty(time)) {
            String replace = time.replace("T", " ").replace("Z", " ");
            return StringUtils.getStringForDate(replace);
        }
        return StringUtils.getStringForDate("");
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dest;
        private TextView who;
        private TextView time;
        private final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            dest = (TextView) itemView.findViewById(R.id.dest);
            who = (TextView) itemView.findViewById(R.id.who);
            time = (TextView) itemView.findViewById(R.id.time);
            image = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
