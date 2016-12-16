package com.example.hana.rentcostumes;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hana.rentcostumes.fragment.ExploreFragment;
import com.example.hana.rentcostumes.fragment.FavoriteCostumeFragment;
import com.example.hana.rentcostumes.fragment.HelpFragment;
import com.example.hana.rentcostumes.fragment.NotificationsFragment;
import com.example.hana.rentcostumes.fragment.RentFragment;
import com.example.hana.rentcostumes.fragment.RentedCostumeFragment;
import com.example.hana.rentcostumes.fragment.SettingFragment;
import com.example.hana.rentcostumes.fragment.UsedCostumeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ExploreFragment.OnFragmentInteractionListener, RentFragment.OnFragmentInteractionListener, UsedCostumeFragment.OnFragmentInteractionListener,
        FavoriteCostumeFragment.OnFragmentInteractionListener, NotificationsFragment.OnFragmentInteractionListener, RentedCostumeFragment.OnFragmentInteractionListener,
        HelpFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener {
    SessionActivity sessionActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = ExploreFragment.class;
            try {
                fragment = (Fragment)fragmentClass.newInstance();
            } catch (Exception e){
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();
        }


        sessionActivity = new SessionActivity();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }
    */

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        //handle fragment
        if (id == R.id.nav_view) {
            fragmentClass = ExploreFragment.class;
        } else if (id == R.id.nav_gallery) {
            fragmentClass = RentFragment.class;
        } else if (id == R.id.notification) {
            fragmentClass = NotificationsFragment.class;
        } else if (id == R.id.rentedcostumes) {
            fragmentClass = RentedCostumeFragment.class;
        } else if (id == R.id.usedcostumes) {
            fragmentClass = UsedCostumeFragment.class;
        } else if (id == R.id.favorite) {
            fragmentClass = FavoriteCostumeFragment.class;
        } else if (id == R.id.menuhelp) {
            fragmentClass = HelpFragment.class;
        } else if (id == R.id.setting) {
            fragmentClass = SettingFragment.class;
        } else if (id == R.id.signout){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Logging Out...");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            sessionActivity.clearPreferences(getApplicationContext(), "status");
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                        }
                    }, 2000);
        }
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
