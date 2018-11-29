package com.example.thomas.plan.loginAndRegister;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Repository;

/**
 * Created by pospe on 11.02.2018.
 */

public class LoginViewModel extends ViewModel {

    private Repository repository;

    private final MutableLiveData<String> loggedUser = new MutableLiveData<>();
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private MutableLiveData<Client> loggedClient = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Nurse> loggedNurse = new MutableLiveData<>();

    MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LoginViewModel(Repository repository){
        this.repository = repository;
    }

    MutableLiveData<String> getLoggedUser() {
        return loggedUser;
    }

    MutableLiveData<LoginState> getLoginState() {
        return loginState;
    }

    MutableLiveData<Client> getLoggedClient() {
        return loggedClient;
    }

    MutableLiveData<Nurse> getLoggedNurse() {
        return loggedNurse;
    }


    void performLoginClient(String username, final String code){
        repository.searchClientByUsername(username, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                if (client.getPassword().equals(code)){
                   loginState.setValue(LoginState.RESULT_OK);
                   loggedClient.setValue(client);
                } else {
                    loginState.setValue(LoginState.ERROR_CREDENTIALS);
                }
            }
        });
    }

    void saveNurse(Nurse nurse){
        repository.saveNurse(nurse);
    }

    void getNurseByEmail(String email){
        repository.searchNurseByEmail(email, new DataSource.LoadNurseCallback() {
            @Override
            public void onNurseLoaded(Nurse nurse) {
                loggedNurse.setValue(nurse);
            }
        });
    }
}
