package com.share.jack.friendcircledemo.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.share.jack.cygtool.http.callback.CygSubscriberApi;
import com.share.jack.cygtool.util.CygActivity;
import com.share.jack.cygtool.util.CygStringUtil;
import com.share.jack.friendcircledemo.BaseActivity;
import com.share.jack.friendcircledemo.R;
import com.share.jack.friendcircledemo.login.model.LoginModel;
import com.share.jack.friendcircledemo.login.model.UserProfile;
import com.share.jack.friendcircledemo.login.model.UserSession;
import com.share.jack.friendcircledemo.main.MainActivity;
import com.share.jack.friendcircledemo.register.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

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
                            UserSession.createUserSession(data);
                            MainActivity.start(thisActivity());
                            finish();
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
}
