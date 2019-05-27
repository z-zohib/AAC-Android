package com.aac.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.aac.events.MainActivity.agendaFileName;

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
        try {
            for (int i = 0; i < eventsArr.length(); i++) {
                JSONObject event = eventsArr.getJSONObject(i);

                // initializing sessionArrays
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) view.findViewById(R.id.friday_list);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount()  {
            return fridaySessions.size();
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
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.session_list_layout, null);

            TextView textViewTime = (TextView) view.findViewById(R.id.session_time);
            TextView textViewTitle = (TextView) view.findViewById(R.id.session_title);
            TextView textViewDescription = (TextView) view.findViewById(R.id.session_description);

            if (position >= fridaySessions.size()) {
                return view;
            }
            textViewTime.setText("11:00-\n\n12:00");
            //TODO: if the getTitle() string is greater then 25 characters, then cut off string
            textViewTitle.setText(fridaySessions.get(position).getTitle());
            textViewDescription.setText(fridaySessions.get(position).getLocation());

            return view;
        }
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

}