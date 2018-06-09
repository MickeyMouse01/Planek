package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

public class PreviewClientViewModel extends ViewModel {

    Repository repository;

    private MutableLiveData<String> viewedClientId = new MutableLiveData<>();
    private MutableLiveData<Client> viewedClient;
    private MutableLiveData<Plan> selectedPlan;

    public PreviewClientViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setViewedPlanId(String viewedPlanId) {
        this.viewedClientId.setValue(viewedPlanId);
    }


    public MutableLiveData<Client> getViewedClient() {
        if (viewedClient == null) {
            viewedClient = new MutableLiveData<>();
            loadClient();
        }
        return viewedClient;
    }

    private void loadClient() {
        repository.getClient(viewedClientId.getValue(), new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                viewedClient.setValue(client);
            }
        });
    }

    public MutableLiveData<Plan> getSelectedPlan(String planId) {
        if (selectedPlan == null) {
            selectedPlan = new MutableLiveData<>();
            loadPlan(planId);
        }
        return selectedPlan;
    }

    private void loadPlan(String planId) {
        if (planId != null){
            repository.getPlan(planId, new DataSource.LoadPlanCallback() {
                @Override
                public void onPlanLoaded(@NonNull Plan plan) {
                    selectedPlan.setValue(plan);
                }
            });
        }
    }
}
