package com.example.thomas.plan.viewPlanInfo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.Collections;
import java.util.List;

public class ViewPlanInfoViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Plan> viewedPlan;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<List<Task>> listOfTasks;

    public ViewPlanInfoViewModel(Repository mRepository) {
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
                Collections.sort(tasks);
                listOfTasks.setValue(tasks);
            }
        },viewedPlanId.getValue());
    }

    void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    MutableLiveData<Plan> getViewedPlan() {
        if(viewedPlan == null){
            viewedPlan = new MutableLiveData<>();
        }
        setViewedPlan(viewedPlanId.getValue());
        return viewedPlan;
    }

    void setViewedPlan(String planId){
        repository.getPlan(planId,new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                viewedPlan.setValue(plan);
            }
        });
    }

    void deleteTaskFromPlan(String planId, final Task task){
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                repository.deleteTaskFromPlan(plan.getUniqueID(),task.getUniqueID());
            }
        });
    }

    void updateTask(String planId, Task task){
        repository.saveTask(task, planId, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }
}
