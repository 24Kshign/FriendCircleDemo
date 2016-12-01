package com.share.jack.friendcircledemo.login.model;

import android.widget.Toast;

import com.share.jack.cygtool.app.CygApplication;
import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.friendcircledemo.api.BaseModel;
import com.share.jack.jpush.JPush;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class LoginModel extends BaseModel {


    public static LoginModel getInstance() {
        return getPresent(LoginModel.class);
    }

    public void execute(String username, String password, Subscriber<UserProfile> subscriber) {
        if (JPush.getRegistrationID().isEmpty()) {
            Toast.makeText(CygApplication.getInstance(), "获取极光注册Id失败,请稍后登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Observable observable = mServletApi.login(username, password, JPush.getRegistrationID())
                .map(new HttpResultFunc<UserProfile>());
        toSubscribe(observable, subscriber);
    }
}
