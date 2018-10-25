package com.example.thomas.plan.selectedTask;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class SelectedTaskVieModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Task>> mListOfTasks;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
        private String NAME_OF_CLASS = getClass().getName();

    public SelectedTaskVieModel(Repository repository){
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
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
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
