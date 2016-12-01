package com.share.jack.friendcircledemo.register;

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
import com.share.jack.friendcircledemo.register.model.RegisterModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ar_et_username)
    EditText arEtUsername;
    @Bind(R.id.ar_et_password)
    EditText arEtPassword;
    @Bind(R.id.ar_btn_register)
    Button arBtnRegister;

    public static void start(Context context) {
        CygActivity.start(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initListener();
    }

    private void initListener() {
        arBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = CygStringUtil.getEditTextContent(arEtUsername);
        String password = CygStringUtil.getEditTextContent(arEtPassword);
        if (!username.isEmpty() && !password.isEmpty()) {
            RegisterModel.getInstance().execute(username, password, new CygSubscriberApi<Void>(thisActivity(), "正在注册,请稍后...") {
                @Override
                protected void onBaseNext(Void data) {
                    Toast.makeText(RegisterActivity.this, "注册成功,请登录", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}