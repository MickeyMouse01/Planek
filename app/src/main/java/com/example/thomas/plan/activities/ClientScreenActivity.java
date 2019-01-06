package com.example.thomas.plan.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.interfaces.ActionItemListener;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.viewmodels.ClientScreenViewModel;
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.adapters.ListOfTasksAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ClientScreenActivity extends BaseActivity {

    private TextView mTextMessage;
    private ClientScreenViewModel mViewModel;
    private String viewPlanId, viewClientId;
    private TextView tvLunch, tvDinner;
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
                case R.id.navigation_all:
                    mViewModel.getPartOfDay().setValue(null);
                    lvMorningActivities.setVisibility(View.VISIBLE);
                    lvLunch.setVisibility(View.VISIBLE);
                    lvAfternoonActivities.setVisibility(View.VISIBLE);
                    lvDinner.setVisibility(View.VISIBLE);
                    tvLunch.setVisibility(View.VISIBLE);
                    tvDinner.setVisibility(View.VISIBLE);
                    mTextMessage.setText(R.string.all_activities);
                    return true;
                case R.id.navigation_morning:
                    mViewModel.getPartOfDay().setValue(Enums.PartOfDay.MORNING);
                    lvMorningActivities.setVisibility(View.VISIBLE);
                    lvLunch.setVisibility(View.VISIBLE);
                    lvAfternoonActivities.setVisibility(View.GONE);
                    lvDinner.setVisibility(View.GONE);
                    tvLunch.setVisibility(View.VISIBLE);
                    tvDinner.setVisibility(View.GONE);
                    mTextMessage.setText(R.string.morning_activities);
                    return true;
                case R.id.navigation_afternoon:
                    mViewModel.getPartOfDay().setValue(Enums.PartOfDay.AFTERNOON);
                    lvMorningActivities.setVisibility(View.GONE);
                    lvLunch.setVisibility(View.GONE);
                    lvAfternoonActivities.setVisibility(View.VISIBLE);
                    lvDinner.setVisibility(View.VISIBLE);
                    tvLunch.setVisibility(View.GONE);
                    tvDinner.setVisibility(View.VISIBLE);
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
        viewClientId = intent.getStringExtra("ClientId");
        mViewModel.setViewedPlanId(viewPlanId);
        mViewModel.setViewedClientId(viewClientId);


        lvMorningActivities = findViewById(R.id.list_of_morning_tasks);
        lvLunch = findViewById(R.id.preview_task_lunch);
        lvAfternoonActivities = findViewById(R.id.list_of_afternoon_tasks);
        lvDinner = findViewById(R.id.preview_task_dinner);
        tvLunch = findViewById(R.id.tv_lunch);
        tvDinner = findViewById(R.id.tv_dinner);

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

        mViewModel.getOnDataAdd().observe(this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, String> collection) {
                if (collection != null){
                    int day = ActivityUtils.getActualDay();
                    String planId = collection.get(Enums.Day.values()[day].getNameOfDay());
                    if (planId != null){
                        viewPlanId = planId;
                        mViewModel.setViewedPlanId(viewPlanId);
                    }


                    if (!collection.values().contains(viewPlanId)){
                        if (morningActivitesAdapter != null){
                            morningActivitesAdapter.clearData();
                            lvMorningActivities.setBackground(null);
                        }
                        if (lunchAdapter != null){
                            lunchAdapter.clearData();
                            lvLunch.setBackground(null);
                        }
                        if (afternoonActivitesAdapter != null){
                            afternoonActivitesAdapter.clearData();
                            lvAfternoonActivities.setBackground(null);
                        }
                        if (dinnerAdapter != null){
                            dinnerAdapter.clearData();
                            lvDinner.setBackground(null);
                        }
                    } else {
                        mViewModel.getMorningActivites();
                        mViewModel.getLunch();
                        mViewModel.getDinner();
                        mViewModel.getAfternoonActivites();
                    }
                }
            }
        });


        mViewModel.getMorningActivites().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        lvMorningActivities.setVisibility(View.VISIBLE);
                        lvMorningActivities.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borderlines));
                        updateMorningTasks(tasks);
                    }
                } else {
                    lvMorningActivities.setBackground(null);
                    lvMorningActivities.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getAfternoonActivites().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        lvAfternoonActivities.setVisibility(View.VISIBLE);
                        lvAfternoonActivities.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borderlines));
                        updateAfternoonTasks(tasks);
                    }
                } else {
                    lvAfternoonActivities.setBackground(null);
                    lvAfternoonActivities.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getLunch().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        lvLunch.setVisibility(View.VISIBLE);
                        lvLunch.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borderlines));
                        updateLunch(tasks);
                    }
                } else {
                    lvLunch.setBackground(null);
                    lvLunch.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getDinner().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    if (!tasks.isEmpty()){
                        lvDinner.setVisibility(View.VISIBLE);
                        lvDinner.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.borderlines));
                        updateDinner(tasks);
                    }
                } else {
                    lvDinner.setBackground(null);
                    lvDinner.setVisibility(View.GONE);
                }
            }
        });

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
