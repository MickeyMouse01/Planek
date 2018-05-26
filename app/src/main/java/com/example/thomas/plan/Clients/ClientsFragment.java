package com.example.thomas.plan.Clients;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.databinding.ClientsFragmentBinding;

import java.util.ArrayList;

/**
 * Created by pospe on 15.02.2018.
 */



public class ClientsFragment extends Fragment
         {

    private MainViewModel mMainViewModel;
    private ListOfClientsAdapter mClientsAdapter;
    private ClientsFragmentBinding mClientsFragmentBinding;

    public ClientsFragment() {
        // Requires empty public constructor
    }

    public static ClientsFragment newInstance() {
        return new ClientsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewModel.getClients();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClientsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.clients_fragment, container, false);

        mMainViewModel = ClientsActivity.obtainViewModel(getActivity());

        mClientsFragmentBinding.setViewmodel(mMainViewModel);

        setHasOptionsMenu(true);

        return mClientsFragmentBinding.getRoot();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        ListView listView =  mClientsFragmentBinding.clientsList;


       mClientsAdapter = new ListOfClientsAdapter(
                new ArrayList<Client>(0),
               mMainViewModel
        );
        listView.setAdapter(mClientsAdapter);
    }
}
