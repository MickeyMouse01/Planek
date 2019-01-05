package com.example.thomas.plan.addEditTask;

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

import com.example.thomas.plan.activities.BaseActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditTaskActivity extends BaseActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    private AddEditTaskViewModel viewModel;
    private EditText mName,timeInput;
    private Button changePicture;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private String relatesPlan = null;
    private boolean timeForTaskIsSet = false;
    private String time;
    private String nameOfPicture;

    private static AddEditTaskViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(AddEditTaskViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        viewModel = obtainViewModel(this);
        mName = findViewById(R.id.add_task_name);
        imageView = findViewById(R.id.add_task_image);
        Button save = findViewById(R.id.add_save_task);
        changePicture = findViewById(R.id.change_picture_task);
        timeInput = findViewById(R.id.task_input_time);
        relatesPlan = intent.getStringExtra("PlanId");
        changePicture.setOnClickListener(this);
        save.setOnClickListener(this);
        imageView.setOnClickListener(this);
        timeInput.setOnClickListener(this);

        viewModel.imageIsUploaded().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                finishThisActivity();
            }
        });
    }


    private void finishThisActivity(){
        hideDialog();
        showSuccessToast("Aktivita byla úspěšně uložena");
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_task;
    }

    private void addOrEditTask() {
        String name = mName.getText().toString();
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        Task newTask = new Task(name);
        if(timeForTaskIsSet){
            newTask.setTime(time);
            newTask.setPartOfDay(Enums.PartOfDay.values()[getPartOfTheDay(time)]);
        }
        newTask.setCreatedDate(dateTime);


        showDialog("Ukládání");
        if (imageBitmap != null) {
            newTask.setImageSet(true);
            newTask.setNameOfImage(nameOfPicture);
            viewModel.uploadImage(imageBitmap, nameOfPicture);
        }


        if (relatesPlan != null) {
            newTask.setIdOfPlan(relatesPlan);
            viewModel.saveTaskToPlan(relatesPlan, newTask);
        }
        if (!newTask.isImageSet()) {
            finishThisActivity();
        }
    }

    private boolean requiredFieldsAreFilled() {
        boolean isFilled = true;
        if (mName.getText().toString().trim().isEmpty()) {
            mName.setError("Toto pole je povinné");
            isFilled = false;
        }
        return isFilled;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_picture_task:
                startActivityForSelectPicture();
                break;
            case R.id.add_task_image:
                startActivityForSelectPicture();
                break;
            case R.id.add_save_task:
                if (requiredFieldsAreFilled())
                    addOrEditTask();
                break;
            case R.id.task_input_time:
                showPickerForTime();
        }
    }

    private void showPickerForTime(){
        int mHour = 0;
        int mMinute = 0;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeForTaskIsSet = true;
                time = String.format("%02d:%02d", hourOfDay, minute);
                timeInput.setText(time);
            }
        },mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void startActivityForSelectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                Uri selectedImage = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    imageView.setImageBitmap(imageBitmap);
                    imageView.setVisibility(View.VISIBLE);
                    changePicture.setVisibility(View.INVISIBLE);
                    nameOfPicture = ActivityUtils.getNameOfFile(this, selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getPartOfTheDay(String time){
        DateFormat format = new SimpleDateFormat("H:mm");
        try {
            Date date = format.parse(time);
            Date noon = format.parse("12:00");
            if (date.before(noon)){
                return 0; //morning
            } else if(date.after(noon)){
                return 2; //afternoon
            } else if(date.getTime() == noon.getTime()){
                return 2;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 3;
    }
}
