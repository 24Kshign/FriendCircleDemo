package com.share.jack.friendcircledemo;

import android.os.Bundle;
import android.os.Handler;

import com.share.jack.friendcircledemo.login.LoginActivity;
import com.share.jack.friendcircledemo.login.model.UserSession;

/**
 * 欢迎界面
 */
public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (UserSession.isLogin()) {
                    SettingActivity.start(thisActivity());
                } else {
                    LoginActivity.start(thisActivity());
                }
            }
        }, 2000);
    }
}