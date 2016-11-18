package com.share.jack.friendcircledemo.main.model;

import com.share.jack.cygtool.http.HttpResultFunc;
import com.share.jack.cygtool.http.NewBaseApi;
import com.share.jack.friendcircledemo.api.FriendApi;
import com.share.jack.friendcircledemo.main.bean.CommentData;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class CommentModel extends NewBaseApi {

    private FriendApi mServletApi;

    public CommentModel() {
        super();
        mServletApi = mRetrofit.create(FriendApi.class);
    }

    public static CommentModel getInstance() {
        return getPresent(CommentModel.class);
    }

    public void execute(int articleId, CommentData data, Subscriber<Void> subscriber) {
        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("fromUserId", data.getFromUserId());
        map.put("toUserId", data.getToUserId());
        map.put("isRootComment", data.isRootComment());
        map.put("comment", data.getContent());
        map.put("createTime", data.getTime());
        Observable observable = mServletApi.comment(map)
                .map(new HttpResultFunc<Void>());
        toSubscribe(observable, subscriber);
    }
}
