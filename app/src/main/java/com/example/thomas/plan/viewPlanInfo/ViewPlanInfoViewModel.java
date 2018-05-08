package com.example.thomas.plan.viewPlanInfo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

public class ViewPlanInfoViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Plan> viewedPlan;

    public ViewPlanInfoViewModel(Repository mRepository) {
        this.viewedPlan = new MutableLiveData<>();
        this.repository = mRepository;
    }

    public MutableLiveData<Plan> getViewedPlan() {
        return viewedPlan;
    }

    public void setViewedPlan (String planId){
        repository.getPlan(planId,new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                viewedPlan.setValue(plan);
            }
        });
    }
}
