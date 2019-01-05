package com.example.thomas.plan.addEditTask;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.thomas.plan.SingleLiveEvent;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Repository;

import java.io.ByteArrayOutputStream;

public class AddEditTaskViewModel extends ViewModel {

    private Repository repository;
    private SingleLiveEvent<Void> imageIsUploaded = new SingleLiveEvent<>();

    public AddEditTaskViewModel(Repository repository){
        this.repository = repository;
    }

    public void saveTaskToPlan(String planId, final Task task){
        repository.getPlan(planId, new DataSource.LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                plan.addToListOfRelatesTasks(task);
                repository.savePlan(plan, new DataSource.SavedDataCallback() {
                    @Override
                    public void onSavedData(@NonNull String message) {

                    }
                });

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
