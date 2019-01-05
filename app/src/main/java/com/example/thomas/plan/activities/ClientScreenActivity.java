package com.example.thomas.plan.activities;

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
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.clientScreen.ClientScreenViewModel;
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.tasks.ListOfTasksAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientScreenActivity extends BaseActivity {

    private TextView mTextMessage;
    private ClientScreenViewModel mViewModel;
    private String viewPlanId;
    private ListView lvMorningActivities, lvAfternoonActivities,lvDinner,lvLunch;
    private ListOfTasksAdapter morningActivitesAdapter;
    private ListOfTasksAdapter afternoonActivitesAdapter;
    private ListOfTasksAdapter lunchAdapter;
    private ListOfTasksAdapter dinnerAdapter;
    private ActionItemListener<Task> taskItemListener;
    private List<Task> mTasks = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewModel.getPartOfDay().setValue(null);
                    //mViewModel.getListOfTasks().setValue(mTasks);
                    mTextMessage.setText(R.string.all_activities);
                    return true;
                case R.id.navigation_dashboard:
                    mViewModel.getPartOfDay().setValue(Enums.PartOfDay.MORNING);
                    //mViewModel.getListOfTasks().setValue(mTasks);
                    mTextMessage.setText(R.string.morning_activities);
                    return true;
                case R.id.navigation_afternoon:
                    mViewModel.getPartOfDay().setValue(Enums.PartOfDay.AFTERNOON);
                    //mViewModel.getListOfTasks().setValue(mTasks);
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

        lvMorningActivities = findViewById(R.id.list_of_morning_tasks);
        lvLunch = findViewById(R.id.preview_task_lunch);
        lvAfternoonActivities = findViewById(R.id.list_of_afternoon_tasks);
        lvDinner = findViewById(R.id.preview_task_dinner);
        setupListAdapter();

        mTextMessage = findViewById(R.id.message);
        mTextMessage.setText(R.string.all_activities);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewModel.getPartOfDay().observe(this, new Observer<Enums.PartOfDay>() {
            @Override
            public void onChanged(@Nullable Enums.PartOfDay partOfDay) {
            }
        });


        mViewModel.getMorningActivites().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        updateMorningTasks(tasks);
                    }
                } else {
                    noDataDisplayed();
                }
            }
        });

        mViewModel.getAfternoonActivites().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        updateAfternoonTasks(tasks);
                    }
                } else {
                    noDataDisplayed();
                }
            }
        });

        mViewModel.getLunch().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        updateLunch(tasks);
                    }
                } else {
                    noDataDisplayed();
                }
            }
        });

        mViewModel.getDinner().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        updateDinner(tasks);
                    }
                } else {
                    noDataDisplayed();
                }
            }
        });

    }

    private void noDataDisplayed(){

    }

    private static ClientScreenViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(ClientScreenViewModel.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_preview_task;
    }

    private void setupListAdapter() {
        taskItemListener = new ActionItemListener<Task>() {
            @Override
            public void onCheckedClick(Task item) {
                mViewModel.saveTask(item);
            }

            @Override
            public void onItemClick(Task item) {
                Collections.sort(mTasks);
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
        Intent intent = new Intent(this, PreviewSelectedTaskActivity.class);
        intent.putExtra("PlanId", planId);
        intent.putExtra("positionOfTask",position);
        startActivity(intent);
    }

    private void initializeMorningActivitiesAdapter(List<Task> tasks){
        Settings settings = new Settings();
        settings.setDisabledDeleteButton(true);
        morningActivitesAdapter = new ListOfTasksAdapter(
                tasks,
                taskItemListener,
                settings
        );
        lvMorningActivities.setAdapter(morningActivitesAdapter);
    }

    private void updateMorningTasks(List<Task> tasks){
        if (morningActivitesAdapter == null){
            initializeMorningActivitiesAdapter(tasks);
        }
        morningActivitesAdapter.replaceData(tasks);
    }

    private void initializeLunchAdapter(List<Task> tasks){
        Settings settings = new Settings();
        settings.setDisabledDeleteButton(true);
        lunchAdapter = new ListOfTasksAdapter(
                tasks,
                taskItemListener,
                settings
        );
        lvLunch.setAdapter(lunchAdapter);
    }

    private void updateLunch(List<Task> tasks){
        if (lunchAdapter == null){
            initializeLunchAdapter(tasks);
        }
        lunchAdapter.replaceData(tasks);
    }

    private void initializeAfternoonActivitiesAdapter(List<Task> tasks){
        Settings settings = new Settings();
        settings.setDisabledDeleteButton(true);
        afternoonActivitesAdapter = new ListOfTasksAdapter(
                tasks,
                taskItemListener,
                settings
        );
        lvAfternoonActivities.setAdapter(afternoonActivitesAdapter);
    }

    private void updateAfternoonTasks(List<Task> tasks){
        if (afternoonActivitesAdapter == null){
            initializeAfternoonActivitiesAdapter(tasks);
        }
        afternoonActivitesAdapter.replaceData(tasks);

    }

    private void initializeDinnerAdapter(List<Task> tasks){
        Settings settings = new Settings();
        settings.setDisabledDeleteButton(true);
        dinnerAdapter = new ListOfTasksAdapter(
                tasks,
                taskItemListener,
                settings
        );
        lvDinner.setAdapter(dinnerAdapter);
    }

    private void updateDinner(List<Task> tasks){
        if (dinnerAdapter == null){
            initializeDinnerAdapter(tasks);
        }
        dinnerAdapter.replaceData(tasks);
    }
}
