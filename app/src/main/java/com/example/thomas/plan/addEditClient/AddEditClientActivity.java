package com.example.thomas.plan.addEditClient;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
//todo seradit podle jmena
public class AddEditClientActivity extends BaseActivity implements View.OnClickListener {

    private EditText mFirstName;
    private EditText mSurname;
    private Spinner sTypeOfGroup;
    private Button save;
    private Client newClient;
    private AddEditClientViewModel mViewModel;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mFirstName = findViewById(R.id.add_firstName);
        mSurname = findViewById(R.id.add_surname);
        sTypeOfGroup = findViewById(R.id.type_of_group);
        save = findViewById(R.id.add_save_button);
        mViewModel = obtainViewModel(this);
        String idOfClient = intent.getStringExtra("idClient");

        save.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_client;
    }

    private static AddEditClientViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddEditClientViewModel viewModel = ViewModelProviders.of(activity, factory).get(AddEditClientViewModel.class);
        return viewModel;
    }

    private void addOrEditClient() {
            String firstName = mFirstName.getText().toString();
            String surname = mSurname.getText().toString();
            int typeOfGroup = sTypeOfGroup.getSelectedItemPosition();

            Client newClient = new Client(firstName, surname, Enums.TypeOfGroup.values()[typeOfGroup]);
            mViewModel.saveClient(newClient);
    }


    @Override
    public void onClick(View v) {
        addOrEditClient();
        finish();
    }
}
