package com.share.jack.friendcircledemo.main.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.friendcircledemo.api.BaseModel;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class PraiseModel extends BaseModel {

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
