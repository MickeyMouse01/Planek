package com.example.thomas.plan.viewClientInfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;

public class ViewClientActivity extends BaseActivity {

    private String viewClientId;
    private ViewClientViewModel viewModel;
    private TextView firstName, surname, typeOfGroup, nurseId;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        viewModel = obtainViewModel(this);
        viewClientId = intent.getStringExtra("ClientId");
        viewModel.setViewedClient(viewClientId);

        firstName = findViewById(R.id.view_firstname);
        surname = findViewById(R.id.view_surname);
        typeOfGroup = findViewById(R.id.view_type_of_group);
        nurseId = findViewById(R.id.view_nurseid);

        viewModel.getViewedClient().observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                initialize(client);
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_view_client;
    }

    private static ViewClientViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        ViewClientViewModel viewModel = ViewModelProviders.of(activity, factory).get(ViewClientViewModel.class);
        return viewModel;
    }

    private void initialize(Client client){
        firstName.setText(client.getFirstName());
        surname.setText(client.getSurname());
        typeOfGroup.setText(client.getTypeOfGroup());
        nurseId.setText(client.getNurseID());
    }
}
