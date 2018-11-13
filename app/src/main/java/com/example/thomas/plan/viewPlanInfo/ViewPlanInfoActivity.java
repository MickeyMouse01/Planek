package com.example.thomas.plan.viewPlanInfo;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.addEditTask.AddEditTaskActivity;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.tasks.ListOfTasksAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class ViewPlanInfoActivity extends BaseActivity {

    private ViewPlanInfoViewModel mViewModel;
    private TextView nameOfPlan;
    private ListOfTasksAdapter listOfTasksAdapter;
    private List<Task> mTasks = new ArrayList<>();
    private String viewPlanId;
    private ImageView imageView;
    private AlertDialog.Builder confirmDialog;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);

        setupListAdapter();
        viewPlanId = intent.getStringExtra("PlanId");
        mViewModel.setViewedPlanId(viewPlanId);
        mViewModel.setViewedPlan(viewPlanId);
        nameOfPlan = findViewById(R.id.view_name_of_plan);
        imageView = findViewById(R.id.preview_plan_image);
        FloatingActionButton fab = findViewById(R.id.fab);

        mViewModel.getViewedPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                if(plan !=null){
                    initialize(plan);
                }

            }
        });

        mViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if (tasks != null) {
                    listOfTasksAdapter.replaceData(getSpecificTasks(tasks));
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runActivity();
            }
        });
        confirmDialog = createConfirmDialog();
    }

    private List<Task> getSpecificTasks(List<Task> tasks) {
        List<Task> taskList = new ArrayList<>();

        for (Task x : tasks) {
            if(x != null){
                if (x.getIdOfPlan().equals(viewPlanId)) {
                    taskList.add(x);
                }
            }
        }
        mTasks = taskList;
        return taskList;
    }

    private void runActivity() {
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        intent.putExtra("PlanId", viewPlanId);
        startActivity(intent);
    }

    private void setupListAdapter() {
        ListView listViewTasks = findViewById(R.id.view_plan_list_tasks);
        ActionItemListener<Task> taskItemListener = new ActionItemListener<Task>() {
            @Override
            public void onCheckedClick(Task item) {
                mTasks.get(mTasks.indexOf(item)).setPassed(item.isPassed());
                mViewModel.updateTask(viewPlanId, item);
            }

            @Override
            public void onItemClick(Task item) {

            }

            @Override
            public void onItemInfoClick(Task item) {

            }

            @Override
            public void onItemDeleteClick(Task item) {
                deleteItemWithConfirmation(item);
            }
        };

        listOfTasksAdapter = new ListOfTasksAdapter(
                mTasks, taskItemListener
        );
        listViewTasks.setAdapter(listOfTasksAdapter);
    }

    private void deleteItemWithConfirmation(final Task task){
        confirmDialog.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTasks.remove(task);
                listOfTasksAdapter.replaceData(mTasks);
                mViewModel.deleteTaskFromPlan(viewPlanId, task);
            }
        }).show();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_view_plan_info;
    }

    public static ViewPlanInfoViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(ViewPlanInfoViewModel.class);
    }

    private void initialize(Plan plan) {
        nameOfPlan.setText(plan.getName());
        if(plan.isImageSet()) {
            StorageReference ref = FirebaseStorage.getInstance()
                    .getReference().child(plan.getUniqueID());
            GlideApp.with(this)
                    .load(ref)
                    .into(imageView);
        }
    }
}
