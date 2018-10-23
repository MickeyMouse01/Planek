package com.example.thomas.plan.previewTask;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class PreviewTaskViewModel extends ViewModel {

    private Repository repository;

    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<Enums.PartOfDay> partOfDay;
    private MutableLiveData<List<Task>> listOfTasks = new MutableLiveData<>();
    ;

    public PreviewTaskViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    public MutableLiveData<Enums.PartOfDay> getPartOfDay() {
        if (partOfDay == null) {
            partOfDay = new MutableLiveData<>();
        }
        getListOfTasks();
        return partOfDay;
    }

    public MutableLiveData<List<Task>> getListOfTasks() {
        loadTasks();
        return listOfTasks;
    }


    private void loadTasks() {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                listOfTasks.setValue(tasks);
            }
        }, viewedPlanId.getValue());
    }
}
