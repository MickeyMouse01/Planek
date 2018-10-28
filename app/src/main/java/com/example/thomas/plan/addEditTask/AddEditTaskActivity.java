package com.example.thomas.plan.addEditTask;

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
import android.widget.Spinner;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEditTaskActivity extends BaseActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    private AddEditTaskViewModel viewModel;
    private Spinner partOfDaySpinner;
    private EditText mName;
    private Button save, changePicture;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private String relatesPlan = null;

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
        save = findViewById(R.id.add_save_task);
        changePicture = findViewById(R.id.change_picture_task);
        partOfDaySpinner = findViewById(R.id.spinner_part_of_day);
        partOfDaySpinner.setSelection(Enums.PartOfDay.UNDEFINED.getCode());
        relatesPlan = intent.getStringExtra("PlanId");
        changePicture.setOnClickListener(this);
        save.setOnClickListener(this);
        imageView.setOnClickListener(this);

        viewModel.imageIsUploaded().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                hideDialog();
                finish();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_task;
    }

    private void addOrEditTask() {
        String name = mName.getText().toString();
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        int partOfDay = partOfDaySpinner.getSelectedItemPosition();
        Task newTask = new Task(name);
        newTask.setCreatedDate(dateTime);
        newTask.setPartOfDay(Enums.PartOfDay.values()[partOfDay]);

        showDialog("Ukládání");
        if (imageBitmap != null) {
            newTask.setImageSet(true);
            viewModel.uploadImage(imageBitmap, newTask.getUniqueID());
        }


        if (relatesPlan != null) {
            newTask.setIdOfPlan(relatesPlan);
            viewModel.saveTaskToPlan(relatesPlan, newTask);
        }
        if (!newTask.isImageSet()) {
            hideDialog();
            finish();
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
                if (requiredFieldsAreFilled()) {
                    addOrEditTask();
                }
                break;
        }
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
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
