package com.example.thomas.plan.Clients;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.example.thomas.plan.Clients.Client;
import com.example.thomas.plan.Common.Enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pospe on 10.02.2018.
 */

public class ClientsViewModel extends ViewModel {

    public final MutableLiveData<List<Client>> mListOfClients = new MutableLiveData<>();
    public final List<Client> clientList = new ArrayList<>();

    public final ObservableList<Client> items = new ObservableArrayList<>();

    public ClientsViewModel() {

    }


    public LiveData<List<Client>> getUsers() {
        if (mListOfClients == null) {
            loadUsers();
        }

        return mListOfClients;
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
