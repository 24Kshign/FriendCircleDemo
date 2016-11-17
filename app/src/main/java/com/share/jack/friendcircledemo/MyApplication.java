package com.share.jack.friendcircledemo;

import com.share.jack.cygtool.app.CygApplication;
import com.share.jack.cygtool.app.HttpServletAddress;
import com.share.jack.jpush.JPush;

/**
 *
 */
public class MyApplication extends CygApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JPush.init();
//        HttpServletAddress.getInstance().setOnlineAddress("http://192.168.1.103:8080/FriendCircle/");
//        HttpServletAddress.getInstance().setOnlineAddress("http://192.168.1.115:8080/FriendCircle/");
        HttpServletAddress.getInstance().setOnlineAddress("http://192.168.1.103:8080/FriendCircle/");
    }
}
