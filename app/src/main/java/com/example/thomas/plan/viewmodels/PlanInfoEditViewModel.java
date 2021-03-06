package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

public class PlanInfoEditViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Plan> viewedPlan;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<List<Task>> listOfTasks;
    private SingleLiveEvent<String> onPlanSaved = new SingleLiveEvent<>();
    private SingleLiveEvent<String> imageIsUploaded = new SingleLiveEvent<>();

    public PlanInfoEditViewModel(Repository repository) {
        this.viewedPlan = new MutableLiveData<>();
        this.repository = repository;
    }

    public MutableLiveData<List<Task>> getTasks() {
        if (listOfTasks == null) {
            listOfTasks = new MutableLiveData<>();
            loadTasks();
        }
        return listOfTasks;
    }

    private void loadTasks() {
        repository.getTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if (tasks != null){
                    Collections.sort(tasks);
                }
                listOfTasks.setValue(tasks);
            }
        }, viewedPlanId.getValue());
    }

    public SingleLiveEvent<String> getOnPlanSaved() {
        return onPlanSaved;
    }
    public SingleLiveEvent<String> getOnImageIsUploaded() {
        return imageIsUploaded;
    }

    public void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    public MutableLiveData<Plan> getViewedPlan() {
        return viewedPlan;
    }

    public void setViewedPlan(String planId) {
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                viewedPlan.setValue(plan);
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

    public void updateTask(String planId, Task task) {
        repository.saveTask(task, planId, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    public void updatePlan(Plan plan) {
        repository.savePlan(plan, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {
                onPlanSaved.setValue(message);
            }
        });
    }


    public void uploadImage(Bitmap bitmap, String name) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        repository.uploadImage(name, data, new DataSource.UploadImageCallback() {
            @Override
            public void onImageUploaded() {
                imageIsUploaded.call();
            }
        });
    }
}
