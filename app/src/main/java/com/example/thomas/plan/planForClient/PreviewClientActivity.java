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

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.addEditPlan.AddEditPlanActivity;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.plans.ListOfPlansAdapter;

import java.util.ArrayList;
import java.util.List;

public class PreviewClientActivity extends BaseActivity implements View.OnClickListener {

    private TextView nameOfClient, surnameOfClient;
    private ImageButton addNewPlanBut;
    private ListView listViewOnePlan, listViewTasks;
    private String clientId;
    private PreviewClientViewModel mViewModel;
    private ListOfPlansAdapter planAdapter;
    private String selectedPlanId;
    private Client viewedClient;
    private AlertDialog.Builder dialogBuilder;
    private ActionItemListener<Plan> actionItemListener;

    public static PreviewClientViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewClientViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);
        clientId = intent.getStringExtra("ClientId");
        mViewModel.setViewedClientId(clientId);

        listViewOnePlan = findViewById(R.id.plan_for_client_lv_plan);
        nameOfClient = findViewById(R.id.plan_for_client_name);
        surnameOfClient = findViewById(R.id.plan_for_client_surname);
        addNewPlanBut = findViewById(R.id.plan_for_client_button_for_add);
        listViewTasks = findViewById(R.id.plan_for_client_lv_tasks);

        mViewModel.getViewedClient(true).observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                initializeClient(client);
            }
        });

        mViewModel.getListOfAllPlans().observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable List<Plan> plans) {
                createDialogBuilder(plans);
            }
        });
        addNewPlanBut.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_plan_for_client;
    }

    private void setupListAdapter(Plan plan) {
        List<Plan> listOfPlan = new ArrayList<>();
        listOfPlan.add(plan);
        actionItemListener = new ActionItemListener<Plan>() {
            @Override
            public void onItemClick(Plan item) {
                Log.d("Plan","PlanCliecked");
            }

            @Override
            public void onItemInfoClick(Plan item) {

            }

            @Override
            public void onItemDeleteClick(Plan item) {

            }
        };
        planAdapter = new ListOfPlansAdapter(listOfPlan,actionItemListener);
        /*planAdapter = new SimplePlanAdapter(
                plan,
                mViewModel
        );*/
        listViewOnePlan.setAdapter(planAdapter);
    }

    private void initializeClient(Client client) {

        nameOfClient.setText(client.getFirstName());
        surnameOfClient.setText(client.getSurname());

        mViewModel.setViewedPlanId(client.getPlanId());

        mViewModel.getSelectedPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                setupListAdapter(plan);
            }
        });

        if (client.getPlanId() == null) {
            addNewPlanBut.setVisibility(View.VISIBLE);
            listViewOnePlan.setVisibility(View.GONE);
        } else {
            addNewPlanBut.setVisibility(View.GONE);
            listViewOnePlan.setVisibility(View.VISIBLE);
        }
    }

    //todo refaktor getSelectedPlan
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.getSelectedPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                setupListAdapter(plan);
            }
        });
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
                mViewModel.getListOfAllPlans();
            }

        });

        builderSingle.setPositiveButton("Vytvorit novy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PreviewClientActivity.this, AddEditPlanActivity.class);
                intent.putExtra("ClientId", clientId);
                startActivityForResult(intent, 1);
            }
        });
        builderSingle.show();
    }

    private void createDialogBuilder(List<Plan> plans) {
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choose an animal");

        List<String> names = new ArrayList<>();
        for (Plan x : plans) {
            names.add(x.getName());
        }
        final CharSequence[] Names = names.toArray(new String[names.size()]);
        dialogBuilder.setSingleChoiceItems(Names, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog blah = dialogBuilder.create();
                blah.show();
            }
        });

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
    }
}
