package com.example.thomas.plan.viewPlanInfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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

    private ViewPlanInfoViewModel viewModel;
    private TextView nameOfPlan;
    private ListView listViewTasks;
    private ListOfTasksAdapter listOfTasksAdapter;
    private FloatingActionButton fab;
    private List<Task> mTasks = new ArrayList<>();
    private String viewPlanId;
    private ActionItemListener<Task> taskItemListener;
    private ImageView imageView;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        viewModel = obtainViewModel(this);

        setupListAdapter();

        viewPlanId = intent.getStringExtra("PlanId");
        viewModel.setViewedPlanId(viewPlanId);
        viewModel.setViewedPlan(viewPlanId);
        nameOfPlan = findViewById(R.id.view_name_of_plan);
        imageView = findViewById(R.id.preview_plan_image);
        fab = findViewById(R.id.fab);

        viewModel.getViewedPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                initialize(plan);
            }
        });

        viewModel.getTasks().observe(this, new Observer<List<Task>>() {
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
        listViewTasks = findViewById(R.id.view_plan_list_tasks);
        taskItemListener = new ActionItemListener<Task>() {
            @Override
            public void onCheckedClick(Task item) {

            }

            @Override
            public void onItemClick(Task item) {

            }

            @Override
            public void onItemInfoClick(Task item) {

            }

            @Override
            public void onItemDeleteClick(Task item) {
                mTasks.remove(item);
                listOfTasksAdapter.replaceData(mTasks);
                viewModel.deleteTaskFromPlan(viewPlanId,item);
            }
        };

        listOfTasksAdapter = new ListOfTasksAdapter(
                mTasks, taskItemListener
        );
        listViewTasks.setAdapter(listOfTasksAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_view_plan_info;
    }

    public static ViewPlanInfoViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        ViewPlanInfoViewModel viewModel = ViewModelProviders.of(activity, factory).get(ViewPlanInfoViewModel.class);
        return viewModel;
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
