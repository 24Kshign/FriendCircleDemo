package com.share.jack.cygtool.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class CygActivityUtil {

    public CygActivityUtil() {
    }

    public static boolean isActive(Activity activity) {
        if (activity == null) {
            return false;
        } else if (activity.isFinishing()) {
            return false;
        }
        return true;
    }

    public static View layoutInflate(Activity activity, int layoutResource) {
        return activity.getLayoutInflater().inflate(layoutResource, null);
    }

    public static View getRootView(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }
}
