package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;



        if (id == R.id.nav_home) {
            startActivity(new Intent(HomePage.this, HomePage.class));
        } else if (id == R.id.nav_userProfile) {
            Toast.makeText(getApplicationContext(), "User Profile" , Toast.LENGTH_SHORT).show();
            fragment = new GenericProfile();

        } else if (id == R.id.nav_searchList) {
            Toast.makeText(getApplicationContext(), "Search List" , Toast.LENGTH_SHORT).show();
            fragment = new SearchList();
        } else if (id == R.id.nav_searchMap) {
            Toast.makeText(getApplicationContext(), "Search Map" , Toast.LENGTH_SHORT).show();
            fragment = new SearchMap();

        } else if (id == R.id.nav_appointmentSchedule) {
            Toast.makeText(getApplicationContext(), "Schedule Appointment" , Toast.LENGTH_SHORT).show();
            fragment = new GeneralAppointment();

        } else if (id == R.id.nav_registration) {
            Toast.makeText(getApplicationContext(), "Registration" , Toast.LENGTH_SHORT).show();
            fragment = new Registration();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, fragment).commit();


        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
