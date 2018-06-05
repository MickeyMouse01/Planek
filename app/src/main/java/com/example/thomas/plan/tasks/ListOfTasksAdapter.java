package com.example.thomas.plan.tasks;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.viewPlanInfo.ViewPlanInfoViewModel;

import java.util.List;

public class ListOfTasksAdapter extends BaseAdapter {

    private ViewPlanInfoViewModel viewModel;
    private List<Task> tasks;
    private View viewTask;
    private TextView textViewName;
    private ImageView infoImage, deleteImage;
    private CheckBox checkBox;
    private RelativeLayout relativeLayout;


    public ListOfTasksAdapter(List<Task> tasks,
                              ViewPlanInfoViewModel viewModel) {
        this.viewModel = viewModel;
        this.tasks = tasks;
    }

    public void replaceData(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        viewTask = inflater.inflate(R.layout.task_item, null);
        textViewName = viewTask.findViewById(R.id.view_plan_task_name);
        deleteImage = viewTask.findViewById(R.id.view_plan_task_delete_image);
        relativeLayout = viewTask.findViewById(R.id.view_plan_relative_layout);
        checkBox = viewTask.findViewById(R.id.view_plan_ispassed);
        textViewName.setText(tasks.get(position).getName());

        if(tasks.get(position).isPassed()){
            relativeLayout.setBackgroundColor(Color.GREEN);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.taskChecked(tasks.get(position).getUniqueID());
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.remove(position); //todo tohle nemuze byt takto
            }
        });

        return viewTask;
    }
}
