package hlk.com.hlkblog.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;
import hlk.com.hlkblog.base.BaseActivity;
import hlk.com.hlkblog.fragment.AndroidFragment;
import hlk.com.hlkblog.fragment.BoredPicFragment;
import hlk.com.hlkblog.fragment.IOSFragment;
import hlk.com.hlkblog.fragment.RobotFragment;
import hlk.com.hlkblog.fragment.VideoFragment;
import hlk.com.hlkblog.fragment.WealFragment;
import hlk.com.hlkblog.fragment.YoungerSisterFragment;
import hlk.com.hlkblog.util.NetWorkUtils;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation)
    NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTitle();
        initData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetWorkUtils.isNetWorkConnectied(context)) {
                        Snackbar.make(mNavigationView, "网络已连接" + NetWorkUtils.getNetType(context), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(mNavigationView, "网络已断开", Snackbar.LENGTH_LONG).show();
                        showOpenWifiDialog();
                    }
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void initData() {
        replaceFragment(R.id.frame_content, new WealFragment());
    }

    private void initTitle() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String itemTitle = (String) item.getTitle();
                mToolbar.setTitle(itemTitle);
                switch (itemTitle) {
                    case "Android":
                        replaceFragment(R.id.frame_content, new AndroidFragment());
                        break;
                    case "福利":
                        replaceFragment(R.id.frame_content, new WealFragment());
                        break;
                    case "iOS":
                        replaceFragment(R.id.frame_content, new IOSFragment());
                        break;
                    case "休息视频":
                        replaceFragment(R.id.frame_content, new VideoFragment());
                        break;
                    case "妹子":
                        replaceFragment(R.id.frame_content, new YoungerSisterFragment());
                        break;
                    case "无聊图":
                        replaceFragment(R.id.frame_content, new BoredPicFragment());
                        break;
                    case "小智":
                        replaceFragment(R.id.frame_content,new RobotFragment());
                        break;
                }

//                Snackbar.make(mNavigationView, item.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
//                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void showOpenWifiDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("是否打开网络连接?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

//
}
