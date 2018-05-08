package com.example.thomas.plan.plans;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.thomas.plan.Clients.MainViewModel;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.databinding.PlanItemBinding;

import java.util.List;

/**
 * Created by Tomas on 22-Apr-18.
 */

public class  ListOfPlansAdapter extends BaseAdapter {

    private MainViewModel mMainViewModel;
    private List<Plan> mPlans;


    public ListOfPlansAdapter(List<Plan> plans,
                                MainViewModel mainViewModel) {
        mMainViewModel = mainViewModel;
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
    public View getView(int position, View view, final ViewGroup viewGroup) {
        PlanItemBinding binding;
        View v = view;
        Plan p = getItem(position);

        if (view == null) {
            // Inflate
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            v = inflater.inflate(R.layout.plan_item, null);
            // Create the binding
            binding = PlanItemBinding.inflate(inflater, viewGroup, false);
            ImageView imageView = v.findViewById(R.id.myImageView);
            //todo kdybych nechtel bindovani

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
                mMainViewModel.viewPlan().setValue(plan.getUniqueID());
            }

            //todo nejaky modal okno na potvrzovani
            //todo nejak spatne se maze plan
            @Override
            public void removePlan(Plan plan) {
                mMainViewModel.removePlan(plan.getUniqueID());
            }
        };

        binding.setPlan(mPlans.get(position));
        binding.setListener(userActionsListener);
        binding.executePendingBindings();
        return binding.getRoot();
    }
}
