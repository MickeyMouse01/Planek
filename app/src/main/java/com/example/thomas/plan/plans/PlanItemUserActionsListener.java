package com.example.thomas.plan.plans;

import com.example.thomas.plan.data.Models.Plan;

/**
 * Created by Tomas on 22-Apr-18.
 */

public interface PlanItemUserActionsListener {

    void onPlanClicked(Plan plan);
    void onPlanInfoClicked(Plan plan);
    void removePlan(Plan plan);
}
