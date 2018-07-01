package com.example.thomas.plan.Clients;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.addEditClient.AddEditClientActivity;
import com.example.thomas.plan.loginAndRegister.LoginActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.R;
import com.example.thomas.plan.addEditPlan.AddEditPlanActivity;
import com.example.thomas.plan.planForClient.PreviewClientActivity;
import com.example.thomas.plan.plans.PlansFragment;
import com.example.thomas.plan.previewTasks.PreviewTasksActivity;
import com.example.thomas.plan.viewClientInfo.ViewClientActivity;
import com.example.thomas.plan.viewPlanInfo.ViewPlanInfoActivity;


public class ClientsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainViewModel mViewModel;
    private Toolbar toolbar;
    private final int VIEW_CLIENTS = 0;
    private final int VIEW_PLANS = 1;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        getContentView();

        mViewModel = obtainViewModel(this);
        mViewModel.addNewClient().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                addNewClient();
            }
        });
        mViewModel.addNewPlan().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                addNewPlan();
            }
        });
        mViewModel.viewClient().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String clientId) {
                viewClient(clientId);
            }
        });
        mViewModel.previewClient().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String clientId) {
                previewClient(clientId);
            }
        });
        mViewModel.viewPlan().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String planId) {
                viewPlan(planId);
            }
        });

        mViewModel.previewPlan().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String planId) {
                previewPlan(planId);
            }
        });

        setupViewFragment(mViewModel.getCurrentFragment().getValue());
        setupToolbar();
        setupDrawerLayout();
        setupNavigation();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupDrawerLayout() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigation() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupViewFragment(int frame) {
        if (frame != 1) {
            ClientsFragment clientsFragment = ClientsFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), clientsFragment, R.id.contentFrame);
        } else {

            PlansFragment plansFragment = PlansFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), plansFragment, R.id.contentFrame);
        }
    }

    public static MainViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        MainViewModel viewModel = ViewModelProviders.of(activity, factory).get(MainViewModel.class);
        return viewModel;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
            mViewModel.addNewClient().call();
        } else if (id == R.id.nav_add_plan) {
            mViewModel.addNewPlan().call();

        } else if (id == R.id.nav_list_clients) {
            setupViewFragment(0);

        } else if (id == R.id.nav_list_plans) {
            setupViewFragment(1);

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addNewClient() {
        Intent intent = new Intent(this, AddEditClientActivity.class);
        startActivity(intent);
    }

    private void addNewPlan() {
        Intent intent = new Intent(this, AddEditPlanActivity.class);
        startActivityForResult(intent, 1);
    }

    private void viewClient(String clientId) {
        mViewModel.getCurrentFragment().setValue(VIEW_CLIENTS);
        Intent intent = new Intent(this, ViewClientActivity.class);
        intent.putExtra("ClientId", clientId);
        startActivity(intent);
    }

    private void previewClient(String clientId) {
        mViewModel.getCurrentFragment().setValue(VIEW_CLIENTS);
        Intent intent = new Intent(this, PreviewClientActivity.class);
        intent.putExtra("ClientId", clientId);
        startActivity(intent);
    }

    private void viewPlan(String planId) {
        mViewModel.getCurrentFragment().setValue(VIEW_PLANS);
        Intent intent = new Intent(this, ViewPlanInfoActivity.class);
        intent.putExtra("PlanId", planId);
        startActivity(intent);
    }

    private void previewPlan(String planId){
        mViewModel.getCurrentFragment().setValue(VIEW_PLANS);
        Intent intent = new Intent(this, PreviewTasksActivity.class);
        intent.putExtra("PlanId", planId);
        startActivity(intent);
    }
}
