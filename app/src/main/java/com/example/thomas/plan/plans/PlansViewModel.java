package com.example.thomas.plan.plans;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.util.List;

/**
 * Created by Tomas on 22-Apr-18.
 */

public class PlansViewModel extends ViewModel {

    public ObservableList<Plan> mListOfPlans;
    private final SingleLiveEvent<Void> mAddNewPlan= new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mViewPlan = new SingleLiveEvent<>();
    private Repository repository;

    public PlansViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public ObservableList<Plan> getPlans() {
        if (mListOfPlans == null) {
            mListOfPlans = new ObservableArrayList<>();
            loadPlans();
        }
        return mListOfPlans;
    }
    public SingleLiveEvent<Void> addNewPlan() {
        return mAddNewPlan;
    }

    public SingleLiveEvent<String> viewPlan() {
        return mViewPlan;
    }

    private void loadPlans(){
        /*repository.getPlans(new DataSource.LoadPlansCallback() {
            @Override
            public void onPlansLoaded(@NonNull List<Plan> plans) {
                mListOfPlans.clear();
                mListOfPlans.addAll(plans);
            }
        });*/
    }
    public void removePlan(String planId){
        repository.deleteClient(planId);
        loadPlans();
    }
}
