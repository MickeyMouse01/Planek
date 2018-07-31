package com.example.thomas.plan.Clients;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private final SingleLiveEvent<String> mViewInfoClient = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mPreviewClient = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mViewPlan = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mPreviewPlan = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mNurseProfile = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> mShowMessage = new SingleLiveEvent<>();
    private MutableLiveData<Integer> currentFragment;
    private MutableLiveData<Nurse> loggedNurse;
    private FirebaseUser firebaseUser;

    private Repository repository;

    public MainViewModel(Repository mRepository) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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
        return mViewInfoClient;
    }
    public SingleLiveEvent<String> previewClient() {
        return mPreviewClient;
    }
    public SingleLiveEvent<String> viewPlan() {
        return mViewPlan;
    }
    public SingleLiveEvent<String> previewPlan() {
        return mPreviewPlan;
    }
    public SingleLiveEvent<Void> previewNurseProfile() {
        return mNurseProfile;
    }
    public SingleLiveEvent<String> showMessage() {
        return mShowMessage;
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

    public MutableLiveData<Nurse> getLoggedNurse() {
        if(loggedNurse == null) {
            loggedNurse = new MutableLiveData<>();
            loadLoggedNurse();
        }
        return loggedNurse;
    }

    private void loadLoggedNurse(){
        if (firebaseUser != null) {
            repository.getNurse(firebaseUser.getUid(), new DataSource.LoadNurseCallback() {
                @Override
                public void onNurseLoaded(Nurse nurse) {
                    loggedNurse.setValue(nurse);
                }
            });
        } else {
            mShowMessage.setValue("Přístup do databáze odmítnut");
        }

    }
}
