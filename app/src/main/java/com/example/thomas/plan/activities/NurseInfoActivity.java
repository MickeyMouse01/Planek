package com.example.thomas.plan.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.plan.glide.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.viewmodels.NurseInfoViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NurseInfoActivity extends BaseActivity {

   private NurseInfoViewModel mViewModel;

   private ImageView imageOfNurse;
   private TextView txtEmail,txtName,txtGroup,txtShift;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState,intent);
        mViewModel = obtainViewModel(this);

        imageOfNurse = findViewById(R.id.nurse_image);
        txtEmail = findViewById(R.id.nurse_mail);
        txtName = findViewById(R.id.nurse_name);
        txtGroup = findViewById(R.id.nurse_group);

        mViewModel.getViewedNurse().observe(this, new Observer<Nurse>() {
            @Override
            public void onChanged(@Nullable Nurse nurse) {
                initializeNurse(nurse);

            }
        });
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_nurse_profile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.getViewedNurse().observe(this, new Observer<Nurse>() {
            @Override
            public void onChanged(@Nullable Nurse nurse) {
                initializeNurse(nurse);
            }
        });
    }

    private static NurseInfoViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(NurseInfoViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NurseInfoEditActivity.class);
        startActivityForResult(intent,3);
        return super.onOptionsItemSelected(item);
    }

    private void initializeNurse(Nurse nurse){
        txtEmail.setText(nurse.getEmail());
        txtName.setText(nurse.getName() + " " + nurse.getSurname());
        txtGroup.setText(nurse.getTypeOfGroup().getNameOfGroup());

        if (nurse.getNameOfImage() != null){
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(nurse.getNameOfImage());

            GlideApp.with(this)
                    .load(ref)
                    .into(imageOfNurse);
        }
    }
}
