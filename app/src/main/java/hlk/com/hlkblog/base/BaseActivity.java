package hlk.com.hlkblog.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import hlk.com.hlkblog.ui.MainActivity;
import hlk.com.hlkblog.util.ToastUtils;

/**
 * 小坤
 * Created by user on 2016/12/26.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 替换Fragment
     *
     * @param id_content fragment id
     * @param fragment   fragment
     */
    protected void replaceFragment(int id_content, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id_content, fragment);
        transaction.commit();
    }

    long endTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - endTime > 2000 && this instanceof MainActivity) {
                ToastUtils.toast_short(this, "再按一次退出");
                endTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return false;
    }


}
