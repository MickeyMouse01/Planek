package com.example.thomas.plan.addEditTask;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEditTaskActivity extends BaseActivity implements View.OnClickListener {

    private AddEditTaskViewModel viewModel;
    private EditText mName;
    private Button save;
    private ImageView imageView;
    private Bitmap imageBitmap;
    private String relatesPlan = null;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        viewModel = obtainViewModel(this);
        mName = findViewById(R.id.add_task_name);
        imageView = findViewById(R.id.add_task_image);
        save = findViewById(R.id.add_save_task);
        relatesPlan = intent.getStringExtra("PlanId");
        save.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_task;
    }

    private static AddEditTaskViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(AddEditTaskViewModel.class);
    }
    private void addOrEditTask(){
        String name = mName.getText().toString();
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        Task newTask = new Task(name);
        newTask.setCreatedDate(dateTime);
        if (relatesPlan != null){
            newTask.setIdOfPlan(relatesPlan);
            viewModel.saveTaskToPlan(relatesPlan, newTask);
        }
        //newTask.setImage(imageBitmap); todo upravit aby to ukladalo do storage databaze

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_task_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
            case R.id.add_save_task:
                addOrEditTask();
                finish();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri selectedImage = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(imageBitmap);

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
