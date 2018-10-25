package com.example.thomas.plan.addEditPlan;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.io.ByteArrayOutputStream;

public class AddEditPlanViewModel extends ViewModel {
    public ObservableField<Plan> newPlan = new ObservableField<>();
    private Repository repository;
    private SingleLiveEvent<Void> imageIsUploaded = new SingleLiveEvent<>();

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

    public SingleLiveEvent<Void> imageIsUploaded() {
        return imageIsUploaded;
    }

    public void uploadImage(Bitmap bitmap, String name){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        repository.uploadImage(name, data, new DataSource.UploadImageCallback() {
            @Override
            public void onImageUploaded() {
                imageIsUploaded.call();
            }
        });
    }
}
