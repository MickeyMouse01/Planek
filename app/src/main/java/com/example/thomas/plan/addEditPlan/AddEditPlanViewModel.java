package com.example.thomas.plan.addEditPlan;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
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

    public void savePlanToClient(final Plan newPlan, String clientId){
        repository.getClient(clientId, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                client.setPlanId(newPlan.getUniqueID());
                repository.saveClient(client);
            }
        });
    }
}
