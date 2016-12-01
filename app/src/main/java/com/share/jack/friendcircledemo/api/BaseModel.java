package com.share.jack.friendcircledemo.api;

import com.share.jack.cygtool.http.NewBaseApi;

/**
 * Created by Jack on 2016/12/1.
 */
public class BaseModel extends NewBaseApi {

    protected FriendApi mServletApi;

    public BaseModel() {
        super();
        mServletApi = mRetrofit.create(FriendApi.class);
    }
}
