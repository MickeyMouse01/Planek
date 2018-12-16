package com.example.thomas.plan.listOfDays;

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
import com.example.thomas.plan.planForClient.PreviewClientActivity;

import java.util.List;

public class ListOfDaysActivity extends BaseActivity {

    ListView listViewDays;
    ListOfDaysViewModel mViewModel;
    String clientId;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        listViewDays = findViewById(R.id.list_of_days);
        mViewModel = obtainViewModel(this);

        clientId = intent.getStringExtra("ClientId");

        mViewModel.getListOfDays().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> days) {
                setupListAdapter(days);
            }
        });
    }

    void previewPlanForClient(int position){
        Intent intent = new Intent(this, PreviewClientActivity.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    void setupListAdapter(final List<String> items){
        ActionItemListener<String> actionItemListener = new ActionItemListener<String>() {
            @Override
            public void onCheckedClick(String item) {

            }

            @Override
            public void onItemClick(String item) {
                previewPlanForClient(items.indexOf(item));
            }

            @Override
            public void onItemInfoClick(String item) {

            }

            @Override
            public void onItemDeleteClick(String item) {

            }
        };
        ListOfDaysAdapter daysAdapter =
                new ListOfDaysAdapter(items,actionItemListener );
        listViewDays.setAdapter(daysAdapter);
    }

    private static ListOfDaysViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(ListOfDaysViewModel.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_list_of_days;
    }
}
