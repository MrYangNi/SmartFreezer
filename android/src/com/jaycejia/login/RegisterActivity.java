package com.jaycejia.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.jaycejia.R;
import com.jaycejia.beans.UserInfo;
import com.jaycejia.databinding.ActivityRegisterBinding;
import com.jaycejia.network.ProgressSubscriber;
import com.jaycejia.network.RetrofitException;
import com.jaycejia.network.RetrofitHandler;
import com.jaycejia.service.UserService;
import com.jaycejia.utils.LogUtil;
import com.jaycejia.utils.ToastUtil;

import okhttp3.ResponseBody;

/**
 * Created by NiYang on 2017/4/30.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_register, null, false);
        setContentView(this.binding.getRoot());

        initView();
    }

    private void initView() {
        this.binding.ivBack.setOnClickListener(this);
        this.binding.btnRegister.setOnClickListener(this);
    }

    private void register() {
        if (TextUtils.isEmpty(this.binding.etAccountNumber.getText())) {
            ToastUtil.showToast("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(this.binding.etNicName.getText())) {
            ToastUtil.showToast("昵称不能为空");
            return;
        }
        if (TextUtils.isEmpty(this.binding.etPassword.getText())) {
            ToastUtil.showToast("密码不能为空");
            return;
        }
        UserInfo userInfo = new UserInfo(null, this.binding.etAccountNumber.getText().toString(), this.binding.etPassword.getText().toString(), null, this.binding.etNicName.getText().toString(), null, null, null, null, null);
        RetrofitHandler.getService(UserService.class).registerUser(userInfo).subscribe(new ProgressSubscriber<ResponseBody>(this) {
            @Override
            protected void onFail(Throwable e) {
                LogUtil.e(TAG, e.getMessage());
            }

            @Override
            protected void onSuccess(ResponseBody responseBody) {
                ToastUtil.showToast("注册成功");
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
            case R.id.btn_register:
                register();
                break;
            default:break;
        }
    }
}
