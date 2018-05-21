package com.example.thomas.plan.Clients;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by pospe on 10.02.2018.
 */

public class MainViewModel extends ViewModel {

    public final int VIEW_CLIENTS = 0;
    public final int VIEW_PLANS = 1;
    public ObservableList<Client> mListOfClients;
    public ObservableList<Plan> mListOfPlans;
    private final SingleLiveEvent<Void> mAddNewClient = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mAddNewPlan = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mViewClient = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mViewPlan = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mPreviewPlan = new SingleLiveEvent<>();
    private MutableLiveData<Integer> currentFragment;

    private Repository repository;

    public MainViewModel(Repository mRepository) {

        this.repository = mRepository;
    }

    public ObservableList<Client> getClients() {
        if (mListOfClients == null) {
            mListOfClients = new ObservableArrayList<>();
            loadClients();
        }
        return mListOfClients;
    }

    public MutableLiveData<Integer> getCurrentFragment() {
        if (currentFragment == null){
            currentFragment = new MutableLiveData<>();
            currentFragment.setValue(VIEW_CLIENTS);
        }
        return currentFragment;
    }

    public SingleLiveEvent<Void> addNewClient() {
        return mAddNewClient;
    }

    public SingleLiveEvent<Void> addNewPlan() {
        return mAddNewPlan;
    }

    public SingleLiveEvent<String> viewClient() {
        return mViewClient;
    }

    public SingleLiveEvent<String> viewPlan() {
        return mViewPlan;
    }

    public SingleLiveEvent<String> previewPlan() {
        return mPreviewPlan;
    }

    private void loadClients() {
        repository.getClients(new DataSource.LoadClientsCallback() {
            @Override
            public void onClientsLoaded(@NonNull List<Client> clients) {
                mListOfClients.clear();
                mListOfClients.addAll(clients);
            }
        });
    }

    public void removeClient(String clientId) {
        repository.deleteClient(clientId);
        loadClients();
    }

    public ObservableList<Plan> getPlans() {
        if (mListOfPlans == null) {
            mListOfPlans = new ObservableArrayList<>();
            loadPlans();
        }
        return mListOfPlans;
    }


    private void loadPlans() {
        repository.getPlans(new DataSource.LoadPlansCallback() {
            @Override
            public void onPlansLoaded(@NonNull List<Plan> plans) {
                mListOfPlans.clear();
                mListOfPlans.addAll(plans);
            }
        });
    }

    public void removePlan(String planId) {
        repository.deletePlan(planId);
        loadPlans();
    }

    public int getFragment() {
        return 0;
    }
}
