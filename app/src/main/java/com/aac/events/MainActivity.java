package com.aac.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.onesignal.OneSignal;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;

    private static final String TAG = MainActivity.class.getName();
    private String agendaURL = "https://dl.dropboxusercontent.com/s/piavrsxzyp929lr/AgendaData.json?dl=0";
    private String cohortsURL = "https://dl.dropboxusercontent.com/s/yoxo4gjgo4vpm26/CohortsData.json?dl=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up dynamic content through JSON retrieval
        getDynamicJSONData();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_about);
        }

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private void getDynamicJSONData() {
        mRequestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, agendaURL,   new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  mJSONArray jsonArray = response.getJSONArray()
                Log.i(TAG, "Response : " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.toString());
            }
        });

        mRequestQueue.add(stringRequest);
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
        switch (item.getItemId()) {
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                break;
            case R.id.nav_planning_team:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlanningTeamFragment()).commit();
                break;
            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FaqFragment()).commit();
                break;
            case R.id.nav_donate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DonateFragment()).commit();
                break;
            case R.id.nav_norms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NormsFragment()).commit();
                break;
            case R.id.nav_aacare:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AacareFragment()).commit();
                break;
            case R.id.nav_sponsor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SponsorFragment()).commit();
                break;
            case R.id.nav_aaconnect:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AaconectFragment()).commit();
                break;
            case R.id.nav_newsletter:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsletterFragment()).commit();
                break;
            case R.id.nav_contact_us:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "afghanamericanconference@gmail.com" });
                startActivity(Intent.createChooser(intent, ""));
                break;
            case R.id.nav_credits:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreditsFragment()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
