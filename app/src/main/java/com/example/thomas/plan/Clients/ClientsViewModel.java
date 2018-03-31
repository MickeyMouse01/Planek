package com.example.thomas.plan.Clients;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thomas.plan.data.ClientsRepository;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pospe on 10.02.2018.
 */

public class ClientsViewModel extends ViewModel {

    public MutableLiveData<List<Client>> mListOfClients;
    public final List<Client> clientList = new ArrayList<>();

    private ClientsRepository clientsRepository;


    public final SingleLiveEvent<Void> addNewClient = new SingleLiveEvent<>();

    public ClientsViewModel(ClientsRepository mClientsRepository) {
        this.clientsRepository = mClientsRepository;
    }

    public LiveData<List<Client>> getClients() {
        if (mListOfClients == null) {
            mListOfClients = new MutableLiveData<>();
            loadClients();
        }
        return mListOfClients;
    }

    public SingleLiveEvent<Void> getAddNewClient() {
        return addNewClient;
    }

    private void loadClients() {
        clientsRepository.getClients(new DataSource.LoadClientsCallback() {
            @Override
            public void onClientsLoaded(@NonNull List<Client> clients) {
            //    Log.d("pocet", clients.get(0).getUniqueID());
                mListOfClients.setValue(clients);
            }
        });


    }

}
