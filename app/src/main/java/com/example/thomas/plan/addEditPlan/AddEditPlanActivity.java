package com.example.thomas.plan.addEditPlan;

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

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEditPlanActivity extends BaseActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    private EditText mName;
    private Button save, changePicture;
    private ImageView imageView;
    private AddEditPlanViewModel mViewModel;
    private Bitmap imageBitmap;
    private String clientId;

    private static AddEditPlanViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddEditPlanViewModel viewModel = ViewModelProviders.of(activity, factory).get(AddEditPlanViewModel.class);
        return viewModel;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        clientId = intent.getStringExtra("ClientId");
        mViewModel = obtainViewModel(this);
        mName = findViewById(R.id.add_name);
        imageView = findViewById(R.id.add_image);
        save = findViewById(R.id.add_save_button);
        changePicture = findViewById(R.id.change_picture_plan);
        changePicture.setOnClickListener(this);
        save.setOnClickListener(this);
        imageView.setOnClickListener(this);

        mViewModel.imageIsUploaded().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                hideDialog();
                finish();
            }
        });

        mViewModel.mutableClient.observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                mViewModel.saveClientToRepository(client);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_plan;
    }

    private void addOrEditPlan() {
        String name = mName.getText().toString();
        Plan newPlan = new Plan();
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        newPlan.setName(name);
        newPlan.setCreatedDate(dateTime);

        showDialog("Ukládání");

        if (imageBitmap != null) {
            newPlan.setImageSet(true);
            mViewModel.uploadImage(imageBitmap, newPlan.getUniqueID());
        }

        if (clientId != null) {
            mViewModel.savePlanToClient(newPlan, clientId);
        }
        mViewModel.savePlan(newPlan);

        if (!newPlan.isImageSet()) {
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
            case R.id.change_picture_plan:
                startActivityForSelectPicture();
                break;
            case R.id.add_image:
                startActivityForSelectPicture();
                break;
            case R.id.add_save_button:
                if (requiredFieldsAreFilled()) {
                    addOrEditPlan();
                }
        }
    }

    private void startActivityForSelectPicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
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


