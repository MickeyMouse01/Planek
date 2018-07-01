package com.example.thomas.plan.addEditPlan;

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
import com.example.thomas.plan.data.Models.Plan;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class AddEditPlanActivity extends BaseActivity implements View.OnClickListener {

    private EditText mName;
    private Button save;
    private ImageView imageView;
    private AddEditPlanViewModel mViewModel;
    private Bitmap imageBitmap;
    private String clientId;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        clientId = intent.getStringExtra("ClientId");

        mViewModel = obtainViewModel(this);
        mName = findViewById(R.id.add_name);
        imageView = findViewById(R.id.add_image);
        save = findViewById(R.id.add_save_button);
        save.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_plan;
    }

    private static AddEditPlanViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddEditPlanViewModel viewModel = ViewModelProviders.of(activity, factory).get(AddEditPlanViewModel.class);
        return viewModel;
    }

    private void addOrEditPlan() {
        String name = mName.getText().toString();
        Plan newPlan = new Plan();
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        newPlan.setName(name);
        newPlan.setCreatedDate(dateTime);
        //newPlan.setImage(imageBitmap); todo upravit aby to ukladalo do storage databaze
        showDialog("Saving");
        if(clientId != null){
            mViewModel.savePlanToClient(newPlan,clientId);
        }
        mViewModel.savePlan(newPlan);
        hideDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;
            case R.id.add_save_button:
                addOrEditPlan();
                finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {

            Uri selectedImage = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(imageBitmap);

                mViewModel.uploadImage(imageBitmap, "obrazek");

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


