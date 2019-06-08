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
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import static com.aac.events.MainActivity.agendaFileName;

public class AgendaFragment extends Fragment {
    private JSONArray eventsArr;
    private static final String TAG = MainActivity.class.getName();

    protected ArrayList<Event> fridaySessions = new ArrayList<>();
    protected ArrayList<Event> saturdaySessions = new ArrayList<>();
    protected ArrayList<Event> sundaySessions = new ArrayList<>();

    protected HashMap<Integer, Event> sessionsMap = new HashMap<>();

    public String title;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.norms, container, false);
        View view = inflater.inflate(R.layout.agenda, container, false);
        view.setBackgroundResource(R.color.lists);

        Bundle args = this.getArguments();
        title = getArguments().getString("Sessions");
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
                sessionsMap.put(event.getInt("id"), new Event(event));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // onclick listener that opens up a fragment that populates data dynamically
        //ListView listView = (ListView) view.findViewById(R.id.friday_list);
        ListView listView = (ListView) view.findViewById(R.id.session_list);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SessionDetailsFragment sessiondetFrag = new SessionDetailsFragment();

                Bundle args = new Bundle();

                if (title == "First time" || title == "FRIDAY SESSIONS") {
                    args.putString("Title", fridaySessions.get(position).getTitle());
                    args.putString("Start Date", fridaySessions.get(position).getStartDate());
                    args.putString("End Date", fridaySessions.get(position).getEndDate());
                    args.putString("Location", fridaySessions.get(position).getLocation());
                    args.putString("Description", fridaySessions.get(position).getDescription());
                    args.putString("Evaluation URL", fridaySessions.get(position).getEvaluationURL());
                    args.putInt("Concurrent ID", fridaySessions.get(position).getConcurrentSessionId());
                    args.putInt("Session ID", fridaySessions.get(position).getId());
                    args.putInt("Day", fridaySessions.get(position).getDay());
                } else if (title == "SATURDAY SESSIONS") {
                    args.putString("Title", saturdaySessions.get(position).getTitle());
                    args.putString("Start Date", saturdaySessions.get(position).getStartDate());
                    args.putString("End Date", saturdaySessions.get(position).getEndDate());
                    args.putString("Location", saturdaySessions.get(position).getLocation());
                    args.putString("Description", saturdaySessions.get(position).getDescription());
                    args.putString("Evaluation URL", saturdaySessions.get(position).getEvaluationURL());
                    args.putInt("Concurrent ID", saturdaySessions.get(position).getConcurrentSessionId());
                    args.putInt("Session ID", saturdaySessions.get(position).getId());
                    args.putInt("Day", saturdaySessions.get(position).getDay());
                } else if (title == "Sunday SESSIONS"){
                    args.putString("Title", sundaySessions.get(position).getTitle());
                    args.putString("Start Date", sundaySessions.get(position).getStartDate());
                    args.putString("End Date", sundaySessions.get(position).getEndDate());
                    args.putString("Location", sundaySessions.get(position).getLocation());
                    args.putString("Description", sundaySessions.get(position).getDescription());
                    args.putString("Evaluation URL", sundaySessions.get(position).getEvaluationURL());
                    args.putInt("Concurrent ID", sundaySessions.get(position).getConcurrentSessionId());
                    args.putInt("Session ID", sundaySessions.get(position).getId());
                    args.putInt("Day", sundaySessions.get(position).getDay());
                }

                sessiondetFrag.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, sessiondetFrag).addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        fridaySessions = new ArrayList<Event>();
        saturdaySessions = new ArrayList<Event>();
        sundaySessions = new ArrayList<Event>();

        Bundle args = this.getArguments();
        title = getArguments().getString("Sessions");
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

    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount()  {

            int size = 0;

            if (title == "First time" || title == "FRIDAY SESSIONS"){
                size = fridaySessions.size();
            }
            else if (title == "SATURDAY SESSIONS"){
                size = saturdaySessions.size();
            }
            else if (title == "Sunday SESSIONS"){
                size = sundaySessions.size();
            }
            return size;
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

            TextView textViewStartTime = (TextView) view.findViewById(R.id.session_starttime);
            TextView textViewEndTime = (TextView) view.findViewById(R.id.session_endtime);
            TextView textViewTitle = (TextView) view.findViewById(R.id.session_title);
            TextView textViewDescription = (TextView) view.findViewById(R.id.session_description);

            if (title == "First time" || title == "FRIDAY SESSIONS"){
                if (position >= fridaySessions.size()) {
                    return view;
                }

                //set string start date & end date
                String starttime_unformatted = fridaySessions.get(position).getStartDate();
                String endtime_unformatted = fridaySessions.get(position).getEndDate();

                //convert start date & end date string to int
                int starttime_integer = Integer.parseInt(starttime_unformatted);
                int endtime_integer = Integer.parseInt(endtime_unformatted);

                //convert epoch unix time to regular time stamp
                Date starttime_converted = new Date(starttime_integer * 1000L);
                Date endtime_converted = new Date(endtime_integer * 1000L);

                //format time hh:mm a
                String start_time = new java.text.SimpleDateFormat("h:mm a").format(starttime_converted);
                String end_time = new java.text.SimpleDateFormat("h:mm a").format(endtime_converted);



                textViewStartTime.setText(start_time);
                textViewEndTime.setText(end_time);
                textViewTitle.setText(fridaySessions.get(position).getTitle());
                textViewDescription.setText(fridaySessions.get(position).getLocation());
            }
            else if (title == "SATURDAY SESSIONS"){
                if (position >= saturdaySessions.size()) {
                    return view;
                }

                //set string start date & end date
                String starttime_unformatted = saturdaySessions.get(position).getStartDate();
                String endtime_unformatted = saturdaySessions.get(position).getEndDate();

                //convert start date & end date string to int
                int starttime_integer = Integer.parseInt(starttime_unformatted);
                int endtime_integer = Integer.parseInt(endtime_unformatted);

                //convert epoch unix time to regular time stamp
                Date starttime_converted = new Date(starttime_integer * 1000L);
                Date endtime_converted = new Date(endtime_integer * 1000L);

                //format time hh:mm a
                String start_time = new java.text.SimpleDateFormat("h:mm a").format(starttime_converted);
                String end_time = new java.text.SimpleDateFormat("h:mm a").format(endtime_converted);


                textViewStartTime.setText(start_time);
                textViewEndTime.setText(end_time);
                textViewTitle.setText(saturdaySessions.get(position).getTitle());
                textViewDescription.setText(saturdaySessions.get(position).getLocation());
            }
            else if (title == "Sunday SESSIONS"){
                if (position >= sundaySessions.size()) {
                    return view;
                }

                //set string start date & end date
                String starttime_unformatted = sundaySessions.get(position).getStartDate();
                String endtime_unformatted = sundaySessions.get(position).getEndDate();

                //convert start date & end date string to int
                int starttime_integer = Integer.parseInt(starttime_unformatted);
                int endtime_integer = Integer.parseInt(endtime_unformatted);

                //convert epoch unix time to regular time stamp
                Date starttime_converted = new Date(starttime_integer * 1000L);
                Date endtime_converted = new Date(endtime_integer * 1000L);

                //format time hh:mm a
                String start_time = new java.text.SimpleDateFormat("h:mm a").format(starttime_converted);
                String end_time = new java.text.SimpleDateFormat("h:mm a").format(endtime_converted);


                textViewStartTime.setText(start_time);
                textViewEndTime.setText(end_time);
                textViewTitle.setText(sundaySessions.get(position).getTitle());
                textViewDescription.setText(sundaySessions.get(position).getLocation());
            }



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