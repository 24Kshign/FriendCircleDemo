package com.share.jack.friendcircledemo.api;

import com.share.jack.cygtool.http.BaseResponse;
import com.share.jack.friendcircledemo.login.model.UserProfile;
import com.share.jack.friendcircledemo.main.bean.DynamicData;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface FriendApi {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("LoginServlet")
    Observable<BaseResponse<UserProfile>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("RegisterServlet")
    Observable<BaseResponse<Void>> register(@Field("username") String username, @Field("password") String password);

    /**
     * 发布
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("PublishServlet")
    Observable<BaseResponse<Void>> publish(@FieldMap Map<String, String> params);

    /**
     * 获取动态列表
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("GetDynamicListServlet")
    Observable<BaseResponse<List<DynamicData>>> getDynamicList(@Field("userId") int id);
}