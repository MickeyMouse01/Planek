package com.example.thomas.plan.planForClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;

public class PlanForClientActivity extends BaseActivity {

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_plan_for_client;
    }
}
