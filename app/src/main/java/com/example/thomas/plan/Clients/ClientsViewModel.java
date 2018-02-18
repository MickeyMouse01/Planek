package com.example.thomas.plan.Clients;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.Clients.Client;
import com.example.thomas.plan.Common.Enums;

import java.util.ArrayList;

/**
 * Created by pospe on 10.02.2018.
 */

public class ClientsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Client>> mListOfClients;
    private ArrayList<Client> clientList = new ArrayList<>();

    public LiveData<ArrayList<Client>> getUsers() {
        if (mListOfClients == null) {
            mListOfClients = new MutableLiveData<>();
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
