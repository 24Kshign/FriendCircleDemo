package com.share.jack.friendcircledemo.api;

import com.share.jack.cygtool.http.BaseResponse;
import com.share.jack.friendcircledemo.login.model.UserProfile;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface FriendApi {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("LoginServlet")
    Observable<BaseResponse<UserProfile>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("RegisterServlet")
    Observable<BaseResponse<Void>> register(@Field("username") String username, @Field("password") String password);

}