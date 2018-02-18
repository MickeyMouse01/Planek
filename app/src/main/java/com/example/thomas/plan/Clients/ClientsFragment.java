package com.example.thomas.plan.Clients;

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

import com.example.thomas.plan.Activities.MainActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.databinding.ClientsFragmentBinding;

import java.util.ArrayList;

/**
 * Created by pospe on 15.02.2018.
 */

public class ClientsFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private ClientsViewModel mClientsViewModel;
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
        mClientsViewModel.getUsers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClientsFragmentBinding = ClientsFragmentBinding.inflate(inflater, container, false);

        mClientsViewModel = MainActivity.obtainViewModel(getActivity());

        mClientsFragmentBinding.setViewmodel(mClientsViewModel);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_client) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
        }

        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setupListAdapter();


    }


   /* private void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.active:
                        mTasksViewModel.setFiltering(TasksFilterType.ACTIVE_TASKS);
                        break;
                    case R.id.completed:
                        mTasksViewModel.setFiltering(TasksFilterType.COMPLETED_TASKS);
                        break;
                    default:
                        mTasksViewModel.setFiltering(TasksFilterType.ALL_TASKS);
                        break;
                }
                mTasksViewModel.loadTasks(false);
                return true;
            }
        });

        popup.show();
    }*/
/*
    private void setupFab() {
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTasksViewModel.addNewTask();
            }
        });
    }*/

    private void setupListAdapter() {
        ListView listView =  mClientsFragmentBinding.tasksList;

       mClientsAdapter = new ListOfClientsAdapter(
                new ArrayList<Client>(0),
                mClientsViewModel
        );
        listView.setAdapter(mClientsAdapter);
    }


}
