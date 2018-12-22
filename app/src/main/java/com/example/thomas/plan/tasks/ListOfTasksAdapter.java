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
import com.example.thomas.plan.data.Models.Settings;
import com.example.thomas.plan.data.Models.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.List;

public class ListOfTasksAdapter extends BaseAdapter {

    private List<Task> tasks;
    private ActionItemListener taskItemListener;
    private Settings settings;

    public ListOfTasksAdapter(List<Task> tasks,
                              ActionItemListener taskListener,
                              Settings settings) {
        taskItemListener = taskListener;
        this.tasks = tasks;
        this.settings = settings;
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
        TextView time = viewTask.findViewById(R.id.task_item_time);

        final Task actualTask = tasks.get(position);

        if(!actualTask.getName().isEmpty()){
            textViewName.setText(actualTask.getName());
        }

        if (!actualTask.getTime().isEmpty()){
            time.setText(actualTask.getTime());
            time.setVisibility(View.VISIBLE);
        }

        if(actualTask.isImageSet()) {
            StorageReference ref = FirebaseStorage.getInstance()
                    .getReference().child(actualTask.getNameOfImage());
            GlideApp.with(viewTask)
                    .load(ref)
                    .into(imageView);
        }

        if (actualTask.isPassed()) {
            constraintLayout.setBackgroundColor(ContextCompat.getColor(viewTask.getContext(), R.color.isPassed));
            checkBox.setChecked(true);
            checkBox.setButtonDrawable(R.drawable.scaled_passed_checkbox);
            deleteImage.setVisibility(View.VISIBLE);
        }
        if(!settings.isDisabledDeleteButton()){
            deleteImage.setVisibility(View.VISIBLE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isPassed = actualTask.isPassed();
                actualTask.setPassed(!isPassed);
                notifyDataSetChanged();
                taskItemListener.onCheckedClick(actualTask);

            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskItemListener.onItemClick(actualTask);
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskItemListener.onItemDeleteClick(actualTask);
            }
        });

        return viewTask;
    }
}
