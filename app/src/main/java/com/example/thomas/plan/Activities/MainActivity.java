package com.example.thomas.plan.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Clients.Client;
import com.example.thomas.plan.Clients.ListOfClientsAdapter;
import com.example.thomas.plan.Clients.ClientsFragment;
import com.example.thomas.plan.Clients.ClientsViewModel;
import com.example.thomas.plan.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private ArrayList<Client> listOfClients;
    private ListView listview;
    private ListOfClientsAdapter listAdapter;
    private ClientsViewModel mViewModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupViewFragment();
        setupDrawerLayout();
        setupNavigation();
/*
        mViewModel = ViewModelProviders.of(this).get(ClientsViewModel.class);
        listOfClients = mViewModel.getUsers().getValue();




        mViewModel.getUsers().observe(this, new Observer<ArrayList<Client>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Client> clients) {
                listAdapter.notifyDataSetChanged();
            }
        });



        Log.d("MainActivity", "Znovu jsem zavolal metodu onCreate");
        listview = (ListView) findViewById(R.id.list_view);
        listAdapter = new ListOfClientsAdapter(MainActivity.this, listOfClients);
        listview.setAdapter(listAdapter);*/
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

        ClientsViewModel viewModel = ViewModelProviders.of(activity).get(ClientsViewModel.class);

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
        Bundle blah = data.getBundleExtra("listOfClients");
        listOfClients = blah.getParcelableArrayList("listOfClients");

      //  listAdapter.updateListOfClients(listOfClients);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_client) {
            Bundle data = new Bundle();
            data.putParcelableArrayList("listOfClients", listOfClients);

            Intent intent = new Intent(this, AddNewClientActivity.class);
            intent.putExtra("listOfClients",data);
            startActivityForResult(intent,1);

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
