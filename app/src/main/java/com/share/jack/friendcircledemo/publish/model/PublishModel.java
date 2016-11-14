package com.share.jack.friendcircledemo.publish.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.cygtool.http.NewBaseApi;
import com.share.jack.friendcircledemo.api.FriendApi;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class PublishModel extends NewBaseApi {

    private FriendApi mServletApi;

    public PublishModel() {
        super();
        mServletApi=mRetrofit.create(FriendApi.class);
    }

    public static PublishModel getInstance() {
        return getPresent(PublishModel.class);
    }

    public void execute(Map<String, String> params, Subscriber<Void> subscriber){
        Observable observable = mServletApi.publish(params)
                .map(new HttpResultFunc<Void>());
        toSubscribe(observable, subscriber);
    }
}
