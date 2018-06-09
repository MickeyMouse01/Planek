package com.example.thomas.plan.planForClient;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;

import java.util.List;

public class SimplePlanAdapter extends BaseAdapter {

    private PreviewClientViewModel viewModel;
    private Plan plan;
    private View planView;
    private TextView nameOfPlan;
    private ImageButton infoButton, deleteButton;
    private LinearLayout linearLayout;

    public SimplePlanAdapter(Plan plan,
                             PreviewClientViewModel viewModel) {
        this.viewModel = viewModel;
        this.plan = plan;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return plan;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        planView = inflater.inflate(R.layout.plan_item, null);
        nameOfPlan = planView.findViewById(R.id.plan_name);
        infoButton = planView.findViewById(R.id.infoButton);
        deleteButton = planView.findViewById(R.id.deleteButton);
        linearLayout = planView.findViewById(R.id.plan_item_linear);

        nameOfPlan.setText(plan.getName());



        return planView;
    }
}
