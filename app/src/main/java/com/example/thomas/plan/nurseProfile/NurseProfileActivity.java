package com.example.thomas.plan.nurseProfile;

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

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.nurseProfileEdit.NurseProfileEditActivity;

public class NurseProfileActivity extends BaseActivity {

   private NurseProfileViewModel mViewModel;

   private ImageView imageOfNurse;
   private TextView txtEmail,txtName,txtGroup,txtShift;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState,intent);
        mViewModel = obtainViewModel(this);

        imageOfNurse = findViewById(R.id.nurse_edit_image);
        txtEmail = findViewById(R.id.nurse_edit_mail);
        txtName = findViewById(R.id.nurse_edit_name);
        txtGroup = findViewById(R.id.nurse_edit_group);
        txtShift = findViewById(R.id.nurse_edit_shift);

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

    private static NurseProfileViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        NurseProfileViewModel viewModel = ViewModelProviders.of(activity, factory).get(NurseProfileViewModel.class);
        return viewModel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, NurseProfileEditActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void initializeNurse(Nurse nurse){
        txtEmail.setText(nurse.getEmail());
        txtName.setText(nurse.getName());
        //txtGroup.setText(nurse.getTypeOfGroup().getCode());
    }
}
