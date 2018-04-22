package com.example.thomas.plan.plans;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import com.example.thomas.plan.data.Models.Plan;

/**
 * Created by Tomas on 22-Apr-18.
 */

public class PlansListBinding {

    @BindingAdapter("app:plans")
    public static void setPlans(ListView listView, ObservableList<Plan> items) {
        ListOfPlansAdapter adapter = (ListOfPlansAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }
}

