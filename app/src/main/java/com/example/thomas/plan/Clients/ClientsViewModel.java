package com.example.thomas.plan.Clients;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.data.Client;
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

    public final SingleLiveEvent<Void> addNewClient = new SingleLiveEvent<>();

    public ClientsViewModel() {

    }


    public LiveData<List<Client>> getUsers() {
        if (mListOfClients == null) {
            mListOfClients = new MutableLiveData<>();
            loadUsers();
        }

        return mListOfClients;
    }

    public SingleLiveEvent<Void> getAddNewClient() {
        return addNewClient;
    }

    public void addClient(Client client){
        clientList.add(client);
        mListOfClients.setValue(clientList);
    }

    private void loadUsers() {
        clientList.add(new Client("Josef","Namornik", Enums.TypeOfGroup.GROUPA));
        clientList.add(new Client("Tomas","Blah", Enums.TypeOfGroup.GROUPB));
        clientList.add(new Client("Sarka","Furit", Enums.TypeOfGroup.GROUPC));
        clientList.add(new Client("Michael","Parnik", Enums.TypeOfGroup.GROUPB));
        clientList.add(new Client("Honza","Lod", Enums.TypeOfGroup.GROUPB));
        clientList.add(new Client("Martin","Neco", Enums.TypeOfGroup.GROUPC));
        mListOfClients.setValue(clientList);
    }

}
