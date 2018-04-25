package com.example.thomas.plan.addEditPlan;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

public class AddEditPlanViewModel extends ViewModel {
    public ObservableField<Plan> newPlan = new ObservableField<>();
    private Repository repository;

    public AddEditPlanViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    public void savePlan(Plan newPlan) {
        repository.savePlan(newPlan);
    }
}
