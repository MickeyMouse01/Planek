package com.example.thomas.plan.plans;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.thomas.plan.Clients.ClientsFragment;
import com.example.thomas.plan.Clients.ClientsViewModel;
import com.example.thomas.plan.Clients.ListOfClientsAdapter;


/**
 * Created by Tomas on 22-Apr-18.
 */

public class PlansFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private PlansViewModel mPlansViewModel;
    private ListOfPlansAdapter mPlansAdapter;
    //private PlansFragmentBinding mPlansFragmentBinding;

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
