package com.example.thomas.plan.Model;

import android.text.TextUtils;

import com.example.thomas.plan.Presenter.LoginPresenter;
import com.example.thomas.plan.View.LoginView;

/**
 * Created by Thomas on 24.01.2018.
 */

public class PresenterImpl implements LoginPresenter
{

    LoginView mLoginView;

    public PresenterImpl(LoginView mLoginView) {
        this.mLoginView = mLoginView;
    }


    @Override
    public void performLogin(String username, String password) {

        if (TextUtils.isEmpty(username) ||  TextUtils.isEmpty(password))
        {
            mLoginView.loginValidations();
        } else
        {
            if(username.equals("admin") && password.equals("admin")) {
                mLoginView.loginSuccess();
            } else {
                mLoginView.loginError();
            }
        }

    }
}
