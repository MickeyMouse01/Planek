package com.example.thomas.plan.ui.previewplans;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class PreviewPlansViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Plan>> mListOfPlans;
    private MutableLiveData<String> clientId;
    private SingleLiveEvent<Plan> selectedPlan = new SingleLiveEvent<>();
    private SingleLiveEvent<String> onSavePlan = new SingleLiveEvent<>();

    public PreviewPlansViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getClientId() {
        if (clientId == null) {
            clientId = new MutableLiveData<>();
        }
        return clientId;
    }

    SingleLiveEvent<Plan> selectPlan() {
        return selectedPlan;
    }

    void savePlan(Plan newPlan){
        repository.savePlan(newPlan, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {
                onSavePlan.setValue(message);
            }
        });
    }

    public SingleLiveEvent<String> onSavePlan() {
        return onSavePlan;
    }

    public MutableLiveData<List<Plan>> getPlans() {
        if (mListOfPlans == null) {
            mListOfPlans = new MutableLiveData<>();
            loadPlans();
        }
        return mListOfPlans;
    }

    private void loadPlans() {
        repository.getPlans(new DataSource.LoadPlansCallback() {
            @Override
            public void onPlansLoaded(@NonNull List<Plan> plans) {
                mListOfPlans.setValue(plans);
            }
        });
    }

}
