package com.example.thomas.plan.planForClient;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.addEditPlan.AddEditPlanActivity;
import com.example.thomas.plan.addEditTask.AddEditTaskActivity;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.plans.ListOfPlansAdapter;
import com.example.thomas.plan.tasks.ListOfTasksAdapter;

import java.util.List;

public class PreviewClientActivity extends BaseActivity implements View.OnClickListener {

    private TextView nameOfClient, surnameOfClient;
    private ImageButton addNewPlanBut;
    private ListView listViewOnePlan, listViewTasks;
    private String clientId;
    private PreviewClientViewModel mViewModel;
    private ListOfPlansAdapter planAdapter;
    private ListOfTasksAdapter taskAdapter;
    private ActionItemListener<Plan> planActionItemListener;
    private ActionItemListener<Task> taskActionItemListener;
    private Intent addEditTaskIntent;
    private FloatingActionButton fab;

    public static PreviewClientViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewClientViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);


        addEditTaskIntent = new Intent(this,AddEditTaskActivity.class);
        mViewModel = obtainViewModel(this);
        clientId = intent.getStringExtra("ClientId");
        mViewModel.setViewedClientId(clientId);

        listViewOnePlan = findViewById(R.id.plan_for_client_lv_plan);
        nameOfClient = findViewById(R.id.plan_for_client_name);
        surnameOfClient = findViewById(R.id.plan_for_client_surname);
        addNewPlanBut = findViewById(R.id.plan_for_client_button_for_add);
        listViewTasks = findViewById(R.id.plan_for_client_lv_tasks);
        fab = findViewById(R.id.preview_client_fab);

        mViewModel.getViewedClient().observe(this, new Observer<Client>() {
            @Override
            public void onChanged(@Nullable Client client) {
                initializeClient(client);
            }
        });
        addNewPlanBut.setOnClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditTaskIntent.putExtra("PlanId", mViewModel.getViewedPlanId());
                startActivity(addEditTaskIntent);
                mViewModel.getViewedPlanId();
            }
        });
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_plan_for_client;
    }

    private void setupListAdapter(final Plan plan) {
        planActionItemListener = new ActionItemListener<Plan>() {
            @Override
            public void onItemClick(Plan item) {

            }

            @Override
            public void onItemInfoClick(Plan item) {

            }

            @Override
            public void onItemDeleteClick(Plan item) {
                mViewModel.deleteSelectedPlan();
                mViewModel.setViewedPlanId(null);
                planAdapter.clearData();
                taskAdapter.clearData();
            }
        };
            planAdapter = new ListOfPlansAdapter(plan, planActionItemListener);
            listViewOnePlan.setAdapter(planAdapter);
    }

    private void setupTaskAdapter(final List<Task> task){
        taskActionItemListener = new ActionItemListener<Task>() {
            @Override
            public void onItemClick(Task item) {

            }

            @Override
            public void onItemInfoClick(Task item) {

            }

            @Override
            public void onItemDeleteClick(Task item) {

            }
        };
        taskAdapter = new ListOfTasksAdapter(task, taskActionItemListener);
        listViewTasks.setAdapter(taskAdapter);
    }

    private void initializeClient(Client client) {

        nameOfClient.setText(client.getFirstName());
        surnameOfClient.setText(client.getSurname());
        mViewModel.setViewedPlanId(client.getPlanId());

        if (client.getPlanId() == null) {
            fab.setVisibility(View.GONE);
            addNewPlanBut.setVisibility(View.VISIBLE);
            listViewOnePlan.setVisibility(View.GONE);
        } else {

            mViewModel.getSelectedPlan().observe(this, new Observer<Plan>() {
                @Override
                public void onChanged(@Nullable Plan plan) {
                    setupListAdapter(plan);
                }
            });

            mViewModel.getTasks().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    setupTaskAdapter(tasks);
                }
            });
            fab.setVisibility(View.VISIBLE);
            addNewPlanBut.setVisibility(View.GONE);
            listViewOnePlan.setVisibility(View.VISIBLE);
        }
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
}
