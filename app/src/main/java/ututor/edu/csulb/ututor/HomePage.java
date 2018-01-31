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
    boolean isVisible = true;

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
            //super.onBackPressed();
            if(isVisible == false){
                isVisible = true;
                startActivity(new Intent(HomePage.this, HomePage.class));
            }
            else{
                //nothing
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int storedID = 0;

        Fragment fragment = null;

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.mainFrame);




        if (id == R.id.nav_home) {
            if(isVisible == false){
                isVisible = true;

                startActivity(new Intent(HomePage.this, HomePage.class));
            }
            else{
                //nothing
            }

        } else if (id == R.id.nav_userProfile) {
            isVisible = false;
            fragment = new GenericProfile();

        } else if (id == R.id.nav_searchList) {
            isVisible = false;
            Toast.makeText(getApplicationContext(), "Search List" , Toast.LENGTH_SHORT).show();
            fragment = new SearchList();
        } else if (id == R.id.nav_searchMap) {
            isVisible = false;
            Toast.makeText(getApplicationContext(), "Search Map" , Toast.LENGTH_SHORT).show();
            fragment = new SearchMap();

        } else if (id == R.id.nav_appointmentSchedule) {
            isVisible = false;
            Toast.makeText(getApplicationContext(), "Schedule Appointment" , Toast.LENGTH_SHORT).show();
            fragment = new GeneralAppointment();

        } else if (id == R.id.nav_faq) {
            isVisible = false;
            Toast.makeText(getApplicationContext(), "FAQ" , Toast.LENGTH_SHORT).show();
            fragment = new FAQ();

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
