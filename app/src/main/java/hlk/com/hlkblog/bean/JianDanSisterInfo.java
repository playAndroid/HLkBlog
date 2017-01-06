package hlk.com.hlkblog.bean;

import java.util.List;

/**
 * 煎蛋妹子
 * Created by user on 2017/1/6.
 */

public class JianDanSisterInfo {

    public String count;
    public String current_page;
    public String status;
    public String total_comments;
    public String page_count;
    public List<Comments> comments;

    @Override
    public String toString() {
        return "JianDanSisterInfo{" +
                "count='" + count + '\'' +
                ", current_page='" + current_page + '\'' +
                ", status='" + status + '\'' +
                ", total_comments='" + total_comments + '\'' +
                ", page_count='" + page_count + '\'' +
                '}';
    }

   public class Comments {
        public String comment_author;
        public List<String> pics;

        @Override
        public String toString() {
            return "comments{" +
                    "comment_author='" + comment_author + '\'' +
                    ", pics=" + pics +
                    '}';
        }
    }
}
