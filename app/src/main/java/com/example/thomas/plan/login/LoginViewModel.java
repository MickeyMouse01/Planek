package com.example.thomas.plan.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.thomas.plan.Clients.ClientsActivity;
import com.example.thomas.plan.login.LoginState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

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
            loginState.setValue(LoginState.RESULT_OK);
        }
    }


}
