package hlk.com.hlkblog.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import hlk.com.hlkblog.R;

/**
 * this welcome activity
 * Created by hlk on 2016/12/14.
 */

public class SplashActivity extends AppCompatActivity {
    private static final float SCALE_END = 1.13F;
    @Bind(R.id.tv_splash_content)
    TextView content;
    @Bind(R.id.tv_splash_title)
    TextView title;
    @Bind(R.id.iv_splash)
    ImageView splashImage;
    private int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash5,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        String[] splash_content = getResources().getStringArray(R.array.splash_title);
        Random random = new Random(SystemClock.elapsedRealtime());
        Logger.d(SystemClock.elapsedRealtime() + "");
        splashImage.setImageResource(SPLASH_ARRAY[random.nextInt(SPLASH_ARRAY.length)]);
        title.setText("小坤同学");
        content.setText(splash_content[random.nextInt(splash_content.length)]);
        animateImage();

    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(splashImage, "scaleX", 1F, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(splashImage, "scaleY", 1F, SCALE_END);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
    }

}
