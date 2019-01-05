package com.example.thomas.plan.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.plans.ListOfPlansAdapter;
import com.example.thomas.plan.ui.previewplans.PreviewPlansViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class PreviewAllPlansActivity extends BaseActivity {

    private PreviewPlansViewModel mViewModel;
    private ListOfPlansAdapter mPlansAdapter;
    private Settings settings;
    private String planId;

    public static PreviewPlansViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewPlansViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        settings = new Settings();
        settings.setDisabledDeleteButton(true);

        mViewModel = PreviewAllPlansActivity.obtainViewModel(this);
        String clientId = intent.getStringExtra("ClientId");
        mViewModel.getClientId().setValue(clientId);

        mViewModel.getPlans().observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable List<Plan> plans) {
                if (mPlansAdapter == null) {
                    setupListAdapter();
                }
                mPlansAdapter.replaceData(plans);
            }
        });

        mViewModel.selectPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                createCopyOfPlan(plan);
            }
        });

        mViewModel.onSavePlan().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                hideDialog();
                showSuccessToast(s);
                finishThisActivity(planId);
            }
        });
    }

    private void createCopyOfPlan(Plan plan){
        showDialog("Ukládání");
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        Plan newPlan = new Plan();
        planId = newPlan.getUniqueID();
        Map<String, Task> mapWithTasks = plan.getListOfRelatesTasks();

        for (Task task: mapWithTasks.values()) {
            task.setIdOfPlan(planId);
        }
        newPlan.setListOfRelatesTasks(plan.getListOfRelatesTasks());
        newPlan.setName(plan.getName() + " - kopie");
        newPlan.setCreatedDate(dateTime);
        mViewModel.savePlan(newPlan);
    }

    private void finishThisActivity(String planId){
        Intent output = new Intent();
        output.putExtra("planId", planId);
        setResult(RESULT_OK, output);
        finish();
    }

    private void setupListAdapter() {
        ListView listViewPlans = findViewById(R.id.preview_plans);
        ActionItemListener actionListener = new ActionItemListener<Plan>() {
            @Override
            public void onCheckedClick(Plan item) {

            }

            @Override
            public void onItemClick(Plan item) {
                mViewModel.selectPlan().setValue(item);
            }

            @Override
            public void onItemInfoClick(Plan item) {
            }

            @Override
            public void onItemDeleteClick(Plan item) {
            }
        };

        mPlansAdapter = new ListOfPlansAdapter(
                new ArrayList<Plan>(0), actionListener, settings
        );
        listViewPlans.setAdapter(mPlansAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.preview_plans_activity;
    }
}
