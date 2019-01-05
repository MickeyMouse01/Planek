package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.Common.LoginState;

/**
 * Created by pospe on 11.02.2018.
 */

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> loggedUser = new MutableLiveData<>();
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private Repository repository;
    private MutableLiveData<Client> loggedClient = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Nurse> loggedNurse = new MutableLiveData<>();

    public LoginViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
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

    public MutableLiveData<Nurse> getLoggedNurse() {
        return loggedNurse;
    }


    public void performLoginClient(String username, final String code) {
        repository.searchClientByUsername(username, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                if (client == null) {
                    loginState.setValue(LoginState.ERROR_CREDENTIALS);
                } else if (client.getPassword().equals(code)) {
                    loginState.setValue(LoginState.RESULT_OK);
                    loggedClient.setValue(client);
                } else {
                    loginState.setValue(LoginState.ERROR_CREDENTIALS);
                }
            }
        });
    }

    public void saveNurse(Nurse nurse) {
        repository.saveNurse(nurse, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    public void getNurseByEmail(String email) {
        repository.searchNurseByEmail(email, new DataSource.LoadNurseCallback() {
            @Override
            public void onNurseLoaded(Nurse nurse) {
                loggedNurse.setValue(nurse);
            }
        });
    }
}
