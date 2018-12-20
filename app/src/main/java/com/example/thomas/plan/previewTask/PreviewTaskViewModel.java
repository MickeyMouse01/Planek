package com.example.thomas.plan.previewTask;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.util.Collections;
import java.util.List;

public class PreviewTaskViewModel extends ViewModel {

    private Repository repository;

    private MutableLiveData<String> viewedPlanId = new MutableLiveData<>();
    private MutableLiveData<Enums.PartOfDay> partOfDay;
    private MutableLiveData<List<Task>> listOfTasks;
    private MutableLiveData<List<Task>> morningActivites;
    private MutableLiveData<List<Task>> lunch;
    private MutableLiveData<List<Task>> afternoonActivites;
    private MutableLiveData<List<Task>> dinner;

    public PreviewTaskViewModel(Repository repository) {
        this.repository = repository;
    }

    void setViewedPlanId(String viewedPlanId) {
        this.viewedPlanId.setValue(viewedPlanId);
    }

    MutableLiveData<List<Task>> getMorningActivites() {
        if (morningActivites == null) {
            morningActivites = new MutableLiveData<>();
        }
        loadMorningActivites();
        return morningActivites;
    }

    private void loadMorningActivites() {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if(tasks != null) {
                    Collections.sort(tasks);
                }
                morningActivites.setValue(tasks);
            }
        }, viewedPlanId.getValue(), Enums.PartOfDay.MORNING);
    }

    public MutableLiveData<List<Task>> getLunch() {
        if (lunch == null) {
            lunch = new MutableLiveData<>();
        }
        loadLunch();
        return lunch;
    }
    private void loadLunch() {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if(tasks != null) {
                    Collections.sort(tasks);
                }
                lunch.setValue(tasks);
            }
        }, viewedPlanId.getValue(), Enums.PartOfDay.LUNCH);
    }

    public MutableLiveData<List<Task>> getAfternoonActivites() {
        if (afternoonActivites == null) {
            afternoonActivites = new MutableLiveData<>();
        }
        loaAfternoonActivities();
        return afternoonActivites;
    }

    private void loaAfternoonActivities() {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if(tasks != null) {
                    Collections.sort(tasks);
                }
                afternoonActivites.setValue(tasks);
            }
        }, viewedPlanId.getValue(), Enums.PartOfDay.AFTERNOON);
    }

    public MutableLiveData<List<Task>> getDinner() {
        if (dinner == null) {
            dinner = new MutableLiveData<>();
        }
        loadDinner();
        return dinner;
    }

    private void loadDinner() {
        repository.getSpecificTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if(tasks != null) {
                    Collections.sort(tasks);
                }
                dinner.setValue(tasks);
            }
        }, viewedPlanId.getValue(), Enums.PartOfDay.DINNER);
    }

    MutableLiveData<Enums.PartOfDay> getPartOfDay() {
        if (partOfDay == null) {
            partOfDay = new MutableLiveData<>();
        }
        return partOfDay;
    }

    /*MutableLiveData<List<Task>> getListOfTasks() {
        if (listOfTasks == null) {
            listOfTasks = new MutableLiveData<>();
        }
        loadTasks();
        return listOfTasks;
    }

    private void loadTasks() {
        repository.getTasksForPlan(new DataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                if(tasks != null) {
                    Collections.sort(tasks);
                }
                listOfTasks.setValue(tasks);
            }
        }, viewedPlanId.getValue());
    }*/

    void saveTask(Task task){
        repository.saveTask(task, viewedPlanId.getValue(), new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    void  deleteTaskFromPlan(Task task){
        repository.deleteTaskFromPlan(viewedPlanId.getValue(),task.getUniqueID());
    }
}
