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
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.onesignal.OneSignal;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;

    private static final String TAG = MainActivity.class.getName();
    protected final static String agendaURL = "https://dl.dropboxusercontent.com/s/piavrsxzyp929lr/AgendaData.json?dl=0";
    protected final static String cohortsURL = "https://dl.dropboxusercontent.com/s/yoxo4gjgo4vpm26/CohortsData.json?dl=0";
    protected final static String testURL = "https://api.myjson.com/bins/rc6p2";
    protected final static String agendaFileName = "agendaData.json";
    protected final static String sponsorFileName = "sponsorData.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up dynamic content through JSON retrieval
        mRequestQueue = Volley.newRequestQueue(this);
        //getDynamicJSONData();
        getDynamicJSONArray();
        //testGetJSON();

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

    private JSONArray parseAgenda(JSONObject response) throws JSONException {
        JSONArray sessionDays = response.getJSONArray("sessionDays");
        JSONArray eventsArray = new JSONArray();

        // sessiondays length should be 3 (0 = fri, 1 = sat, 2 = sun)
        for (int dayIndex = 0; dayIndex < sessionDays.length(); dayIndex++) {
            Log.i(TAG, "ENTERED: sessionDay: " + dayIndex);
            JSONObject sessionDay = sessionDays.getJSONObject(dayIndex);
            JSONArray sessions = sessionDay.getJSONArray("sessions");

            // sessions length is number of time slots (0 = 12pm-1:15pm, 1 = 1:30pm-3pm, etc...)
            for (int sessionTimeIndex = 0; sessionTimeIndex < sessions.length(); sessionTimeIndex++) {
                Log.i(TAG, "SESSIONDAY: " + sessionTimeIndex + " session: " + sessionTimeIndex);
                JSONObject session = sessions.getJSONObject(sessionTimeIndex);
                JSONArray concurrentSessions = session.getJSONArray("concurrentSessions");

                // the concurrentsession is the actual event (containing the location, etc...
                for (int eventIndex = 0; eventIndex < concurrentSessions.length(); eventIndex++) {
                    JSONObject event = new JSONObject();
                    JSONObject concurrentSession = concurrentSessions.getJSONObject(eventIndex);
                    event.put("id", concurrentSession.getString("id"));
                    event.put("description", concurrentSession.getString("description"));
                    event.put("location", concurrentSession.getString("location"));
                    event.put("startDate", concurrentSession.getString("startDate"));
                    event.put("endDate", concurrentSession.getString("endDate"));
                    event.put("title", concurrentSession.getString("title"));
                    event.put("evaluationURL", concurrentSession.getString("evaluationURL"));
                    event.put("concurrentSessionId", sessionTimeIndex);
                    event.put("day", dayIndex);
                    eventsArray.put(event);
                }
            }
        }

        return eventsArray;
    }

    private void writeJsonToFile(JSONArray eventsArray) {
        // directly saves the response JSON into the android directory without parsing
        try {
            Writer output = null;
            File file = new File(getFilesDir(), agendaFileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(eventsArray.toString());
            output.close();
            Toast.makeText(getApplicationContext(), "Composition saved", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json imported to file: " + eventsArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJsonFromFile(String fileName) {
        // retrieves a file from the android directory as a string and prints it in the log
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( fileName ) ;
            InputStreamReader isr = new InputStreamReader( fIn ) ;
            BufferedReader buffreader = new BufferedReader( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        String answer = datax.toString();
        Log.i("jsonnnnnn", answer);
    }

    private void getDynamicJSONArray() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, agendaURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // PARSING; iterates through the layers of arrays and objects in the JSON to the core of the events
                        try {
                            JSONArray eventsArray = parseAgenda(response);
                            writeJsonToFile(eventsArray);
                            readJsonFromFile(agendaFileName);
                        } catch (JSONException error) {
                            error.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
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
            case R.id.nav_agenda:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AgendaHostFragment()).commit();
                break;
            case R.id.nav_norms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NormsFragment()).commit();
                break;
            case R.id.nav_speakers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpeakersFragment()).commit();
                break;
            case R.id.nav_cohorts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CohortsFragment()).commit();
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
            case R.id.nav_information:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InformationFragment()).commit();
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
