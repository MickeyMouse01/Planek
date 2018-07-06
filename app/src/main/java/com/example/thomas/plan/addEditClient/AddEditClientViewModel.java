package com.example.thomas.plan.addEditClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;

/**
 * Created by Tomas on 08-Apr-18.
 */

public class AddEditClientViewModel extends ViewModel {
    public MutableLiveData<Client> editedClient;
    private Repository repository;

    public AddEditClientViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public void saveClient(Client newClient) {
        repository.saveClient(newClient);
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
}
