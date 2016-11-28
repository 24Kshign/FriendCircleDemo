package com.share.jack.friendcircledemo.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.share.jack.cygtool.http.callback.CygSubscriberApi;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.cygtool.util.CygStringUtil;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.SettingActivity;
import com.share.jack.friendcircledemo.login.model.LoginModel;
import com.share.jack.friendcircledemo.login.model.UserProfile;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.register.RegisterActivity;
import com.share.jack.friendcircledemo.util.ConfUtil;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @Bind(R.id.al_et_username)
    EditText alEtUsername;
    @Bind(R.id.al_et_password)
    EditText alEtPassword;
    @Bind(R.id.al_btn_login)
    Button alBtnLogin;
    @Bind(R.id.al_btn_register)
    Button alBtnRegister;
    private UserProfile userProfile;

    public static void start(Context context) {
        CygActivity.start(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initListener();
    }

    private void initListener() {
        alBtnLogin.setOnClickListener(this);
        alBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        String username = CygStringUtil.getEditTextContent(alEtUsername);
        String password = CygStringUtil.getEditTextContent(alEtPassword);
        switch (v.getId()) {
            case R.id.al_btn_login:
                if (!username.isEmpty() && !password.isEmpty()) {
                    LoginModel.getInstance().execute(username, password, new CygSubscriberApi<UserProfile>(thisActivity(), true) {
                        @Override
                        protected void onBaseNext(UserProfile data) {
                            userProfile = data;
                            setJPush(data);
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.al_btn_register:
                RegisterActivity.start(this);
                break;
        }
    }

    //设置极光推送
    private void setJPush(UserProfile data) {
        mHandler.sendMessage(mHandler.obtainMessage(ConfUtil.MSG_SET_ALIAS, data.getAlias()));
    }

    //极光服务器设置别名是否成功的回调
    private final TagAliasCallback tagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tagSet) {
            switch (code) {
                case 0:
                    Log.i("LoginActivity", "设置别名成功");
                    SettingActivity.start(thisActivity());
                    UserSession.createUserSession(userProfile);
                    finish();
                    break;
                case 6002:
                    String logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(ConfUtil.MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    Log.i("LoginActivity", "设置别名失败");
                    break;
            }
        }
    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConfUtil.MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用JPush接口来设置别名。
                    JPushInterface.setAliasAndTags(thisActivity(),
                            (String) msg.obj, null, tagAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
