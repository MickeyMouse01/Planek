package com.example.thomas.plan.previewPlan;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.ArrayList;
import java.util.List;

public class PreviewPlanViewModel extends ViewModel {

    Repository repository;
    MutableLiveData<List<Task>> mListOfTasks = new MutableLiveData<>();

    public PreviewPlanViewModel(Repository repository){
        this.repository = repository;
        loadTasks();
    }

    public MutableLiveData<List<Task>> getListOfTasks() {
        return mListOfTasks;
    }

    private void loadTasks(){
        List<Task> blah = new ArrayList<>();
        blah.add(new Task("task"));
        blah.add(new Task("task1"));
        blah.add(new Task("task2"));
        blah.add(new Task("task3"));
        blah.add(new Task("task4"));
        mListOfTasks.setValue(blah);
    }
}
