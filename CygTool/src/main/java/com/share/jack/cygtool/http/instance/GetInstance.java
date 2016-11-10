package com.share.jack.cygtool.http.instance;

import android.util.Log;

/**
 *
 */
public class GetInstance {

    private static final String TAG = "GetInstance";

    private GetInstance() {

    }

    public static <T> T getInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}