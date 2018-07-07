package com.example.thomas.plan.nurseProfileEdit;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.nurseProfile.NurseProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NurseProfileEditViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Nurse> editedNurse;
    private FirebaseUser firebaseUser;

    public NurseProfileEditViewModel(Repository repository){
        this.repository = repository;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public MutableLiveData<Nurse> getEditedNurse() {
        if(editedNurse == null){
            editedNurse = new MutableLiveData<>();
            loadNurse();
        }
        return editedNurse;
    }

    private void loadNurse(){
        repository.getNurse(firebaseUser.getUid(), new DataSource.LoadNurseCallback() {
            @Override
            public void onNurseLoaded(Nurse nurse) {
                editedNurse.setValue(nurse);
            }
        });
    }

    public void saveNurse(Nurse nurse){
        repository.saveNurse(nurse);
    }
}