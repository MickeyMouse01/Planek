package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.previewTasks.PreviewTasksViewModel;

public class PreviewClientViewModel extends ViewModel {

    Repository repository;

    private MutableLiveData<String> viewedClientId = new MutableLiveData<>();
    private MutableLiveData<Client> viewedClient = new MutableLiveData<>();

    public PreviewClientViewModel(Repository repository){
        this.repository = repository;
    }

    public void setViewedPlanId(String viewedPlanId) {
        this.viewedClientId.setValue(viewedPlanId);
    }

    public MutableLiveData<Client> getViewedClient() {
        return viewedClient;
    }
}
