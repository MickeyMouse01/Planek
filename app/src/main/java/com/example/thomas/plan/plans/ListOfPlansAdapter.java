package com.example.thomas.plan.plans;

import android.arch.lifecycle.ViewModel;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thomas.plan.Clients.MainViewModel;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.databinding.PlanItemBinding;

import java.util.List;

/**
 * Created by Tomas on 22-Apr-18.
 */

public class ListOfPlansAdapter extends BaseAdapter {

    private MainViewModel mMainViewModel;
    private List<Plan> mPlans;
    private TextView nameOfPlan;
    private View planView;
    private ImageButton infoButton, deleteButton;
    private LinearLayout linearLayout;

    private String NAME_OF_CLASS = getClass().getName();


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
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        planView = inflater.inflate(R.layout.plan_item, null);

        nameOfPlan = planView.findViewById(R.id.plan_name);
        infoButton = planView.findViewById(R.id.infoButton);
        deleteButton = planView.findViewById(R.id.deleteButton);
        linearLayout = planView.findViewById(R.id.plan_item_linear);


        nameOfPlan.setText(mPlans.get(position).getName());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.previewPlan().setValue(mPlans.get(position).getUniqueID());
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.viewPlan().setValue(mPlans.get(position).getUniqueID());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.removePlan(mPlans.get(position).getUniqueID());
            }
        });
        return planView;
    }
}
