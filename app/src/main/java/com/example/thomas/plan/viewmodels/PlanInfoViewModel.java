package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.Collections;
import java.util.List;

public class PlanInfoViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Plan> viewedPlan;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<List<Task>> listOfTasks;

    public PlanInfoViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public MutableLiveData<List<Task>> getTasks() {
        if (listOfTasks == null) {
            listOfTasks = new MutableLiveData<>();
            loadTasks();
        }
        return listOfTasks;
    }

    private void loadTasks(){
        repository.getTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if (tasks != null){
                    Collections.sort(tasks);
                }
                listOfTasks.setValue(tasks);
            }
        },viewedPlanId.getValue());
    }

    public void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    public MutableLiveData<Plan> getViewedPlan() {
        if(viewedPlan == null){
            viewedPlan = new MutableLiveData<>();
        }
        setViewedPlan(viewedPlanId.getValue());
        return viewedPlan;
    }

    public void setViewedPlan(String planId){
        repository.getPlan(planId,new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                viewedPlan.setValue(plan);
            }
        });
    }

    public void deleteTaskFromPlan(String planId, final Task task){
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                repository.deleteTaskFromPlan(plan.getUniqueID(),task.getUniqueID());
            }
        });
    }

    public void updateTask(String planId, Task task){
        repository.saveTask(task, planId, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }
}
