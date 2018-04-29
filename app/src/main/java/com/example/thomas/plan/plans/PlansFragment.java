package com.example.thomas.plan.plans;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.thomas.plan.Clients.ListOfClientsAdapter;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.databinding.PlansFragmentBinding;


import java.util.ArrayList;


/**
 * Created by Tomas on 22-Apr-18.
 */

public class  PlansFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private PlansViewModel mPlansViewModel;
    private ListOfPlansAdapter mPlansAdapter;
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
        mPlansViewModel.getPlans();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        ListView listView =  mPlansFragmentBinding.plansList;

        mPlansAdapter = new ListOfPlansAdapter(
                new ArrayList<Plan>(0),
                mPlansViewModel
        );
        listView.setAdapter(mPlansAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
