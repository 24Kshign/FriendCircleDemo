package com.share.jack.cygtool.http.callback;

import android.app.Activity;
import android.util.Log;

import com.share.jack.cygtool.http.progress.ProgressCancelListener;
import com.share.jack.cygtool.http.progress.ProgressDialogHandler;
import com.share.jack.cygtool.util.CygActivityUtil;

import rx.Subscriber;

/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private final static String TAG = "BaseSubscriber";

    protected abstract void onBaseError(Throwable t);

    protected abstract void onBaseNext(T data);

    protected abstract boolean isNeedProgressDialog();

    private ProgressDialogHandler mProgressDialogHandler;
    private Activity activity;

    public BaseSubscriber(Activity activity) {
        this.activity = activity;
        mProgressDialogHandler = new ProgressDialogHandler(activity, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onError(Throwable t) {
        if (!CygActivityUtil.isActive(activity)) {
            return;
        }
        //关闭进度条
        if (isNeedProgressDialog()) {
            dismissProgressDialog();
        }
        onBaseError(t);
    }

    //订阅开始
    @Override
    public void onStart() {
        if (!CygActivityUtil.isActive(activity)) {
            return;
        }
        Log.d(TAG, "http is start");
        //显示进度条
        if (isNeedProgressDialog()) {
            showProgressDialog();
        }
    }

    //订阅完成
    @Override
    public void onCompleted() {
        if (!CygActivityUtil.isActive(activity)) {
            return;
        }
        Log.d(TAG, "http is Complete");
        //关闭进度条
        if (isNeedProgressDialog()) {
            dismissProgressDialog();
        }
    }

    @Override
    public void onNext(T t) {
        if (!CygActivityUtil.isActive(activity)) {
            return;
        }
        onBaseNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
