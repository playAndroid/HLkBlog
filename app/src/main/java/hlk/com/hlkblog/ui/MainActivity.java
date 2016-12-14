package hlk.com.hlkblog.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
                .url("http://blog.csdn.net/data_hlk?viewmode=contents")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
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
                Logger.e("hlk", "onResponse" + response.toString());
                tv_content.setText(response.toString());
            }
        });

    }
}
