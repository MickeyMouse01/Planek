package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.addEditPlan.AddEditPlanActivity;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.plans.ListOfPlansAdapter;

public class PreviewClientActivity extends BaseActivity implements View.OnClickListener {

    private TextView nameOfClient,surnameOfClient;
    private ImageButton addNewPlanBut;
    private ListView listViewOnePlan, listViewTasks;
    private String clientId;
    private PreviewClientViewModel mViewModel;
    private SimplePlanAdapter planAdapter;
    private String selectedPlanId;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);
        clientId = intent.getStringExtra("ClientId");
        mViewModel.setViewedPlanId(clientId);

        nameOfClient = findViewById(R.id.plan_for_client_name);
        surnameOfClient = findViewById(R.id.plan_for_client_surname);
        addNewPlanBut = findViewById(R.id.plan_for_client_button_for_add);
        listViewTasks = findViewById(R.id.plan_for_client_lv_tasks);

        mViewModel.getViewedClient().observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                initializeClient(client);
            }
        });
        //todo zmenit ze static na dynamic
        mViewModel.getSelectedPlan("f3aeab35-2b2a-419e-925a-8810af79ba5c").observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                setupListAdapter(plan);
            }
        });
        addNewPlanBut.setOnClickListener(this);

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

    private void setupListAdapter(Plan plan){
        listViewOnePlan =  findViewById(R.id.plan_for_client_lv_plan);
        planAdapter = new SimplePlanAdapter(
                plan,
                mViewModel
        );
        listViewOnePlan.setAdapter(planAdapter);
    }

    private void initializeClient(Client client){
        nameOfClient.setText(client.getFirstName());
        surnameOfClient.setText(client.getSurname());
        selectedPlanId = client.getPlanId();

        if(client.getPlanId() == null) {
            addNewPlanBut.setVisibility(View.VISIBLE);
            listViewOnePlan.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PreviewClientActivity.this);
        builderSingle.setCancelable(true);
        builderSingle.setNeutralButton("Zrusit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setNegativeButton("Pridat vytvoreny", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Vybral jsem z katulani nabidky", "nabidka");
            }
        });

        builderSingle.setPositiveButton("Vytvorit novy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PreviewClientActivity.this, AddEditPlanActivity.class);
                intent.putExtra("ClientId",clientId);
                startActivityForResult(intent, 1);
           }
        });
        builderSingle.show();
    }
}
