package com.share.jack.friendcircledemo.login.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.cygtool.http.NewBaseApi;
import com.share.jack.friendcircledemo.api.FriendApi;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class LoginModel extends NewBaseApi {

    private FriendApi mServletApi;

    public LoginModel() {
        super();
        mServletApi = mRetrofit.create(FriendApi.class);
    }

    public static LoginModel getInstance() {
        return getPresent(LoginModel.class);
    }

    public void execute(String username, String password, Subscriber<UserProfile> subscriber) {
        Observable observable = mServletApi.login(username, password)
                .map(new HttpResultFunc<UserProfile>());
        toSubscribe(observable, subscriber);
    }
}
