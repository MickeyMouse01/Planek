package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;

/**
 * Created by Tomas on 08-Apr-18.
 */

public class AddEditClientViewModel extends ViewModel {
    private MutableLiveData<Client> editedClient;
    private Repository repository;
    private SingleLiveEvent<String> onClientSaved = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> onStatusOfUsername= new SingleLiveEvent<>();

    public AddEditClientViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public void saveClient(Client newClient) {
        repository.saveClient(newClient, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {
                onClientSaved().setValue(message);
            }
        });
    }

    public MutableLiveData<Client> getEditedClient(String clientId) {
        if (editedClient == null) {
            editedClient  = new MutableLiveData<>();
            loadClient(clientId);
        }

        return editedClient;
    }

    private void loadClient(String clientId){
        repository.getClient(clientId, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(Client client) {
                editedClient.setValue(client);
            }
        });
    }

    public SingleLiveEvent<String> onClientSaved(){
        return onClientSaved;
    }

    public SingleLiveEvent<Boolean> getIsThereSameUsername() {
        return onStatusOfUsername;
    }

    public void searchUsername(String username){
        repository.searchClientByUsername(username, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(Client client) {
                if (client == null){
                    onStatusOfUsername.setValue(false);
                } else {
                    if (editedClient != null){
                        if (client.getUniqueID().equals(editedClient.getValue().getUniqueID())){
                            onStatusOfUsername.setValue(false);
                        } else {
                            onStatusOfUsername.setValue(true);
                        }
                    } else {
                        onStatusOfUsername.setValue(true);
                    }


                }
            }
        });
    }
}
