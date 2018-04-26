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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thomas.plan.addEditClient.AddEditClientActivity;
import com.example.thomas.plan.Activities.LoginActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.R;
import com.example.thomas.plan.addEditPlan.AddEditPlanActivity;
import com.example.thomas.plan.plans.PlansFragment;
import com.example.thomas.plan.viewClient.ViewClientActivity;


public class ClientsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ClientsViewModel mViewModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupViewFragment(0);
        setupDrawerLayout();
        setupNavigation();

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
            ClientsFragment clientsFragment =
                    (ClientsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            if (clientsFragment == null) {
                // Create the fragment
                clientsFragment = ClientsFragment.newInstance();
                ActivityUtils.replaceFragmentInActivity(
                        getSupportFragmentManager(), clientsFragment, R.id.contentFrame);
            }
            //todo opravit chybu, cannot be cast to plansfragmetn
        } else {
            PlansFragment plansFragment =
                    (PlansFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            if (plansFragment == null) {
                // Create the fragment
                plansFragment = PlansFragment.newInstance();
                ActivityUtils.replaceFragmentInActivity(
                        getSupportFragmentManager(), plansFragment, R.id.contentFrame);
        }

    }
    }


    public static ClientsViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        ClientsViewModel viewModel = ViewModelProviders.of(activity, factory).get(ClientsViewModel.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setupViewFragment(1);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_client) {
            mViewModel.addNewClient().call();
        }
         else if (id == R.id.nav_add_plan) {
            mViewModel.addNewPlan().call();

        } else if (id == R.id.nav_list_clients) {

        } else if (id == R.id.nav_list_plans) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void addNewClient(){
        Intent intent = new Intent(this, AddEditClientActivity.class);
        startActivity(intent);
    }

    private void addNewPlan(){
        Intent intent = new Intent(this, AddEditPlanActivity.class);
        startActivityForResult(intent,1);
    }
    private void viewClient(String clientId){
        Intent intent = new Intent(this, ViewClientActivity.class);
        startActivity(intent);
    }

}
