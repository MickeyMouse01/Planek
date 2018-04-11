package com.example.thomas.plan.addEditClient;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;

/**
 * Created by Tomas on 08-Apr-18.
 */

public class AddEditClientViewModel extends ViewModel {
    public ObservableField<Client> newClient = new ObservableField<>();


    private Repository repository;

    public AddEditClientViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public void saveClient(Client newClient) {
        repository.saveClient(newClient);
    }

}
