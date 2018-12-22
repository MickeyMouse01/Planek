package com.example.thomas.plan.viewPlanInfoEdit;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.tasks.ListOfTasksAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PlanInfoEditActivity extends BaseActivity {

    private PlanInfoEditViewModel mViewModel;
    private String viewPlanId;
    private Button saveButton;
    private TextView nameOfPlan;
    private ImageView imageView;
    private ListOfTasksAdapter listOfTasksAdapter;
    private List<Task> mTasks = new ArrayList<>();
    private AlertDialog.Builder confirmDialog;

    public static PlanInfoEditViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PlanInfoEditViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);

        setupListAdapter();
        viewPlanId = intent.getStringExtra("PlanId");
        nameOfPlan = findViewById(R.id.edit_name_of_plan);
        imageView = findViewById(R.id.edit_plan_image);
        saveButton = findViewById(R.id.save_edit_button);
        mViewModel.setViewedPlanId(viewPlanId);
        mViewModel.setViewedPlan(viewPlanId);

        mViewModel.getViewedPlan().observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(@Nullable Plan plan) {
                if(plan !=null){
                    initialize(plan);
                }
            }
        });

        mViewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if (tasks != null) {
                    listOfTasksAdapter.replaceData(getSpecificTasks(tasks));
                }
            }
        });

        mViewModel.getOnPlanSaved().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                hideDialog();
                showSuccessToast(s);
                finish();
            }
        });
        confirmDialog = createConfirmDialog();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditedPlan();
            }
        });
    }

    private void saveEditedPlan(){
        showDialog("Ukládání");
       Plan editedPlan = mViewModel.getViewedPlan().getValue();
       String editedName = nameOfPlan.getText().toString();
       editedPlan.setName(editedName);
       mViewModel.updatePlan(editedPlan);
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

    private void setupListAdapter() {
        ListView listViewTasks = findViewById(R.id.edit_plan_list_tasks);
        ActionItemListener<Task> taskItemListener = new ActionItemListener<Task>() {
            @Override
            public void onCheckedClick(Task item) {
                mTasks.get(mTasks.indexOf(item)).setPassed(item.isPassed());
                mViewModel.updateTask(viewPlanId, item);
            }

            @Override
            public void onItemClick(Task item) {

            }

            @Override
            public void onItemInfoClick(Task item) {

            }

            @Override
            public void onItemDeleteClick(Task item) {
                deleteItemWithConfirmation(item);
            }
        };
        Settings settings = new Settings();
        settings.setDisabledDeleteButton(false);
        listOfTasksAdapter = new ListOfTasksAdapter(
                mTasks,
                taskItemListener,
                settings
        );
        listViewTasks.setAdapter(listOfTasksAdapter);
    }

    private void deleteItemWithConfirmation(final Task task){
        confirmDialog.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTasks.remove(task);
                listOfTasksAdapter.replaceData(mTasks);
                mViewModel.deleteTaskFromPlan(viewPlanId, task);
            }
        }).show();
    }

    private void initialize(Plan plan) {
        nameOfPlan.setText(plan.getName());
        if(plan.isImageSet()) {
            StorageReference ref = FirebaseStorage.getInstance()
                    .getReference().child(plan.getNameOfImage());
            GlideApp.with(this)
                    .load(ref)
                    .into(imageView);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_plan_info_edit;
    }
}
