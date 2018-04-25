package com.example.thomas.plan.plans;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.databinding.PlanItemBinding;

import java.util.List;

/**
 * Created by Tomas on 22-Apr-18.
 */

public class  ListOfPlansAdapter extends BaseAdapter {

    private PlansViewModel mPlansViewModel;
    private List<Plan> mPlans;


    public ListOfPlansAdapter(List<Plan> plans,
                                PlansViewModel plansViewModel) {
        mPlansViewModel = plansViewModel;
        mPlans = plans;

    }

    public void replaceData(List<Plan> plans) {
        mPlans = plans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPlans != null ? mPlans.size() : 0;
    }

    @Override
    public Plan getItem(int position) {
        return mPlans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        PlanItemBinding binding;
        if (view == null) {
            // Inflate
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            // Create the binding
            binding = PlanItemBinding.inflate(inflater, viewGroup, false);
        } else {
            // Recycling view
            binding = DataBindingUtil.getBinding(view);
        }

        PlanItemUserActionsListener userActionsListener = new PlanItemUserActionsListener() {
            @Override
            public void onPlanClicked(Plan plan) {

            }

            @Override
            public void onPlanInfoClicked(Plan plan) {
                mPlansViewModel.viewPlan().setValue(plan.getUniqueID());
            }

            //todo nejaky modal okno na potvrzovani
            @Override
            public void removePlan(Plan plan) {
                mPlansViewModel.removePlan(plan.getUniqueID());
            }
        };

        binding.setPlan(mPlans.get(position));

        binding.setListener(userActionsListener);

        binding.executePendingBindings();
        return binding.getRoot();
    }
}
