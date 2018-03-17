package com.example.thomas.plan.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.example.thomas.plan.login.LoginState;

/**
 * Created by pospe on 11.02.2018.
 */

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> loggedUser = new MutableLiveData<>();
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();

    public MutableLiveData<String> getLoggedUser() {
        return loggedUser;
    }

    public MutableLiveData<LoginState> getLoginState() {
        return loginState;
    }


    public void performLogin(String username, String password) {

        if (TextUtils.isEmpty(username) ||  TextUtils.isEmpty(password))
        {
            loginState.setValue(LoginState.ERROR_VALIDATIONS);
        } else
        {
            if(username.equals("admin") && password.equals("admin")) {
                loginState.setValue(LoginState.RESULT_OK);
            } else {
                loginState.setValue(LoginState.ERROR_CREDENTIALS);
            }
        }
    }
}
