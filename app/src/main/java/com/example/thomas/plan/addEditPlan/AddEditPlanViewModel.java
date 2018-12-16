package com.example.thomas.plan.addEditPlan;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.Common.Enums.Day;
import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Repository;

import java.io.ByteArrayOutputStream;

public class AddEditPlanViewModel extends ViewModel {
    MutableLiveData<Client> mutableClient = new MutableLiveData<>();
    private Repository repository;
    private SingleLiveEvent<Void> imageIsUploaded = new SingleLiveEvent<>();

    public AddEditPlanViewModel(Repository mRepository) {
        this.repository = mRepository;
    }

    void savePlan(Plan newPlan) {
        repository.savePlan(newPlan, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    void savePlanToClient(final Plan newPlan, String clientId, final int day) {
        repository.getClient(clientId, new DataSource.LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                client .getPlansForDate()
                        .put(Day.values()[day].getNameOfDay(), newPlan.getUniqueID());
                mutableClient.setValue(client);
            }
        });
    }

    void saveClientToRepository(Client client) {
        repository.saveClient(client, new DataSource.SavedDataCallback() {
            @Override
            public void onSavedData(@NonNull String message) {

            }
        });
    }

    SingleLiveEvent<Void> imageIsUploaded() {
        return imageIsUploaded;
    }

    void uploadImage(Bitmap bitmap, String name) {
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
