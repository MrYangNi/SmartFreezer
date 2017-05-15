package com.jaycejia.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.jaycejia.MainActivity;
import com.jaycejia.R;
import com.jaycejia.beans.LoginInfo;
import com.jaycejia.beans.UserAuth;
import com.jaycejia.databinding.ActivityLoginBinding;
import com.jaycejia.network.AuthManager;
import com.jaycejia.network.ProgressSubscriber;
import com.jaycejia.network.RetrofitHandler;
import com.jaycejia.service.LoginService;
import com.jaycejia.utils.AnimationUtils;
import com.jaycejia.utils.LogUtil;
import com.jaycejia.utils.ToastUtil;

/**
 * Created by NiYang on 2017/4/30.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding = null;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_login, null, false);
        setContentView(this.binding.getRoot());

        initView();
    }

    private void initView() {
        this.binding.ivBack.setOnClickListener(this);
        this.binding.btnLogin.setOnClickListener(this);
    }

    private void login() {
        if (TextUtils.isEmpty(this.binding.etAccountNumber.getText())) {
            ToastUtil.showToast("您的账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(this.binding.etPassword.getText())) {
            ToastUtil.showToast("您的密码不能为空");
            return;
        }
        LoginInfo loginInfo = new LoginInfo(AuthManager.clientId,this.binding.etAccountNumber.getText().toString(),this.binding.etPassword.getText().toString());
        RetrofitHandler.getService(LoginService.class).login(loginInfo).subscribe(new ProgressSubscriber<UserAuth>(this) {
            @Override
            protected void onFail(Throwable e) {
                LogUtil.e(TAG, e.getMessage());
            }

            @Override
            protected void onSuccess(UserAuth userAuth) {
                LogUtil.d(TAG,"登录成功");
                AuthManager.setToken(userAuth.getToken());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //关闭先前的任务栈，开启新的任务栈盛放MainActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
            default:break;
        }
    }
}
