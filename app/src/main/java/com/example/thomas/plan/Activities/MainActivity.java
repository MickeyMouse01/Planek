package com.example.thomas.plan.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thomas.plan.Client;
import com.example.thomas.plan.Adapters.ListOfClientsAdapter;
import com.example.thomas.plan.MainViewModel;
import com.example.thomas.plan.R;

import java.util.ArrayList;

import static com.example.thomas.plan.Common.Enums.TypeOfGroup;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private ArrayList<Client> listOfClients;
    private ListView listview;
    private ListOfClientsAdapter listAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d("MainActivity", "Znovu jsem zavolal metodu onCreate");
        listview = (ListView) findViewById(R.id.list_view);

        initClients(mViewModel);

        listAdapter = new ListOfClientsAdapter(MainActivity.this, listOfClients);

        listview.setAdapter(listAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " + listOfClients.get(+position), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void initClients(MainViewModel mViewModel) {
        listOfClients = new ArrayList<Client>();
        listOfClients.add(new Client("Josef","Namornik", TypeOfGroup.GROUPA));
        listOfClients.add(new Client("Tomas","Blah", TypeOfGroup.GROUPB));
        listOfClients.add(new Client("Sarka","Furit", TypeOfGroup.GROUPC));
        listOfClients.add(new Client("Michael","Parnik", TypeOfGroup.GROUPB));
        listOfClients.add(new Client("Honza","Lod", TypeOfGroup.GROUPB));
        listOfClients.add(new Client("Martin","Neco", TypeOfGroup.GROUPC));

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

        listAdapter.updateListOfClients(listOfClients);


    }

    @SuppressWarnings("StatementWithEmptyBody")
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
