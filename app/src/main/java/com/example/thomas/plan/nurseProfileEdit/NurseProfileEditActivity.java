package com.example.thomas.plan.nurseProfileEdit;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.nurseProfile.NurseProfileViewModel;

public class NurseProfileEditActivity extends BaseActivity implements View.OnClickListener {

    private NurseProfileEditViewModel mViewModel;

    private Button save;
    private EditText email,nameAndSurname;
    private Spinner typeOfGroup, shift;
    private Nurse editedNurse;

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

        mViewModel.getEditedNurse().observe(this, new Observer<Nurse>() {
            @Override
            public void onChanged(@Nullable Nurse nurse) {
                initializeNurse(nurse);
            }
        });
        save.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        editNurse();
    }
}
