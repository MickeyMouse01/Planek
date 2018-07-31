package com.example.thomas.plan.nurseProfileEdit;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.nurseProfile.NurseProfileViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NurseProfileEditActivity extends BaseActivity implements View.OnClickListener {

    private NurseProfileEditViewModel mViewModel;
    private static final int PICK_IMAGE = 1;

    private Button save, uploadNewImage;
    private EditText email,nameAndSurname;
    private Spinner typeOfGroup, shift;
    private ImageView imageOfNurse;
    private Nurse editedNurse;
    private Bitmap imageBitmap;

    @Override
    protected int getContentView() {
        return R.layout.activity_nurse_profile_edit;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);

        email = findViewById(R.id.nurse_edit_mail);
        nameAndSurname = findViewById(R.id.nurse_edit_name);
        typeOfGroup = findViewById(R.id.nurse_edit_group);
        shift = findViewById(R.id.nurse_edit_shift);
        save = findViewById(R.id.nurse_edit_save_button);
        uploadNewImage = findViewById(R.id.nurse_edit_upload_image);
        imageOfNurse = findViewById(R.id.nurse_edit_image);

        mViewModel.getEditedNurse().observe(this, new Observer<Nurse>() {
            @Override
            public void onChanged(@Nullable Nurse nurse) {
                initializeNurse(nurse);
            }
        });
        save.setOnClickListener(this);
        uploadNewImage.setOnClickListener(this);
    }

    private static NurseProfileEditViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(NurseProfileEditViewModel.class);
    }

    private void initializeNurse(Nurse nurse){
        editedNurse = nurse;
        email.setText(nurse.getEmail());
        nameAndSurname.setText(nurse.getName() + " " + nurse.getSurname());
        typeOfGroup.setSelection(nurse.getTypeOfGroup().getCode());
        shift.setSelection(nurse.getShift().getCode());
        StorageReference ref = FirebaseStorage.getInstance().getReference().child(nurse.getUniqueID());

        GlideApp.with(this)
                .load(ref)
                .into(imageOfNurse);

    }

    private void editNurse(){
        editedNurse.setEmail(email.getText().toString());
        String editedNameAndSurname = nameAndSurname.getText().toString();
        String[] split = editedNameAndSurname.split("\\s+");
        String name = split[0];
        String surname = split[1];
        editedNurse.setName(name);
        editedNurse.setSurname(surname);
        editedNurse.setTypeOfGroup(Enums.TypeOfGroup.values()[typeOfGroup.getSelectedItemPosition()]);
        editedNurse.setShift(Enums.Shift.values()[shift.getSelectedItemPosition()]);
        mViewModel.saveNurse(editedNurse);
        finish();

    }

    private void uploadImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            Uri selectedImage = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageOfNurse.setImageBitmap(imageBitmap);
                mViewModel.uploadImage(imageBitmap, editedNurse.getUniqueID());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nurse_edit_save_button:{
                editNurse();
                break;
            }
            case R.id.nurse_edit_upload_image:{
                uploadImage();
                break;
            }
        }
    }
}
