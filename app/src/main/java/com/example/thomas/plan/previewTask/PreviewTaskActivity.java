package com.example.thomas.plan.previewTask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.selectedTask.SelectedTaskActivity;
import com.example.thomas.plan.tasks.ListOfTasksAdapter;

import java.util.ArrayList;
import java.util.List;

public class PreviewTaskActivity extends BaseActivity {

    private TextView mTextMessage;
    private PreviewTaskViewModel mViewModel;
    private String viewPlanId;
    private ListView lvListOfTasks;
    private ListOfTasksAdapter listOfTasksAdapter;
    private ActionItemListener<Task> taskItemListener;
    private List<Task> mTasks = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewModel.getPartOfDay().setValue(null);
                    mViewModel.getListOfTasks().setValue(mTasks);
                    mTextMessage.setText(R.string.all_activities);
                    return true;
                case R.id.navigation_dashboard:
                    mViewModel.getPartOfDay().setValue(Enums.PartOfDay.MORNING);
                    mViewModel.getListOfTasks().setValue(mTasks);
                    mTextMessage.setText(R.string.morning_activities);
                    return true;
                case R.id.navigation_afternoon:
                    mViewModel.getPartOfDay().setValue(Enums.PartOfDay.AFTERNOON);
                    mViewModel.getListOfTasks().setValue(mTasks);
                    mTextMessage.setText(R.string.afternoon_activities);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);
        viewPlanId = intent.getStringExtra("PlanId");
        mViewModel.setViewedPlanId(viewPlanId);

        setupListAdapter();

        mTextMessage = findViewById(R.id.message);
        mTextMessage.setText(R.string.all_activities);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewModel.getPartOfDay().observe(this, new Observer<Enums.PartOfDay>() {
            @Override
            public void onChanged(@Nullable Enums.PartOfDay partOfDay) {
            }
        });

        mViewModel.getListOfTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                updateData(tasks);
            }
        });

    }

    private List<Task> getSpecificTasks(List<Task> tasks, Enums.PartOfDay partOfDay) {
        List<Task> taskList = new ArrayList<>();

        for (Task x : tasks) {
            if(x != null){
                if (x.getIdOfPlan().equals(viewPlanId)
                        && x.getPartOfDay().equals(partOfDay)) {
                    taskList.add(x);
                }
            }
        }
        return taskList;
    }

    private static PreviewTaskViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewTaskViewModel.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_preview_task;
    }

    private void setupListAdapter() {
        lvListOfTasks = findViewById(R.id.list_of_tasks);
        taskItemListener = new ActionItemListener<Task>() {
            @Override
            public void onCheckedClick(Task item) {
                mViewModel.saveTask(item);
            }

            @Override
            public void onItemClick(Task item) {
                previewSelectedTask(item.getIdOfPlan(), mTasks.indexOf(item));

            }

            @Override
            public void onItemInfoClick(Task item) {

            }

            @Override
            public void onItemDeleteClick(Task item) {
                mViewModel.deleteTaskFromPlan(item);
            }
        };
    }

    private void previewSelectedTask(String planId, int position){
        Intent intent = new Intent(this, SelectedTaskActivity.class);
        intent.putExtra("PlanId", planId);
        intent.putExtra("positionOfTask",position);
        startActivity(intent);
    }

    private void initializeAdapter(){
        if (!mTasks.isEmpty()) {
            listOfTasksAdapter = new ListOfTasksAdapter(
                    mTasks, taskItemListener
            );
            lvListOfTasks.setAdapter(listOfTasksAdapter);
        }
    }


    private void updateData(List<Task> tasks){
        if (mViewModel.getPartOfDay().getValue() == null) {
           mTasks = tasks;
        } else {
            mTasks = getSpecificTasks(tasks, mViewModel.getPartOfDay().getValue());
        }

        if (listOfTasksAdapter == null){
            initializeAdapter();
        }
        listOfTasksAdapter.replaceData(mTasks);
    }
}
