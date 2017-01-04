package hlk.com.hlkblog.bean;

import java.util.List;

/**
 * hlk
 * Created by user on 2016/12/30.
 */

public class GankBean {
    public boolean error;
    public List<Results> results;


    public class Results {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;

        @Override
        public String toString() {
            return "Results{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GankBean{" +
                "error=" + error +
                ", list=" + results +
                '}';
    }
}
