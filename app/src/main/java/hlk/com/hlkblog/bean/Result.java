package hlk.com.hlkblog.bean;

/**
 * Created by user on 2016/11/3.
 */

public class Result {
    public int code;
    public String text;

    public Result(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public Result() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
