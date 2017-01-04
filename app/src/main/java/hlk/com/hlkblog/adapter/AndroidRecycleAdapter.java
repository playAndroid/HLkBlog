package hlk.com.hlkblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hlk.com.hlkblog.R;
import hlk.com.hlkblog.bean.GankBean;

/**
 * Android 适配器
 * Created by user on 2017/1/3.
 */

public class AndroidRecycleAdapter extends RecyclerView.Adapter<AndroidRecycleAdapter.ViewHolder> {

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
    public AndroidRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_android_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidRecycleAdapter.ViewHolder  holder, int position) {
        holder.title.setText(mList.get(position).desc);
        holder.who.setText(mList.get(position).who);
        holder.time.setText(mList.get(position).createdAt);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView dest;
        private TextView who;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            dest = (TextView) itemView.findViewById(R.id.dest);
            who = (TextView) itemView.findViewById(R.id.who);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
