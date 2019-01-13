package com.example.thomas.plan.adapters;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.interfaces.ActionItemListener;

import java.util.List;

public class ListOfDaysAdapter extends BaseAdapter {

    private List<String> mDays;
    private ActionItemListener actionListener;
    private String nameOfDay;
    private String nameOfWeek;
    private int actualDay, actualWeek;

    public ListOfDaysAdapter(List<String> days, ActionItemListener actionItemListener) {
        actionListener = actionItemListener;
        mDays = days;
        setDayAndWeek();
    }

    private void setDayAndWeek() {
        actualDay = ActivityUtils.getActualDay();
        actualWeek = ActivityUtils.getActualNumberOfWeek();

        nameOfDay = Enums.Day.values()[actualDay].toString();
        nameOfWeek = Enums.Week.values()[actualWeek].toString();
    }

    public void replaceData(List<String> days) {
        mDays = days;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDays != null ? mDays.size() : 0;
    }

    @Override
    public String getItem(int position) {
        return mDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View dayView = inflater.inflate(R.layout.day_item, null);

        TextView txtName = dayView.findViewById(R.id.name_of_day);
        ConstraintLayout constraintLayout = dayView.findViewById(R.id.days_item_layout);
        txtName.setText(mDays.get(position));

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onItemClick(mDays.get(position));
            }
        });

        if (mDays.contains(nameOfWeek)){
            if (position == actualWeek){
                constraintLayout.setBackgroundColor(ContextCompat.getColor(dayView.getContext(), R.color.isPassed));
            }
        }
        if (mDays.contains(nameOfDay)){
            if (position == actualDay){
                constraintLayout.setBackgroundColor(ContextCompat.getColor(dayView.getContext(), R.color.isPassed));
            }
        }


        return dayView;
    }
}
