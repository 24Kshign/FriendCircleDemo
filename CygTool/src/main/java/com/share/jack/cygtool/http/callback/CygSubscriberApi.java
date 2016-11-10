package com.share.jack.cygtool.http.callback;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.share.jack.cygtool.http.ApiException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 */
public abstract class CygSubscriberApi<T> extends BaseSubscriber<T> {

    private static final String TAG = "CygSubscriberApi";

    private boolean isNeedProgress;
    private Activity activity;

    public CygSubscriberApi(Activity activity, boolean isNeedProgress) {
        super(activity);
        this.activity = activity;
        this.isNeedProgress = isNeedProgress;
    }

    public CygSubscriberApi(Fragment fragment, boolean isNeedProgress) {
        this(fragment.getActivity(), isNeedProgress);
    }

    @Override
    protected boolean isNeedProgressDialog() {
        return isNeedProgress;
    }

    @Override
    protected void onBaseError(Throwable t) {
        String alert = "";
        if (t instanceof NetworkErrorException || t instanceof UnknownHostException) {
            alert = "网络异常";
        } else if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
            alert = "请求超时";
        } else if (t instanceof IOException) {
            alert = "请求超时";
        } else if (t instanceof ApiException) {
            ApiException exception = (ApiException) t;
            if (exception.isTokenExpried()) {
                alert = "token异常";
            } else {
                alert = t.getMessage();
            }
        }

        Log.e(TAG, "api failure,throw=" + t.getMessage());
        Toast.makeText(activity, "请求失败：" + alert, Toast.LENGTH_SHORT).show();
    }
}
