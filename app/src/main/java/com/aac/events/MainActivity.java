package com.aac.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;

    private static final String TAG = MainActivity.class.getName();
    protected final static String agendaURL = "https://dl.dropboxusercontent.com/s/24ria513yvg47sb/AgendaData.json?dl=0";
    protected final static String speakersURL = "https://dl.dropboxusercontent.com/s/amz7xnk0puf26k4/PeopleList.json?dl=0";
    protected final static String agendaFileName = "agendaData.json";
    protected final static String speakersFileName = "speakersData.json";
    protected final static String evalLinkFileName = "evalLink.txt";
    protected String conferenceEvalLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up dynamic content through JSON retrieval
        mRequestQueue = Volley.newRequestQueue(this);
        getRequestSpeakers();
        getRequestAgenda();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Adding menu icons to tool bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        //OneSignal.startInit(this)
        // .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
        // .unsubscribeWhenNotificationsAreDisabled(true)
        // .init();
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

        this.conferenceEvalLink = response.getString("conferenceEvalLink");

        // sessiondays length should be 3 (0 = fri, 1 = sat, 2 = sun)
        for (int dayIndex = 0; dayIndex < sessionDays.length(); dayIndex++) {
            JSONObject sessionDay = sessionDays.getJSONObject(dayIndex);
            JSONArray sessions = sessionDay.getJSONArray("sessions");

            // sessions length is number of time slots (0 = 12pm-1:15pm, 1 = 1:30pm-3pm, etc...)
            for (int sessionTimeIndex = 0; sessionTimeIndex < sessions.length(); sessionTimeIndex++) {
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
                    event.put("speakerIDs", concurrentSession.getJSONArray("speakerIDs"));
                    eventsArray.put(event);
                }
            }
        }

        return eventsArray;
    }

    private JSONArray parseSpeakers(JSONArray response) throws JSONException {
        JSONArray speakersArray = new JSONArray();

        // should be 4 tabs within response length
        for (int tabIndex = 0; tabIndex < response.length(); tabIndex++) {
            Log.i(TAG, "ENTERED: tabs: " + tabIndex);
            JSONObject tab = response.getJSONObject(tabIndex);
            String speakerTitle = tab.getString("peopleTitle");
            JSONArray pplArray = tab.getJSONArray("peopleArray");

            for (int pplArrayIndex = 0; pplArrayIndex < pplArray.length(); pplArrayIndex++) {
                Log.i(TAG, "speaker: " + pplArrayIndex);
                JSONObject newSpeaker = new JSONObject();
                JSONObject speaker = pplArray.getJSONObject(pplArrayIndex);

                newSpeaker.put("id", speaker.getInt("speakerID"));
                newSpeaker.put("peopleTitle", speakerTitle);
                newSpeaker.put("imageURL", speaker.getString("imageURL"));
                newSpeaker.put("imageName", speaker.getString("imageName"));
                newSpeaker.put("name", speaker.getString("name"));
                newSpeaker.put("personDescription", speaker.getString("personDescription"));
                newSpeaker.put("sessionIDs", speaker.getJSONArray("sessionIDs"));

                speakersArray.put(newSpeaker);
            }
        }

        return speakersArray;
    }

    private void writeAgendaToFile(JSONArray eventsArray) {
        // directly saves the response JSON into the android directory without parsing
        try {
            Writer output = null;
            File file = new File(getFilesDir(), agendaFileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(eventsArray.toString());
            output.close();
            Toast.makeText(getApplicationContext(), "Agenda loaded", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json imported to file: " + eventsArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeConfEvalLinkToFile(String link) {
        // directly saves the response JSON into the android directory without parsing
        try {
            Writer output = null;
            File file = new File(getFilesDir(), evalLinkFileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(link);
            output.close();
            Toast.makeText(getApplicationContext(), "Eval Link loaded", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json imported to file: " + link);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSpeakersToFile(JSONArray speakersArray) {
        // directly saves the response JSON into the android directory without parsing
        try {
            Writer output = null;
            File file = new File(getFilesDir(), speakersFileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(speakersArray.toString());
            output.close();
            Toast.makeText(getApplicationContext(), "Speakers loaded", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json imported to file: " + speakersArray.toString());
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

    private void getRequestAgenda() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, agendaURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // PARSING; iterates through the layers of arrays and objects in the JSON to the core of the events
                        try {
                            JSONArray eventsArray = parseAgenda(response);
                            writeAgendaToFile(eventsArray);
                            readJsonFromFile(agendaFileName);
                            writeConfEvalLinkToFile(conferenceEvalLink);
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

    private void getRequestSpeakers() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, speakersURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // PARSING; iterates through the layers of arrays and objects in the JSON to the core of the events
                        try {
                            writeSpeakersToFile((JSONArray) parseSpeakers(response));
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_planning_team:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlanningTeamFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FaqFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_donate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DonateFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_agenda:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AgendaHostFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_norms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NormsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_speakers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpeakersHostFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_cohorts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CohortsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_aacare:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AacareFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_sponsor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SponsorFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_aaconnect:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AaconectFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_information:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InformationFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_confeval:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConferenceEvalFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_newsletter:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsletterFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_contact_us:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "afghanamericanconference@gmail.com" });
                startActivity(Intent.createChooser(intent, ""));
                break;
            case R.id.nav_credits:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreditsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_privacypolicy:
                Intent intent_pp = new Intent();
                intent_pp.setAction(Intent.ACTION_VIEW);
                intent_pp.addCategory(Intent.CATEGORY_BROWSABLE);
                intent_pp.setData(Uri.parse("https://www.afghanamericanconference.org/mobile-app-privacy-policy"));
                startActivity(intent_pp);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
