package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;

/**
 * Created by Tomas on 18-Apr-18.
 */

public class ClientInfoViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Client> viewedClient;

    public ClientInfoViewModel(Repository mRepository) {
        this.viewedClient = new MutableLiveData<>();
        this.repository = mRepository;
    }

    public MutableLiveData<Client> getViewedClient() {
        return viewedClient;
    }

    public void setViewedClient(String clientId){
        repository.getClient(clientId,new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                    viewedClient.setValue(client);
            }
        });
    }
}
