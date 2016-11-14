package com.share.jack.friendcircledemo.main.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.cygtool.http.NewBaseApi;
import com.share.jack.friendcircledemo.api.FriendApi;
import com.share.jack.friendcircledemo.main.bean.DynamicData;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class MainModel extends NewBaseApi {

    private FriendApi mServletApi;

    public MainModel() {
        super();
        mServletApi = mRetrofit.create(FriendApi.class);
    }

    public static MainModel getInstance() {
        return getPresent(MainModel.class);
    }

    public void execute(int id, Subscriber<List<DynamicData>> subscriber) {
        Observable observable = mServletApi.getDynamicList(id)
                .map(new HttpResultFunc<List<DynamicData>>());
        toSubscribe(observable, subscriber);
    }
}
