package com.example.thomas.plan.viewPlanInfo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.ArrayList;
import java.util.List;

public class ViewPlanInfoViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Plan> viewedPlan;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<List<Task>> listOfTasks;

    public ViewPlanInfoViewModel(Repository mRepository) {
        this.viewedPlan = new MutableLiveData<>();
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
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                listOfTasks.setValue(tasks);
            }
        },viewedPlanId.getValue());
    }

    public void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    public MutableLiveData<Plan> getViewedPlan() {
        return viewedPlan;
    }

    public void setViewedPlan (String planId){
        repository.getPlan(planId,new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                viewedPlan.setValue(plan);
            }
        });
    }

    public void deleteTask(String taskId){
        repository.deleteTask(taskId);
    }

    public void taskChecked(String taskId) {
        //repository.updatePlan();
    }
}
