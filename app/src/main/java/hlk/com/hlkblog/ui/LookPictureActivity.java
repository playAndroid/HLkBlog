package hlk.com.hlkblog.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;
import hlk.com.hlkblog.base.BaseActivity;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 查看图片
 * Created by user on 2017/1/3.
 */

public class LookPictureActivity extends BaseActivity {

    //    @Bind(R.id.view_pager)
//    ViewPager mViewPager;
    @Bind(R.id.image_view)
    ImageView mImageView;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        String url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(mImageView);
        PhotoViewAttacher attacher = new PhotoViewAttacher(mImageView);
        attacher.update();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle(R.string.navigation_weal);
        mToolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
