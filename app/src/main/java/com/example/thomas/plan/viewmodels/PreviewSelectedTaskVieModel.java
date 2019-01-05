package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.interfaces.DataSource;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.Collections;
import java.util.List;

public class PreviewSelectedTaskVieModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Task>> mListOfTasks;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();

    public PreviewSelectedTaskVieModel(Repository repository){
        this.repository = repository;
    }

    public MutableLiveData<List<Task>> getListOfTasks() {
        if (mListOfTasks == null) {
            mListOfTasks = new MutableLiveData<>();
            loadTasks();
        }

        return mListOfTasks;
    }


    public void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    private void loadTasks(){
        repository.getTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                Collections.sort(tasks);
                mListOfTasks.setValue(tasks);

            }
        },viewedPlanId.getValue());
    }

    public void saveTask(Task task){
        repository.saveTask(task, viewedPlanId.getValue(), new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }
}
