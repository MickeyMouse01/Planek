package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class PreviewClientViewModel extends ViewModel {

    Repository repository;

    public ObservableList<Plan> mListOfPlans;

    private MutableLiveData<String> viewedClientId = new MutableLiveData<>();
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<Client> viewedClient;
    public MutableLiveData<Plan> selectedPlan;
    private MutableLiveData<List<Task>> listOfTasks;
    private MutableLiveData<List<Plan>> listOfAllPlans;

    public PreviewClientViewModel(Repository repository) {
        this.repository = repository;
    }

    public String getViewedPlanId() {
        return viewedPlanId.getValue();
    }

    public void setViewedPlanId(String planId) {
        this.viewedPlanId.setValue(planId);
    }

    public void setViewedClientId(String viewedClientId) {
        this.viewedClientId.setValue(viewedClientId);
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

    public MutableLiveData<List<Task>> getTasks() {
        if (listOfTasks == null) {
            listOfTasks = new MutableLiveData<>();
            loadTasks();
        }
        return listOfTasks;
    }

    private void loadTasks(){
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                listOfTasks.setValue(tasks);
            }
        },viewedPlanId.getValue());
    }

    public void deleteSelectedPlan(){
        Client client = getViewedClient().getValue();
        client.setPlanId(null);
        getViewedClient().postValue(client);
        repository.saveClient(client);
    }
}

