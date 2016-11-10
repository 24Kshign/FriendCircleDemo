package com.share.jack.friendcircledemo.login.model;

import com.share.jack.cygtool.util.SharePreferenceUtil;
import com.share.jack.friendcircledemo.util.ConfUtil;

/**
 *
 */
public class UserSession {

    public static boolean isLogin() {
        return SharePreferenceUtil.getBoolean(ConfUtil.LOGIN_INFO, ConfUtil.SHARE_IS_LOGIN, false);
    }

    public static void createUserSession(UserProfile data) {
        SharePreferenceUtil.setBoolean(ConfUtil.LOGIN_INFO, ConfUtil.SHARE_IS_LOGIN, true);
        SharePreferenceUtil.setInt(ConfUtil.LOGIN_INFO, ConfUtil.USER_ID, data.getId());
        SharePreferenceUtil.setString(ConfUtil.USER_NAME, ConfUtil.USER_NAME, data.getUsername());
    }

    public static void destroyUserSession() {
        SharePreferenceUtil.clearSharePreference(ConfUtil.LOGIN_INFO);
    }
}