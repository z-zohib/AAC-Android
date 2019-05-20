package com.aac.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
import java.util.ArrayList;

import static com.aac.events.MainActivity.agendaFileName;
import static com.aac.events.MainActivity.agendaURL;

public class AgendaFragment extends Fragment {
    private JSONArray eventsArr;
    private static final String TAG = MainActivity.class.getName();

    protected ArrayList<Event> fridaySessions = new ArrayList<>();
    protected ArrayList<Event> saturdaySessions = new ArrayList<>();
    protected ArrayList<Event> sundaySessions = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.norms, container, false);
        View view = inflater.inflate(R.layout.agenda, container, false);
        eventsArr = getAgendaJsonArr();
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_agenda);
        TextView textView = null;
        try {
            for (int i = 0; i < eventsArr.length(); i++) {
                JSONObject event = eventsArr.getJSONObject(i);

                if (event.getInt("day") == 0) {
                    fridaySessions.add(new Event(event));
                } else if (event.getInt("day") == 1) {
                    saturdaySessions.add(new Event(event));
                } else if (event.getInt("day") == 2) {
                    sundaySessions.add(new Event(event));
                } else {
                    Log.i(TAG, "Event Day initialized and categorized incorrectly");
                }
            }

            textView = new TextView(getActivity());
            textView.setText("FRIDAY\n");
            linearLayout.addView(textView);
            for (Event session: fridaySessions) {
                textView = new TextView(getActivity());
                textView.setText(session.getTitle());
                linearLayout.addView(textView);
            }

            textView = new TextView(getActivity());
            textView.setText("\nSATURDAY\n");
            linearLayout.addView(textView);
            for (Event session: saturdaySessions) {
                textView = new TextView(getActivity());
                textView.setText(session.getTitle());
                linearLayout.addView(textView);
            }

            textView = new TextView(getActivity());
            textView.setText("\nSUNDAY\n");
            linearLayout.addView(textView);
            for (Event session: sundaySessions) {
                textView = new TextView(getActivity());
                textView.setText(session.getTitle());
                linearLayout.addView(textView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // commented below was work on two scrollview inside one linear layout or viceversa
        /*LinearLayout linearOrg = (LinearLayout) view.findViewById(R.id.linearPTScrollView);
        LinearLayout innerLinear = (LinearLayout) view.findViewById(R.id.planningTeamLV_organizingCommittee);

        for (String orgName: orgNameArray) {
            TextView itemName = new TextView(getActivity());
            itemName.setText(orgName);
            innerLinear.addView(itemName);
        }

        for (String subName: subNameArray) {
            innerLinear = (LinearLayout) view.findViewById(R.id.planningTeamLV_subCommittee);
            TextView itemName = new TextView(getActivity());
            itemName.setText(subName);
            innerLinear.addView(itemName);
        }

        ((ViewGroup)innerLinear.getParent()).removeView(innerLinear);
        linearOrg.addView(innerLinear);*/

        return view;
    }

    private String getAgendaJsonStr() {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = getContext().openFileInput( agendaFileName ) ;
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
        return datax.toString();
    }

    private JSONArray getAgendaJsonArr() {
        JSONArray events = null;

        try {
            events = (new JSONArray(getAgendaJsonStr()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount()  {
            return eventsArr.length();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}