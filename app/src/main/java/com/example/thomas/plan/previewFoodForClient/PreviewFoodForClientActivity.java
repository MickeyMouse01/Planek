package com.example.thomas.plan.previewFoodForClient;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PreviewFoodForClientActivity extends BaseActivity {

    private static final int LUNCH_IMAGE = 0;
    private static final int DINNER_IMAGE = 1;
    private Bitmap lunchBitmap;
    private Bitmap dinnerBitmap;
    private EditText editTextLunchTime;
    private EditText editTextDinnerTime;
    private String nameOfLunch;
    private String nameOfDinner;
    private PreviewFoodForClientViewModel mViewModel;
    private ImageView ivLunch, ivDinner;
    private Button saveButton;
    private String clientId;
    private int day;
    private Task taskLunch, taskDinner;


    public static PreviewFoodForClientViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewFoodForClientViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);

        ivLunch = findViewById(R.id.imageview_add_lunch);
        ivDinner = findViewById(R.id.imageview_add_dinner);
        editTextLunchTime = findViewById(R.id.food_lunch_time);
        editTextDinnerTime = findViewById(R.id.food_dinner_time);
        saveButton = findViewById(R.id.food_save_button);

        clientId = intent.getStringExtra("ClientId");
        day = intent.getIntExtra("position", 0);

        mViewModel.getClientId().setValue(clientId);

        mViewModel.getViewedClient().observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                initializeClient(client);
            }
        });

        ivLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPictureActivity(LUNCH_IMAGE);
            }
        });

        ivDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPictureActivity(DINNER_IMAGE);
            }
        });

        editTextLunchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerForTime(editTextLunchTime);
            }
        });

        editTextDinnerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerForTime(editTextDinnerTime);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requiredFieldsAreFilled()) {
                    showDialog("Ukládání");
                    addOrEditData();
                    hideDialog();
                    finish();
                }
            }
        });
    }

    private void showPickerForTime(final EditText timeInput) {
        int mHour = 0;
        int mMinute = 0;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%02d:%02d", hourOfDay, minute);
                timeInput.setText(time);
            }
        }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private boolean requiredFieldsAreFilled() {
        boolean isFilled = true;
        if (editTextLunchTime.getText().toString().trim().isEmpty()) {
            if(lunchBitmap != null){
                editTextLunchTime.setError("Toto pole je povinné");
                isFilled = false;
            }
        } else if (editTextDinnerTime.getText().toString().trim().isEmpty()) {
            if(dinnerBitmap != null){
                editTextDinnerTime.setError("Toto pole je povinné");
                isFilled = false;
            }
        }
        return isFilled;
    }

    private void addOrEditData() {
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        Task newTask;
        if (taskLunch == null) {
            if (lunchBitmap != null) {
                newTask = new Task();
                newTask.setPartOfDay(Enums.PartOfDay.LUNCH);
                newTask.setImageSet(true);
                newTask.setNameOfImage(nameOfLunch);
                newTask.setIdOfPlan(mViewModel.getPlanId());
                newTask.setCreatedDate(dateTime);
                newTask.setName("Oběd");
                newTask.setTime(editTextLunchTime.getText().toString());
                mViewModel.uploadImage(lunchBitmap, nameOfLunch);
                mViewModel.saveTaskToPlan(newTask);
            }
        } else {
            if (lunchBitmap != null){
                taskLunch.setNameOfImage(nameOfLunch);
                mViewModel.uploadImage(lunchBitmap, nameOfLunch);
            }
            if (!taskLunch.getTime().equals(editTextLunchTime)) {
                taskLunch.setTime(editTextLunchTime.getText().toString());
            }
            mViewModel.updateTask(taskLunch);
        }
        if (taskDinner == null) {
            if (dinnerBitmap != null) {
                newTask = new Task();
                newTask.setPartOfDay(Enums.PartOfDay.DINNER);
                newTask.setImageSet(true);
                newTask.setNameOfImage(nameOfDinner);
                newTask.setIdOfPlan(mViewModel.getPlanId());
                newTask.setCreatedDate(dateTime);
                newTask.setName("Večeře");
                newTask.setTime(editTextDinnerTime.getText().toString());
                mViewModel.uploadImage(dinnerBitmap, nameOfDinner);
                mViewModel.saveTaskToPlan(newTask);
            }
        } else {
            if (dinnerBitmap != null){
                taskDinner.setNameOfImage(nameOfDinner);
                mViewModel.uploadImage(dinnerBitmap, nameOfDinner);
            }
            if (!taskDinner.getTime().equals(editTextDinnerTime)) {
                taskDinner.setTime(editTextDinnerTime.getText().toString());
            }
            mViewModel.updateTask(taskDinner);
        }
    }

    private void selectPictureActivity(int typeOfImage) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Vyberte obrázek"), typeOfImage);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri selectedImage = data.getData();
            try {
                if (requestCode == LUNCH_IMAGE) {
                    lunchBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ivLunch.setImageBitmap(lunchBitmap);
                    ivLunch.setScaleType(ImageView.ScaleType.FIT_XY);
                    nameOfLunch = ActivityUtils.getNameOfFile(this, selectedImage);
                } else if (requestCode == DINNER_IMAGE) {
                    dinnerBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ivDinner.setImageBitmap(dinnerBitmap);
                    ivDinner.setScaleType(ImageView.ScaleType.FIT_XY);
                    nameOfDinner = ActivityUtils.getNameOfFile(this, selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeClient(Client client) {
        String planId = client.getPlansForDate().get(Enums.Day.values()[day].getNameOfDay());
        if (planId != null) { //plan existuje
            showDialog("Načítání");
            mViewModel.getSelectedPlan(planId).observe(this, new Observer<Plan>() {
                @Override
                public void onChanged(@Nullable Plan plan) {
                    initializePlan();

                }
            });
        } else {
            //zalozit novy plan
        }

    }

    private void loadImageForLunch(Task task) {
        taskLunch = task;
        ivLunch.setScaleType(ImageView.ScaleType.FIT_XY);
        StorageReference ref = FirebaseStorage.getInstance()
                .getReference().child(task.getNameOfImage());
        GlideApp.with(this)
                .load(ref)
                .into(ivLunch);
        editTextLunchTime.setText(task.getTime());
        hideDialog();
    }

    private void loadImageForDinner(Task task) {
        taskDinner = task;
        ivDinner.setScaleType(ImageView.ScaleType.FIT_XY);
        StorageReference ref = FirebaseStorage.getInstance()
                .getReference().child(task.getNameOfImage());
        GlideApp.with(this)
                .load(ref)
                .into(ivDinner);
        editTextDinnerTime.setText(task.getTime());
        hideDialog();
    }

    private void initializePlan() {
        hideDialog();
        mViewModel.getTaskLunch().observe(this, new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                showDialog("Načítání");
                loadImageForLunch(task);
            }
        });
        mViewModel.getTaskDinner().observe(this, new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                showDialog("Načítání");
                loadImageForDinner(task);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_preview_food_for_client;
    }
}
