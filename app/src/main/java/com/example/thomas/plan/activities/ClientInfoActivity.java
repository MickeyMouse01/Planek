package com.example.thomas.plan.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.viewmodels.ClientInfoViewModel;
import com.example.thomas.plan.data.Models.Client;

public class ClientInfoActivity extends BaseActivity {

    private String viewClientId;
    private TextView firstName, surname, typeOfGroup, username, createdDate;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        ClientInfoViewModel viewModel = obtainViewModel(this);
        viewClientId = intent.getStringExtra("ClientId");
        viewModel.setViewedClient(viewClientId);

        firstName = findViewById(R.id.view_firstname);
        surname = findViewById(R.id.view_surname);
        typeOfGroup = findViewById(R.id.view_type_of_group);
        username = findViewById(R.id.view_username);
        createdDate = findViewById(R.id.view_created_date);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AddEditClientActivity.class);
        intent.putExtra("idClient", viewClientId);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private static ClientInfoViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(ClientInfoViewModel.class);
    }

    private void initialize(Client client){
        if(client == null){
            finish();
            Toast.makeText(this, "Klient byl odstranen", Toast.LENGTH_SHORT).show();
        } else {
            firstName.setText(client.getFirstName());
            surname.setText(client.getSurname());
            typeOfGroup.setText(client.getTypeOfGroup().getNameOfGroup());
            username.setText(client.getUsername());
            createdDate.setText(client.getCreatedDate());
        }
    }
}
