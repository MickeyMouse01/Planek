package com.example.thomas.plan.viewPlanInfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Clients.MainViewModel;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Plan;


public class ViewPlanInfoActivity extends BaseActivity {

    private ViewPlanInfoViewModel viewModel;
    private TextView nameOfPlan;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState,intent);

        viewModel = obtainViewModel(this);
        String viewPlanId = intent.getStringExtra("PlanId");
        viewModel.setViewedPlan(viewPlanId);
        nameOfPlan = findViewById(R.id.view_name_of_plan);
        viewModel.getViewedPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                initialize(plan);
            }
        });
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

    private void initialize(Plan plan){
        nameOfPlan.setText(plan.getName());
    }
}
