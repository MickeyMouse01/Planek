package com.example.thomas.plan.tasks;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListOfTasksAdapter extends BaseAdapter {

    private List<Task> tasks;
    private View viewTask;
    private TextView textViewName;
    private ImageView infoImage, deleteImage;
    private CheckBox checkBox;
    private RelativeLayout relativeLayout;
    private ActionItemListener taskItemListener;
    private ImageView imageView;


    public ListOfTasksAdapter(List<Task> tasks, ActionItemListener taskListener) {
        taskItemListener = taskListener;
        this.tasks = tasks;
    }

    public void replaceData(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
    public void clearData(){
        tasks.clear();
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
        imageView = viewTask.findViewById(R.id.view_plan_imageView);
        textViewName.setText(tasks.get(position).getName());

        if (tasks.get(position).isPassed()) {
            relativeLayout.setBackgroundColor(Color.GREEN);
            checkBox.setChecked(true);
            deleteImage.setVisibility(View.VISIBLE);
        }

        if(tasks.get(position).isImageSet()) {
            StorageReference ref = FirebaseStorage.getInstance()
                    .getReference().child(tasks.get(position).getUniqueID());
            GlideApp.with(viewTask)
                    .load(ref)
                    .into(imageView);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isPassed = tasks.get(position).isPassed();
                tasks.get(position).setPassed(!isPassed);
                taskItemListener.onCheckedClick(tasks.get(position));
                notifyDataSetChanged();

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskItemListener.onItemClick(tasks.get(position));
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskItemListener.onItemDeleteClick(tasks.get(position));
            }
        });

        return viewTask;
    }
}
