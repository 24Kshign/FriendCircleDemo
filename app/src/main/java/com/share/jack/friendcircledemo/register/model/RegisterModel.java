package com.share.jack.friendcircledemo.register.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.friendcircledemo.api.BaseModel;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class RegisterModel extends BaseModel {

    public static RegisterModel getInstance() {
        return getPresent(RegisterModel.class);
    }

    public void execute(String username, String password, Subscriber<Void> subscriber) {
        Observable observable = mServletApi.register(username, password)
                .map(new HttpResultFunc<Void>());
        toSubscribe(observable, subscriber);
    }
}
