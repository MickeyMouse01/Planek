package com.example.thomas.plan.previewTasks;

import com.example.thomas.plan.data.Models.Task;

import java.util.List;

public interface TasksListener {

    void updateTasks(List<Task> tasks);
    void getTaskInfo();
}
