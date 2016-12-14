package hlk.com.hlkblog.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        OkHttpUtils
                .get()
                .url("http://blog.csdn.net/data_hlk/article/list/1")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                //子线程
                InputStream inputStream = response.body().byteStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.e("hlk", "onError");
            }

            @Override
            public void onResponse(Object response, int id) {
                String html = response.toString();
                tv_content.setText(html);
                Document document = Jsoup.parse(html);
                List<Element> titleList = new ArrayList<Element>();
                Elements linkTitle = document.getElementsByClass("link_title");
                StringBuffer sb = new StringBuffer();
                for (Element element : linkTitle) {
                    titleList.add(element);
                    sb.append(element.text());
                }
                tv_content.setText(sb.toString());
            }
        });

    }
}
