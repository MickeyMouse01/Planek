package com.example.thomas.plan.Clients;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;

import java.util.List;

/**
 * Created by pospe on 10.02.2018.
 */

public class ClientsViewModel extends ViewModel {

    public ObservableList<Client> mListOfClients;
    public final SingleLiveEvent<Void> addNewClient = new SingleLiveEvent<>();

    private Repository repository;

    public ClientsViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public ObservableList<Client> getClients() {
        if (mListOfClients == null) {
            mListOfClients = new ObservableArrayList<>();
            loadClients();
        }
        return mListOfClients;
    }

    public SingleLiveEvent<Void> getAddNewClient() {
        return addNewClient;
    }

    private void loadClients() {

        repository.getClients(new DataSource.LoadClientsCallback() {
            @Override
            public void onClientsLoaded(@NonNull List<Client> clients) {
                mListOfClients.clear();
                mListOfClients.addAll(clients);
            }
        });
    }
}
