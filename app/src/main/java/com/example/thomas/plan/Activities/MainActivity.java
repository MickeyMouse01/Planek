package com.example.thomas.plan.Activities;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.ClientsRepository;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.Clients.ListOfClientsAdapter;
import com.example.thomas.plan.Clients.ClientsFragment;
import com.example.thomas.plan.Clients.ClientsViewModel;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Local.DatabaseInitializer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private ArrayList<Client> listOfClients;
    private ListView listview;
    private ListOfClientsAdapter listAdapter;
    private ClientsViewModel mViewModel;
    private Toolbar toolbar;
    private ClientsRepository repository;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupViewFragment();
        setupDrawerLayout();
        setupNavigation();

        mViewModel = obtainViewModel(this);

        mViewModel.getAddNewClient().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                Log.d("Blah", "saf");
                Toast.makeText(MainActivity.this, "TOto je klinuti", Toast.LENGTH_SHORT).show();
            }
        });
        repository = ClientsRepository.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigation(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupViewFragment() {
        ClientsFragment clientsFragment =
                (ClientsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (clientsFragment == null) {
            // Create the fragment
            clientsFragment = ClientsFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), clientsFragment, R.id.contentFrame);
        }
    }

    public static ClientsViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        ClientsViewModel viewModel = ViewModelProviders.of(activity,factory).get(ClientsViewModel.class);

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

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_client) {
            repository.getClient("e5edccbf-4cf9-40a3-b93a-d2ce8e81c398");

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
