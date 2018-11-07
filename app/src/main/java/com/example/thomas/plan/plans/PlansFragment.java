package com.example.thomas.plan.plans;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Clients.ClientsActivity;
import com.example.thomas.plan.Clients.MainViewModel;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.databinding.PlansFragmentBinding;

import java.util.ArrayList;


/**
 * Created by Tomas on 22-Apr-18.
 */

public class PlansFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainViewModel mMainViewModel;
    private PlansFragmentBinding mPlansFragmentBinding;

    public PlansFragment() {
        // Requires empty public constructor
    }

    public static PlansFragment newInstance() {
        return new PlansFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewModel.getPlans();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPlansFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.plans_fragment, container, false);

        mMainViewModel = ClientsActivity.obtainViewModel(getActivity());
        mPlansFragmentBinding.setViewmodel(mMainViewModel);
        setHasOptionsMenu(true);
        setupListAdapter();

        return mPlansFragmentBinding.getRoot();
    }

    private void setupListAdapter() {
        ListView listView = mPlansFragmentBinding.plansList;
        ActionItemListener actionListener = new ActionItemListener<Plan>() {
            @Override
            public void onCheckedClick(Plan item) {

            }

            @Override
            public void onItemClick(Plan item) {
                mMainViewModel.viewPlan().setValue(item.getUniqueID());
            }

            @Override
            public void onItemInfoClick(Plan item) {

            }

            @Override
            public void onItemDeleteClick(Plan item) {
                mMainViewModel.removePlan(item.getUniqueID());
            }
        };

        ListOfPlansAdapter mPlansAdapter = new ListOfPlansAdapter(
                new ArrayList<Plan>(0), actionListener
        );
        listView.setAdapter(mPlansAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
