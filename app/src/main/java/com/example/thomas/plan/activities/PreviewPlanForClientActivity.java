package com.example.thomas.plan.activities;

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

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.interfaces.ActionItemListener;
import com.example.thomas.plan.Common.Enums.Day;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.adapters.ListOfPlansAdapter;
import com.example.thomas.plan.viewmodels.PreviewPlanForClientViewModel;
import com.example.thomas.plan.adapters.ListOfTasksAdapter;

import java.util.List;

public class PreviewPlanForClientActivity extends BaseActivity implements View.OnClickListener {

    ImageButton addNewPlanBut;
    ListView listViewOnePlan, listViewTasks;
    String clientId;
    String nameOfDay, nameOfWeek;
    int day;
    int week;
    PreviewPlanForClientViewModel mViewModel;
    ListOfPlansAdapter planAdapter;
    ListOfTasksAdapter taskAdapter;
    Intent addEditTaskIntent;
    FloatingActionButton fab;

    public static PreviewPlanForClientViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewPlanForClientViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);


        addEditTaskIntent = new Intent(this, AddEditTaskActivity.class);
        mViewModel = obtainViewModel(this);
        clientId = intent.getStringExtra("ClientId");
        week = intent.getIntExtra("positionOfWeek", 0);
        day = intent.getIntExtra("positionOfDay", 0);
        nameOfDay = Day.values()[day].toString();
        nameOfWeek = Enums.Week.values()[week].toString();
        mViewModel.setNameOfDay(nameOfDay);
        mViewModel.setNameOfWeek(nameOfDay);
        mViewModel.setViewedClientId(clientId);

        listViewOnePlan = findViewById(R.id.plan_for_client_lv_plan);
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
                mViewModel.getViewedClient();
            }
        });

        mViewModel.showToastWithMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                hideDialog();
                showSuccessToast(s);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_plan_for_client;
    }

    private void setupPlanAdapter(final Plan plan) {
        ActionItemListener<Plan> planActionItemListener = new ActionItemListener<Plan>() {
            @Override
            public void onCheckedClick(Plan item) {

            }

            @Override
            public void onItemClick(Plan item) {
                viewPlan(item.getUniqueID());
            }

            @Override
            public void onItemInfoClick(Plan item) {

            }

            @Override
            public void onItemDeleteClick(Plan item) {
                mViewModel.deletePlanFromClient(nameOfDay, nameOfWeek);
                planAdapter.clearData();
                if (taskAdapter != null) {
                    taskAdapter.clearData();
                }
            }
        };
        planAdapter = new ListOfPlansAdapter(plan, planActionItemListener);
        listViewOnePlan.setAdapter(planAdapter);
    }

    private void setupTaskAdapter(final List<Task> tasks) {
        ActionItemListener<Task> taskActionItemListener = new ActionItemListener<Task>() {
            @Override
            public void onCheckedClick(Task item) {
                tasks.get(tasks.indexOf(item)).setPassed(item.isPassed());
                mViewModel.updateTask(item);
            }

            @Override
            public void onItemClick(Task item) {

            }

            @Override
            public void onItemInfoClick(Task item) {

            }

            @Override
            public void onItemDeleteClick(Task item) {
                tasks.remove(item);
                taskAdapter.replaceData(tasks);
                mViewModel.deleteTaskFromPlan(mViewModel.getViewedPlanId(), item);
            }
        };
        Settings settings = new Settings();
        settings.setDeleteInvisibleAfterPassed(true);
        taskAdapter = new ListOfTasksAdapter(tasks, taskActionItemListener, settings);
        listViewTasks.setAdapter(taskAdapter);
    }

    private void initializeClient(Client client) {
        String viewedPlanId = client.getDating().get(nameOfWeek).get(nameOfDay);
        mViewModel.setViewedPlanId(viewedPlanId);
        if (viewedPlanId.isEmpty() ) {
            fab.setVisibility(View.GONE);
            addNewPlanBut.setVisibility(View.VISIBLE);
            listViewOnePlan.setVisibility(View.GONE);
        } else {
            mViewModel.getSelectedPlan().observe(this, new Observer<Plan>() {
                @Override
                public void onChanged(@Nullable Plan plan) {
                    setupPlanAdapter(plan);
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
        hideDialog();
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PreviewPlanForClientActivity.this);
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
                Intent intent = new Intent(PreviewPlanForClientActivity.this, PreviewAllPlansActivity.class);
                intent.putExtra("ClientId", clientId);
                startActivityForResult(intent, 0);
            }

        });

        builderSingle.setPositiveButton("Vytvorit novy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PreviewPlanForClientActivity.this, AddEditPlanActivity.class);
                intent.putExtra("ClientId", clientId);
                intent.putExtra("positionOfDay", nameOfDay);
                intent.putExtra("positionOfWeek", nameOfWeek);
                startActivityForResult(intent, 1);
            }
        });
        builderSingle.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
            showDialog("Ukládání");
            String selectedPlanId = data.getStringExtra("planId");
            Client client = mViewModel.getViewedClient().getValue();
            client.getDating().get(nameOfWeek).put(nameOfDay,selectedPlanId);
            mViewModel.saveUpdatedClient(client);
        } else if (requestCode == 1 && data != null){
            showDialog("Ukládání");
            String selectedPlanId = data.getStringExtra("planId");
            Client client = mViewModel.getViewedClient().getValue();
            client.getDating().get(nameOfWeek).put(nameOfDay,selectedPlanId);
            mViewModel.saveUpdatedClient(client);
        }
    }

    private void viewPlan(String planId) {
        Intent intent = new Intent(this, PlanInfoActivity.class);
        intent.putExtra("PlanId", planId);
        startActivity(intent);
    }
}
