package hlk.com.hlkblog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hlk.com.hlkblog.R;
import hlk.com.hlkblog.bean.BlogListInfo;

/**
 * 文章列表适配器
 * Created by user on 2016/12/15.
 */

public class ArticleRecycleAdapter extends RecyclerView.Adapter<ArticleRecycleAdapter.ViewHodler> {

    private Context mContext;
    private List<BlogListInfo> listData;

    public ArticleRecycleAdapter(Context context, List<BlogListInfo> data) {
        this.mContext = context;
        this.listData = data;
    }

    @Override
    public ArticleRecycleAdapter.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_article_list, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(ArticleRecycleAdapter.ViewHodler holder, int position) {
        holder.tv_title.setText(listData.get(position).getTitle());
        holder.tv_read_count.setText(listData.get(position).getReadCount());
        holder.tv_time.setText(listData.get(position).getTime());
        holder.tv_comment_count.setText(listData.get(position).getCommentCount());
        holder.tv_description.setText(listData.get(position).getArticle_description());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_title)
        TextView tv_title;
        //        @Bind(R.id.tv_time)
        TextView tv_time;
        //        @Bind(R.id.tv_read_count)
        TextView tv_read_count;
        //        @Bind(R.id.tv_comment_count)
        TextView tv_comment_count;

        TextView tv_description;

        public ViewHodler(View itemView) {
            super(itemView);
//            ButterKnife.bind(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_read_count = (TextView) itemView.findViewById(R.id.tv_read_count);
            tv_comment_count = (TextView) itemView.findViewById(R.id.tv_comment_count);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }
}
