package com.share.jack.friendcircledemo.util;

import android.app.Activity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 *
 */
public class TipDialog {

    /**
     * 提示框
     *
     * @param activity
     * @param title
     * @param content
     * @param negativeText
     * @param positiveText
     * @param onConfirmListener
     * @return
     */
    public static MaterialDialog confirm(Activity activity, CharSequence title, CharSequence content
            , CharSequence positiveText, CharSequence negativeText, final OnConfirmListener onConfirmListener) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .negativeText(negativeText)
                .positiveText(positiveText)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        if (onConfirmListener != null) {
                            onConfirmListener.onConfirm(dialog, which);
                        }
                    }
                }).show();
    }

    /**
     * 确认框
     */
    public static MaterialDialog confirmDialog(Activity activity,
                                         CharSequence title, CharSequence content,
                                         OnConfirmListener onConfirmListener) {
        return confirm(activity, title, content, "确定", "取消", onConfirmListener);
    }

    public interface OnConfirmListener {
        void onConfirm(MaterialDialog materialDialog, DialogAction dialogAction);
    }

}