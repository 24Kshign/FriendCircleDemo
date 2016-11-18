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

    private static UserProfile userProfile;

    public static void createUserSession(UserProfile data) {
        SharePreferenceUtil.setBoolean(ConfUtil.LOGIN_INFO, ConfUtil.SHARE_IS_LOGIN, true);
        SharePreferenceUtil.setInt(ConfUtil.LOGIN_INFO, ConfUtil.USER_ID, data.getId());
        SharePreferenceUtil.setString(ConfUtil.LOGIN_INFO, ConfUtil.USER_NAME, data.getUsername());
    }

    public static void destroyUserSession() {
        SharePreferenceUtil.clearSharePreference(ConfUtil.LOGIN_INFO);
    }

    public static UserProfile getUserProfile() {
        if (userProfile == null) {
            userProfile = new UserProfile();
        }
        userProfile.setId(SharePreferenceUtil.getInt(ConfUtil.LOGIN_INFO, ConfUtil.USER_ID, -1));
        userProfile.setUsername(SharePreferenceUtil.getString(ConfUtil.LOGIN_INFO, ConfUtil.USER_NAME, "24K纯帅"));
        return userProfile;
    }
}