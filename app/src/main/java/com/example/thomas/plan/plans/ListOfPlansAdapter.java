package com.example.thomas.plan.plans;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Plan;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 22-Apr-18.
 */

public class ListOfPlansAdapter extends BaseAdapter {

    private List<Plan> mPlans;
    private TextView nameOfPlan;
    private View planView;
    private ImageButton infoButton, deleteButton;
    private LinearLayout linearLayout;
    private ActionItemListener actionListener;
    private ImageView imageView;

    private String NAME_OF_CLASS = getClass().getName();


    public ListOfPlansAdapter(List<Plan> plans, ActionItemListener actionItemListener) {
        actionListener = actionItemListener;
        mPlans = plans;
    }
    public ListOfPlansAdapter(Plan plan, ActionItemListener actionItemListener){
        actionListener = actionItemListener;
        mPlans = new ArrayList<>();
        mPlans.add(plan);
    }

    public void replaceData(List<Plan> plans) {
        mPlans = plans;
        notifyDataSetChanged();
    }
    public void clearData(){
        mPlans.clear();
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


        imageView = planView.findViewById(R.id.plan_imageview);
        nameOfPlan = planView.findViewById(R.id.plan_name);
        infoButton = planView.findViewById(R.id.infoButton);
        deleteButton = planView.findViewById(R.id.deleteButton);
        linearLayout = planView.findViewById(R.id.plan_item_linear);

        if(!mPlans.isEmpty()){
            nameOfPlan.setText(mPlans.get(position).getName());

            if(mPlans.get(position).isImageSet()) {
                StorageReference ref = FirebaseStorage.getInstance()
                        .getReference().child(mPlans.get(position).getUniqueID());
                GlideApp.with(planView)
                        .load(ref)
                        .into(imageView);
            }

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionListener.onItemClick(mPlans.get(position));
                }
            });

            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionListener.onItemInfoClick(mPlans.get(position));
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionListener.onItemDeleteClick(mPlans.get(position));
                }
            });
        }
        return planView;
    }
}
