package com.example.thomas.plan.previewFoodForClient;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PreviewFoodForClientViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<String> clientId = new MutableLiveData<>();
    private MutableLiveData<Plan> selectedPlan;
    private MutableLiveData<Client> viewedClient;
    private MutableLiveData<Task> taskLunch;
    private MutableLiveData<Task> taskDinner;

    public PreviewFoodForClientViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getClientId() {
        return clientId;
    }

    public String getPlanId() {
        return selectedPlan.getValue().getUniqueID();
    }

    public MutableLiveData<Client> getViewedClient() {
        if (viewedClient == null) {
            viewedClient = new MutableLiveData<>();
            loadClient();
        }
        return viewedClient;
    }

    private void loadClient() {
        repository.getClient(clientId.getValue(), new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(Client client) {
                viewedClient.setValue(client);
            }
        });
    }

    public MutableLiveData<Plan> getSelectedPlan(String planId) {
        if (selectedPlan == null) {
            selectedPlan = new MutableLiveData<>();
            loadSelectedPlan(planId);
        }
        return selectedPlan;
    }

    private void loadSelectedPlan(String planId) {
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                selectedPlan.setValue(plan);
            }
        });
    }

    public MutableLiveData<Task> getTaskLunch() {
        if (taskLunch == null) {
            taskLunch = new MutableLiveData<>();
            loadTask(Enums.PartOfDay.LUNCH);
        }
        return taskLunch;
    }

    public MutableLiveData<Task> getTaskDinner() {
        if (taskDinner == null) {
            taskDinner = new MutableLiveData<>();
            loadTask(Enums.PartOfDay.DINNER);
        }
        return taskDinner;
    }

    private void loadTask(final Enums.PartOfDay partOfDay) {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                switch (partOfDay) {
                    case LUNCH:
                        taskLunch.setValue(tasks.get(0));
                        break;
                    case DINNER:
                        taskDinner.setValue(tasks.get(0));
                        break;
                }
            }
        }, selectedPlan.getValue().getUniqueID(), partOfDay);
    }

    void updateTask(Task task){
        repository.saveTask(task, getPlanId(), new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }


    void saveTaskToPlan(Task newTask) {
        Plan plan = selectedPlan.getValue();
        if(plan.getListOfRelatesTasks() != null){
            for (Task task: plan.getListOfRelatesTasks().values()) {
                if (task.getPartOfDay() == newTask.getPartOfDay()){
                    plan.getListOfRelatesTasks().values().remove(task);
                }
            }
        }

        plan.addToListOfRelatesTasks(newTask);
        repository.savePlan(plan, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    void uploadImage(Bitmap bitmap, String name){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        repository.uploadImage(name, data, new DataSource.UploadImageCallback() {
            @Override
            public void onImageUploaded() {
            }
        });
    }
}