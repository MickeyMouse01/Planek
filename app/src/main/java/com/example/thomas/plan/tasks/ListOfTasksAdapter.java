package com.example.thomas.plan.tasks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        infoImage = viewTask.findViewById(R.id.view_plan_task_info_image);
        deleteImage = viewTask.findViewById(R.id.view_plan_task_delete_image);
        textViewName.setText(tasks.get(position).getName());
        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("klikanec","klikanec");
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteTask(tasks.get(position).getUniqueID());
            }
        });

        return viewTask;
    }
}
