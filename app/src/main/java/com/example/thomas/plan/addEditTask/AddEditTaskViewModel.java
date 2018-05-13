package com.example.thomas.plan.addEditTask;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

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
    private void blah(Plan plan, Task task){

    }

    public Plan getRelatesPlan() {
        return relatesPlan;
    }

    public void saveTask(Task newTask,String planId) {
        repository.saveTask(newTask,planId);
    }
}
