package com.example.thomas.plan.previewTasks;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.ArrayList;
import java.util.List;

public class PreviewTasksViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Task>> mListOfTasks;
    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();

    public PreviewTasksViewModel(Repository repository){
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
}
