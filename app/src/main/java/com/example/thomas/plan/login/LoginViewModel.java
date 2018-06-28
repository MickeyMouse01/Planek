package com.example.thomas.plan.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.thomas.plan.Clients.ClientsActivity;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.login.LoginState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by pospe on 11.02.2018.
 */

public class LoginViewModel extends ViewModel {

    private Repository repository;

    private final MutableLiveData<String> loggedUser = new MutableLiveData<>();
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private MutableLiveData<Client> loggedClient = new MutableLiveData<>();

    public LoginViewModel(Repository repository){
        this.repository = repository;
    }

    public MutableLiveData<String> getLoggedUser() {
        return loggedUser;
    }

    public MutableLiveData<LoginState> getLoginState() {
        return loginState;
    }

    public MutableLiveData<Client> getLoggedClient() {
        return loggedClient;
    }

    //todo dostat tady apliakcnni kontext
    public void performLogin(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            loginState.setValue(LoginState.ERROR_VALIDATIONS);
        }
    }

    public void performLoginClient(String username, final String code){
        repository.searchClientByUsername(username, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                if (client != null && client.getPassword().equals(code)){
                   loginState.setValue(LoginState.RESULT_OK);
                   loggedClient.setValue(client);
                } else {
                    loginState.setValue(LoginState.ERROR_CREDENTIALS);
                }
            }
        });
    }






}
