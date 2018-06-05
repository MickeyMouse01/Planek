package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;

public class PreviewClientActivity extends BaseActivity {

    private TextView nameOfClient,surnameOfClient;
    private ImageButton addNewPlanBut;
    private ListView listViewOnePlan, listViewTasks;
    private String clientId;
    private PreviewClientViewModel mViewModel;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);
        clientId = intent.getStringExtra("ClientId");
        mViewModel.setViewedPlanId(clientId);

        nameOfClient = findViewById(R.id.plan_for_client_name);
        surnameOfClient = findViewById(R.id.plan_for_client_surname);
        addNewPlanBut = findViewById(R.id.plan_for_client_button_for_add);
        listViewOnePlan = findViewById(R.id.plan_for_client_lv_plan);
        listViewTasks = findViewById(R.id.plan_for_client_lv_tasks);

        mViewModel.getViewedClient().observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                initializeClient(client);
            }
        });



    }

    @Override
    protected int getContentView() {
        return R.layout.activity_plan_for_client;
    }

    public static PreviewClientViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewClientViewModel.class);
    }

    private void initializeClient(Client client){

    }

}
