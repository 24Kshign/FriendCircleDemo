package com.share.jack.friendcircledemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //   ┃　　　┃   神兽保佑
 * //   ┃　　　┃   阿弥陀佛
 * //   ┃　　　┗━━━┓
 * //   ┃　　　　　　　┣┓
 * //   ┃　　　　　　　┏┛
 * //   ┗┓┓┏━┳┓┏┛
 * //     ┃┫┫　┃┫┫
 * //     ┗┻┛　┗┻┛
 * //
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentSystemUI(ContextCompat.getColor(thisActivity(), R.color.title_bar_background),
                ContextCompat.getColor(thisActivity(), R.color.title_bar_background));
    }

    public Activity thisActivity() {
        return this;
    }

    public void setTranslucentSystemUI(int statusBarColor, int navigationBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(statusBarColor);
            getWindow().setNavigationBarColor(navigationBarColor);
        }
    }
}
