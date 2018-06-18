package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class PreviewClientViewModel extends ViewModel {

    Repository repository;

    public ObservableList<Plan> mListOfPlans;

    private MutableLiveData<String> viewedClientId = new MutableLiveData<>();
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<Client> viewedClient;
    private MutableLiveData<Plan> selectedPlan;
    private MutableLiveData<List<Plan>> listOfAllPlans;

    public PreviewClientViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setViewedPlanId(String planId) {
        this.viewedPlanId.setValue(planId);
    }

    public void setViewedClientId(String viewedClientId) {
        this.viewedClientId.setValue(viewedClientId);
    }

    public MutableLiveData<Client> getViewedClient(boolean initializeClient) {
        if (viewedClient == null) {
            viewedClient = new MutableLiveData<>();
        }
        if (initializeClient) {
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

    public MutableLiveData<Plan> getSelectedPlan() {
        if (selectedPlan == null) {
            selectedPlan = new MutableLiveData<>();
        }
        loadPlan();
        return selectedPlan;
    }

    private void loadPlan() {
        if (viewedPlanId.getValue() != null) {
            repository.getPlan(viewedPlanId.getValue(), new DataSource.LoadPlanCallback() {
                @Override
                public void onPlanLoaded(@NonNull Plan plan) {
                    selectedPlan.setValue(plan);
                }
            });
        }
    }

    public MutableLiveData<List<Plan>> getListOfAllPlans() {
        if (listOfAllPlans == null) {
            listOfAllPlans = new MutableLiveData<>();
            loadAllPlans();
        }
        return listOfAllPlans;
    }


    private void loadAllPlans() {
        repository.getPlans(new DataSource.LoadPlansCallback() {
            @Override
            public void onPlansLoaded(@NonNull List<Plan> plans) {
                listOfAllPlans.setValue(plans);
            }
        });
    }
}

