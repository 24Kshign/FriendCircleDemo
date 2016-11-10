package com.share.jack.cygtool.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 *
 */
public class CygActivity {

    private static final String TAG = "CygActivity";

    public static final int DEFAULT_FLAGS = 0;

    public CygActivity() {
    }

    private static Intent newIntent(Context context, Class<? extends Activity> cls, Bundle extras, int flags) {
        Intent intent = new Intent(context, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        intent.setFlags(flags);
        return intent;
    }

    public static void start(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "error");
        }
    }

    public static void start(Context context, Class<? extends Activity> cls, Bundle extras, int flags) {
        start(context, newIntent(context, cls, extras, flags));
    }

    public static void start(Context context, Class<? extends Activity> cls, Bundle extras) {
        start(context, cls, extras, DEFAULT_FLAGS);
    }

    public static void start(Context context, Class<? extends Activity> cls) {
        start(context, cls, null, DEFAULT_FLAGS);
    }
}