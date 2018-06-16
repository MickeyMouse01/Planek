package com.example.thomas.plan;

import com.example.thomas.plan.data.Models.Task;

public interface TaskActionListener extends ActionItemListener {
    void onItemChecked(Task item);
}
