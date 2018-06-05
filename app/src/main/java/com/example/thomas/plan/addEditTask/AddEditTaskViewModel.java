package com.example.thomas.plan.addEditTask;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

public class AddEditTaskViewModel extends ViewModel {

    private Repository repository;
    private Plan relatesPlan;

    public AddEditTaskViewModel(Repository repository){
        this.repository = repository;
    }

    public void saveTaskToPlan(String planId, final Task task){
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                plan.addToListOfRelatesTasks(task);
                repository.savePlan(plan);
            }
        });
    }

    public void saveTask(Task newTask,String planId) {
        repository.saveTask(newTask,planId);
    }
}
