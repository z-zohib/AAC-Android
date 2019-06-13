package com.aac.events;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import android.widget.ListAdapter;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;

import static com.aac.events.MainActivity.agendaFileName;


public class PersonDetailsFragment extends Fragment {

    private JSONArray eventsArr;
    protected ArrayList<Event> speakerSessions = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.speakerdetails, container,false);
        view.setBackgroundResource(R.color.lists);

        Bundle args = this.getArguments();
        TextView textViewPersonName = (TextView) view.findViewById(R.id.person_name);
        TextView textViewPersonTitle = (TextView) view.findViewById(R.id.persontitle);
        TextView textViewPersonBio = (TextView) view.findViewById(R.id.person_bio);
        ImageView imageViewPerson = (ImageView) view.findViewById(R.id.personimage);


        Integer personID = getArguments().getInt("Person ID");
        String imageURL = getArguments().getString("Image URL");
        String imageName = getArguments().getString("Image Name");
        String personName = getArguments().getString("Person Name");
        String personBio = getArguments().getString("Person Description");
        String sids = getArguments().getString("Session IDs");
        String personTitle = getArguments().getString("Title");

        if (!imageName.isEmpty()) {
            int person_image = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
            imageViewPerson.setImageResource(person_image);

        }
        else {
            Picasso.get().load(imageURL).into(imageViewPerson);
        }

        textViewPersonName.setText(personName);
        textViewPersonTitle.setText(personTitle);
        textViewPersonBio.setText(personBio);



        //Pulling sessionIDs from people JSON and then looking for that ID in the agenda JSON and pushing it into a new array
        eventsArr = getAgendaJsonArr();
        JSONArray sessionIDs;

        try {
            int speaker_sessionIDs;
            sessionIDs = new JSONArray(sids);

            for (int p = 0; p < sessionIDs.length(); p++) {
                speaker_sessionIDs = Integer.parseInt(sessionIDs.getString(p));


                try {

                    for (int i = 0; i < eventsArr.length(); i++) {
                        JSONObject event = eventsArr.getJSONObject(i);

                        if (event.getInt("id") == speaker_sessionIDs){

                            speakerSessions.add(new Event(event));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ListView listView = (ListView) view.findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        setListViewHeightBasedOnChildren(listView);

        //Click of agenda item in person details navigates to session details

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SessionDetailsFragment sessiondetFrag = new SessionDetailsFragment();

                Bundle args = new Bundle();

                args.putString("Title", speakerSessions.get(position).getTitle());
                args.putString("Start Date", speakerSessions.get(position).getStartDate());
                args.putString("End Date", speakerSessions.get(position).getEndDate());
                args.putString("Location", speakerSessions.get(position).getLocation());
                args.putString("Description", speakerSessions.get(position).getDescription());
                args.putString("Evaluation URL", speakerSessions.get(position).getEvaluationURL());
                args.putInt("Concurrent ID", speakerSessions.get(position).getConcurrentSessionId());
                args.putInt("Session ID", speakerSessions.get(position).getId());
                args.putInt("Day", speakerSessions.get(position).getDay());
                args.putString("Speaker ID", speakerSessions.get(position).getSpeakerIDs());


                sessiondetFrag.setArguments(args);
                getFragmentManager().beginTransaction().replace(android.R.id.tabhost, sessiondetFrag).addToBackStack(null).commit();
            }
        });




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        JSONArray eventsArr;
        speakerSessions = new ArrayList<Event>();


        Bundle args = this.getArguments();
        //Pulling sessionIDs from people JSON and then looking for that ID in the agenda JSON and pushing it into a new array
        eventsArr = getAgendaJsonArr();
        JSONArray sessionIDs;
        String sids = getArguments().getString("Session IDs");

        try {
            int speaker_sessionIDs;
            sessionIDs = new JSONArray(sids);

            for (int p = 0; p < sessionIDs.length(); p++) {
                speaker_sessionIDs = Integer.parseInt(sessionIDs.getString(p));


                try {

                    for (int i = 0; i < eventsArr.length(); i++) {
                        JSONObject event = eventsArr.getJSONObject(i);

                        if (event.getInt("id") == speaker_sessionIDs){

                            speakerSessions.add(new Event(event));
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount()  {

            int size = speakerSessions.size();

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


                view = getLayoutInflater().inflate(R.layout.persondet_session, null);

                TextView textViewStartTime = (TextView) view.findViewById(R.id.session_starttime);
                TextView textViewEndTime = (TextView) view.findViewById(R.id.session_endtime);
                TextView textViewTitle = (TextView) view.findViewById(R.id.session_title);
                TextView textViewDescription = (TextView) view.findViewById(R.id.session_description);
                TextView textViewDay = (TextView) view.findViewById(R.id.day);

                if (position >= speakerSessions.size()) {
                    return view;
                }

                //set string start date & end date
                String starttime_unformatted = speakerSessions.get(position).getStartDate();
                String endtime_unformatted = speakerSessions.get(position).getEndDate();

                //convert start date & end date string to int
                int starttime_integer = Integer.parseInt(starttime_unformatted);
                int endtime_integer = Integer.parseInt(endtime_unformatted);

                //convert epoch unix time to regular time stamp
                Date starttime_converted = new Date(starttime_integer * 1000L);
                Date endtime_converted = new Date(endtime_integer * 1000L);

                //format time hh:mm a
                String start_time = new java.text.SimpleDateFormat("h:mm a").format(starttime_converted);
                String end_time = new java.text.SimpleDateFormat("h:mm a").format(endtime_converted);

                if (speakerSessions.get(position).getDay()==0){
                    textViewDay.setText("Friday");
                }
                else if (speakerSessions.get(position).getDay()==1){
                    textViewDay.setText("Saturday");
                }
                else if (speakerSessions.get(position).getDay()==2){
                    textViewDay.setText("Sunday");
                }


                textViewStartTime.setText(start_time);
                textViewEndTime.setText(end_time);
                textViewTitle.setText(speakerSessions.get(position).getTitle());
                textViewDescription.setText(speakerSessions.get(position).getLocation());



            return view;
        }
    }

    //Setting height of listview based on the number of items in listview
    public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int expectedWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
                MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(expectedWidth,
                    LayoutParams.WRAP_CONTENT));

            view.measure(expectedWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount()-1));

        listView.setLayoutParams(params);
        listView.requestLayout();
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
