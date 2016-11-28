package com.share.jack.friendcircledemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.cygtool.util.CygDialogUtil;
import com.share.jack.friendcircledemo.login.LoginActivity;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.as_btn_to_main)
    Button asBtnToMain;
    @Bind(R.id.as_btn_to_out)
    Button asBtnToOut;

    public static void start(Context context) {
        CygActivity.start(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        asBtnToMain.setOnClickListener(this);

        asBtnToOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.as_btn_to_main:
                MainActivity.start(this);
                break;
            case R.id.as_btn_to_out:
                logout();
                break;
        }
    }

    private void logout() {
        CygDialogUtil.confirm(this, "注销登录?", "确定注销登录吗?", new CygDialogUtil.OnConfirmListener() {
            @Override
            public void onConfirm(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                UserSession.destroyUserSession();
                Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                LoginActivity.start(thisActivity());
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}