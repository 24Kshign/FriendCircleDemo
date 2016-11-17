package com.share.jack.friendcircledemo.main.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.cygtool.http.NewBaseApi;
import com.share.jack.friendcircledemo.api.FriendApi;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class PraiseModel extends NewBaseApi {

    private FriendApi mServletApi;

    public PraiseModel() {
        super();
        mServletApi = mRetrofit.create(FriendApi.class);
    }

    public static PraiseModel getInstance() {
        return getPresent(PraiseModel.class);
    }

    public void execute(String isPraise, int articleId, int userId, int toUserId, Subscriber<Void> subscriber) {
        Map<String, Integer> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("userId", userId);
        map.put("toUserId", toUserId);
        Observable observable = mServletApi.praise(isPraise, map)
                .map(new HttpResultFunc<Void>());
        toSubscribe(observable, subscriber);
    }
}
