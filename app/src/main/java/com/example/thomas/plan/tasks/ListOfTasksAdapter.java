package com.example.thomas.plan.tasks;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ListOfTasksAdapter extends BaseAdapter {

    private List<Task> tasks;
    private ActionItemListener taskItemListener;

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

        View viewTask = inflater.inflate(R.layout.task_item, null);
        TextView textViewName = viewTask.findViewById(R.id.view_plan_task_name);
        ImageView deleteImage = viewTask.findViewById(R.id.view_plan_task_delete_image);
        ConstraintLayout constraintLayout = viewTask.findViewById(R.id.view_plan_relative_layout);
        CheckBox checkBox = viewTask.findViewById(R.id.view_plan_ispassed);
        ImageView imageView = viewTask.findViewById(R.id.view_plan_imageView);
        textViewName.setText(tasks.get(position).getName());

        if (tasks.get(position).isPassed()) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(viewTask.getContext(), R.color.isPassed));
            checkBox.setChecked(true);
            checkBox.setButtonDrawable(R.drawable.scaled_passed_checkbox);
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
                notifyDataSetChanged();
                taskItemListener.onCheckedClick(tasks.get(position));

            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskItemListener.onItemClick(tasks.get(position));
            }
        });

        constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("podrzel", "podrzel");
                return true;
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
