package com.example.thomas.plan.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.adapters.ListOfDaysAdapter;
import com.example.thomas.plan.interfaces.ActionItemListener;
import com.example.thomas.plan.viewmodels.ListOfDaysViewModel;

import java.util.Arrays;
import java.util.List;

public class ListOfDaysActivity extends BaseActivity {

    ListView listViewDays;
    ListOfDaysViewModel mViewModel;
    String clientId;
    private ListOfDaysAdapter listOfDaysAdapter;
    private int positionOfWeek;
    private Button backToWeeksButton;
    private boolean listOfWeeksIsDisplayed = true;

    private static ListOfDaysViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(ListOfDaysViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        listViewDays = findViewById(R.id.list_of_days);
        backToWeeksButton = findViewById(R.id.button_back_to_list_of_weeks);
        mViewModel = obtainViewModel(this);

        clientId = intent.getStringExtra("ClientId");

        mViewModel.getListOfWeeks().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> weeks) {
                setupListAdapter(weeks);
            }
        });

        backToWeeksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfDaysAdapter.replaceData(mViewModel.getListOfWeeks().getValue());
                backToWeeksButton.setVisibility(View.GONE);
                listOfWeeksIsDisplayed = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (listOfWeeksIsDisplayed){
            super.onBackPressed();
        } else {
            listOfDaysAdapter.replaceData(mViewModel.getListOfWeeks().getValue());
            backToWeeksButton.setVisibility(View.GONE);
            listOfWeeksIsDisplayed = true;
        }
    }

    void previewPlanForClient(int positionOfDay, int positionOfWeek) {
        Intent intent = new Intent(this, PreviewPlanForClientActivity.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("positionOfDay", positionOfDay);
        intent.putExtra("positionOfWeek", positionOfWeek);
        startActivity(intent);
    }

    void setupListAdapter(final List<String> items) {
        ActionItemListener<String> actionItemListener = new ActionItemListener<String>() {
            @Override
            public void onCheckedClick(String item) {

            }

            @Override
            public void onItemClick(String item) {
                List<String> weeks = mViewModel.getListOfWeeks().getValue();
                if (weeks.contains(item)) {
                    positionOfWeek = weeks.indexOf(item);
                    listOfDaysAdapter.replaceData(mViewModel.getListOfDays().getValue());
                    backToWeeksButton.setVisibility(View.VISIBLE);
                    listOfWeeksIsDisplayed = false;
                } else {
                    List<String> days = mViewModel.getListOfDays().getValue();
                    selectPlanOrFood(days.indexOf(item), positionOfWeek);
                }
            }

            @Override
            public void onItemInfoClick(String item) {

            }

            @Override
            public void onItemDeleteClick(String item) {

            }
        };
        listOfDaysAdapter =
                new ListOfDaysAdapter(items, actionItemListener);
        listViewDays.setAdapter(listOfDaysAdapter);
    }

    private void previewFoodForClient(int positionOfDay, int positionOfWeek) {
        Intent intent = new Intent(this, PreviewFoodForClientActivity.class);
        intent.putExtra("ClientId", clientId);
        intent.putExtra("positionOfDay", positionOfDay);
        intent.putExtra("positionOfWeek", positionOfWeek);
        startActivity(intent);
    }

    private void selectPlanOrFood(final int positionOfDay, final int positionOfWeek) {
        CharSequence items[] = {"Plán", "Strava"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Vyberte");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        previewPlanForClient(positionOfDay, positionOfWeek);
                        break;
                    case 1:
                        previewFoodForClient(positionOfDay, positionOfWeek);
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_list_of_days;
    }
}
