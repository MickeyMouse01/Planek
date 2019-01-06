package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.Collections;
import java.util.List;

public class PreviewPlanForClientViewModel extends ViewModel {

   private MutableLiveData<Plan> selectedPlan;
   private Repository repository;
   private MutableLiveData<String> viewedClientId = new MutableLiveData<>();
   private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
   private MutableLiveData<String> nameOfDay = new MutableLiveData<>();
   private MutableLiveData<Client> viewedClient;
   private MutableLiveData<List<Task>> listOfTasks;
   private SingleLiveEvent<String> mShowToastWithMessage = new SingleLiveEvent<>();

    public PreviewPlanForClientViewModel(Repository repository) {
        this.repository = repository;
    }

    public String getViewedPlanId() {
        return viewedPlanId.getValue();
    }

    public void setViewedPlanId(String planId) {
        this.viewedPlanId.setValue(planId);
    }

    public SingleLiveEvent<String> showToastWithMessage(){
        return mShowToastWithMessage;
    }

    public void setViewedClientId(String viewedClientId) {
        this.viewedClientId.setValue(viewedClientId);
    }

    public void setNameOfDay(String nameOfDay) {
        this.nameOfDay.setValue(nameOfDay);
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
        if (!viewedPlanId.getValue().isEmpty()) {
            repository.getPlan(viewedPlanId.getValue(), new DataSource.LoadPlanCallback() {
                @Override
                public void onPlanLoaded(@NonNull Plan plan) {
                    if (plan != null) {
                        selectedPlan.setValue(plan);
                    } else {
                        deletePlanFromClient(nameOfDay.getValue());
                    }

                }
            });
        }
    }

    public MutableLiveData<List<Task>> getTasks() {
        if (listOfTasks == null) {
            listOfTasks = new MutableLiveData<>();
        }
        loadTasks();
        return listOfTasks;
    }

    private void loadTasks() {
        repository.getTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                Collections.sort(tasks);
                listOfTasks.setValue(tasks);
            }
        }, viewedPlanId.getValue());
    }

    public void deletePlanFromClient(String nameOfDay) {
        Client client = getViewedClient().getValue();
        client.getPlansForDate().remove(nameOfDay);
        repository.saveClient(client, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {
                showToastWithMessage().setValue(message);
            }
        });
    }

    public void deleteTaskFromPlan(String planId, final Task task) {
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                repository.deleteTaskFromPlan(plan.getUniqueID(), task.getUniqueID());
            }
        });
    }


    public void saveUpdatedClient(Client client){
        repository.saveClient(client, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {
                showToastWithMessage().setValue(message);
            }
        });
    }

    public void updateTask(Task task){
        repository.saveTask(task, viewedPlanId.getValue(), new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }
}


