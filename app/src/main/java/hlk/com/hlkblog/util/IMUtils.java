package hlk.com.hlkblog.util;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import hlk.com.hlkblog.bean.ChatMessage;
import hlk.com.hlkblog.bean.CommonException;
import hlk.com.hlkblog.bean.Result;
import hlk.com.hlkblog.net.HttpUtils;
import okhttp3.Call;

/**
 * IM工具类
 * Created by user on 2017/1/18.
 */

public class IMUtils {
    private static String API_KEY = "534dc342ad15885dffc10d7b5f813451";
    private static String URLS = "http://www.tuling123.com/openapi/api";

    /**
     * 发送消息
     *
     * @param msg 内容
     * @return ChatMessage 消息对象
     */
    public static ChatMessage sendMassage(String msg) {
        final ChatMessage message = new ChatMessage();
        String url = setParams(msg);
        HttpUtils.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                throw new CommonException("服务器连接错误");
            }

            @Override
            public void onResponse(String response, int id) {
                Result result = JSON.parseObject(response, Result.class);
                if (result.getCode() > 400000 || result.getText() == null || result.getText().trim().equals("")) {
                    message.setMsg("该功能有待开发");
                } else {
                    message.setMsg(result.getText());
                }
                message.setType(ChatMessage.Type.INPUT);
                message.setDate(new Date());
            }
        });
        return message;
    }

    /**
     * 拼接Url
     *
     * @param msg 发送的信息
     * @return 访问的url
     */
    private static String setParams(String msg) {

        try {
            msg = URLEncoder.encode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return URLS + "?key=" + API_KEY + "&info=" + msg;
    }
}
