package com.example.thomas.plan.nurseProfile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class NurseProfileViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Nurse> viewedNurse;
    private FirebaseUser user;

    public NurseProfileViewModel(Repository repository) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.repository = repository;
    }

    MutableLiveData<Nurse> getViewedNurse() {
        if(viewedNurse == null) {
            viewedNurse = new MutableLiveData<>();
            loadViewedNurse();

        }
        return viewedNurse;
    }

    private void loadViewedNurse(){
        repository.getNurse(user.getUid(), new DataSource.LoadNurseCallback() {
            @Override
            public void onNurseLoaded(Nurse nurse) {
                viewedNurse.setValue(nurse);
            }
        });
    }
}
