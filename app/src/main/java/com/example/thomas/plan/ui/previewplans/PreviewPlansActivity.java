package com.example.thomas.plan.ui.previewplans;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.plans.ListOfPlansAdapter;

import java.util.ArrayList;
import java.util.List;

public class PreviewPlansActivity extends BaseActivity {

    private PreviewPlansViewModel mViewModel;
    private ListOfPlansAdapter mPlansAdapter;
    private ActionItemListener actionListener;
    private ListView listViewPlans;
    private String clientId;
    private Settings settings;


    public static PreviewPlansViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewPlansViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        settings = new Settings();
        settings.setDisableDeleteButton(true);

        mViewModel = PreviewPlansActivity.obtainViewModel(this);
        clientId = intent.getStringExtra("ClientId");
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

        mViewModel.selectPlan().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String planId) {
                Intent output = new Intent();
                output.putExtra("planId", planId);
                setResult(RESULT_OK, output);
                finish();
            }
        });

    }

    private void setupListAdapter() {
        listViewPlans = findViewById(R.id.preview_plans);
        actionListener = new ActionItemListener<Plan>() {
            @Override
            public void onCheckedClick(Plan item) {

            }

            @Override
            public void onItemClick(Plan item) {
                mViewModel.selectPlan().setValue(item.getUniqueID());
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
