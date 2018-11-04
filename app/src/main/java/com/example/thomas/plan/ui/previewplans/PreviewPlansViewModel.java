package com.example.thomas.plan.ui.previewplans;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.util.List;

public class PreviewPlansViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Plan>> mListOfPlans;
    private MutableLiveData<String> clientId;
    private SingleLiveEvent<String> selectedPlanId = new SingleLiveEvent<>();;

    public PreviewPlansViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getClientId() {
        if (clientId == null) {
            clientId = new MutableLiveData<>();
        }
        return clientId;
    }

    public SingleLiveEvent<String> selectPlan() {
        return selectedPlanId;
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