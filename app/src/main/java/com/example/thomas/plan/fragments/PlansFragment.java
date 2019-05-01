package com.example.thomas.plan.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
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

import com.example.thomas.plan.interfaces.ActionItemListener;
import com.example.thomas.plan.activities.MainActivity;
import com.example.thomas.plan.viewmodels.MainViewModel;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.adapters.ListOfPlansAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tomas on 22-Apr-18.
 */

public class PlansFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainViewModel mMainViewModel;
    private ListOfPlansAdapter mPlansAdapter;
    private ListView listView;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog mProgressDialog;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.plans_fragment, null);
        listView = view.findViewById(R.id.plans_list);

        mMainViewModel = MainActivity.obtainViewModel(getActivity());

        mMainViewModel.getPlans().observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable List<Plan> plans) {
                if (mPlansAdapter != null) {
                    if (plans != null){
                        mPlansAdapter.replaceData(plans);
                    } else {
                        mPlansAdapter.clearData();
                    }

                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
        alertDialog = createConfirmDialog();
    }

    private AlertDialog.Builder createConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_dialog_message);
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
    }

    private void setupListAdapter() {
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
            public void onItemDeleteClick(final Plan item) {
                alertDialog.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMainViewModel.removePlan(item.getUniqueID());
                    }
                }).show();

            }
        };

        mPlansAdapter = new ListOfPlansAdapter(
                new ArrayList<Plan>(0), actionListener
        );
        listView.setAdapter(mPlansAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
