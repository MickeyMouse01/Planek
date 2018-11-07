package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class PreviewClientViewModel extends ViewModel {

    public ObservableList<Plan> mListOfPlans;
    private MutableLiveData<Plan> selectedPlan;
    private Repository repository;
    private MutableLiveData<String> viewedClientId = new MutableLiveData<>();
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<Client> viewedClient;
    private MutableLiveData<List<Task>> listOfTasks;

    public PreviewClientViewModel(Repository repository) {
        this.repository = repository;
    }

    String getViewedPlanId() {
        return viewedPlanId.getValue();
    }

    void setViewedPlanId(String planId) {
        this.viewedPlanId.setValue(planId);
    }

    void setViewedClientId(String viewedClientId) {
        this.viewedClientId.setValue(viewedClientId);
    }

    MutableLiveData<Client> getViewedClient() {
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

    MutableLiveData<Plan> getSelectedPlan() {
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
                    if (plan != null) {
                        selectedPlan.setValue(plan);
                    } else {
                        deletePlanFromClient();
                    }

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

    private void loadTasks() {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                listOfTasks.setValue(tasks);
            }
        }, viewedPlanId.getValue());
    }

    void deletePlanFromClient() {
        Client client = getViewedClient().getValue();
        client.setPlanId(null);
        repository.saveClient(client, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {
            }
        });
    }

    void deleteTaskFromPlan(String planId, final Task task) {
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                repository.deleteTaskFromPlan(plan.getUniqueID(), task.getUniqueID());
            }
        });
    }


    void saveUpdatedClient(Client client){
        repository.saveClient(client, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    void updateTask(Task task){
        repository.saveTask(task, viewedPlanId.getValue(), new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }
}


