package com.example.thomas.plan.addEditPlan;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.addEditClient.AddEditClientViewModel;
import com.example.thomas.plan.addEditPlan.AddEditPlanViewModel;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;

public class AddEditPlanActivity extends BaseActivity implements View.OnClickListener {

    private EditText mName;
    private Button save;
    private ImageView imageView;
    private AddEditPlanViewModel mViewModel;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setContentView(R.layout.activity_add_edit_plan);

        mViewModel = obtainViewModel(this);
        mName = findViewById(R.id.add_name);
        imageView = findViewById(R.id.add_image);
        save = findViewById(R.id.add_save_button);
        save.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_plan;
    }
    private static AddEditPlanViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddEditPlanViewModel viewModel = ViewModelProviders.of(activity, factory).get(AddEditPlanViewModel.class);
        return viewModel;
    }
    private void addOrEditPlan(){
        String name = mName.getText().toString();
        Plan newPlan = new Plan();
        newPlan.setName(name);
        showDialog("Saving");
        mViewModel.savePlan(newPlan);
        hideDialog();
    }

    @Override
    public void onClick(View v) {
        addOrEditPlan();
        finish();
    }
    //image picker
    /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     startActivityForResult(pickPhoto , 1);*/
}
