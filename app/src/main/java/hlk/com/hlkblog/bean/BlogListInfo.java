package hlk.com.hlkblog.bean;

import java.io.Serializable;

/**
 * 博客文章列表
 * Created by hlk on 2016/12/14.
 */

public class BlogListInfo implements Serializable {
    /**
     * 文章题目
     */
    private String title;
    /**
     * 阅读次数
     */
    private String readCount;
    /**
     * 评论次数
     */
    private String commentCount;

    /**
     * 写作时间
     */
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
}
