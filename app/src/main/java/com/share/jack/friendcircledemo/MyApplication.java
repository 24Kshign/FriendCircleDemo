package com.share.jack.friendcircledemo;

import com.share.jack.cygtool.app.CygApplication;
import com.share.jack.cygtool.app.HttpServletAddress;

import cn.jpush.android.api.JPushInterface;

/**
 *
 */
public class MyApplication extends CygApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);
        HttpServletAddress.getInstance().setOnlineAddress("http://192.168.1.115:8080/FriendCircle/");
//        HttpServletAddress.getInstance().setOnlineAddress("http://192.168.0.108:8080/FriendCircle/");
    }
}
