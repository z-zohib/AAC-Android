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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.net.Uri;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


import static com.aac.events.MainActivity.speakersFileName;

public class SessionDetailsFragment extends Fragment {

    private JSONArray speakersArr;
    protected ArrayList<Speaker> sessionSpeakers = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sessiondetails, container,false);
        view.setBackgroundResource(R.color.lists);

        Bundle args = this.getArguments();
        TextView textViewTitle = (TextView) view.findViewById(R.id.title_session);
        TextView textViewDescription = (TextView) view.findViewById(R.id.session_desc);
        TextView textViewTime = (TextView) view.findViewById(R.id.session_time);
        TextView textViewDate = (TextView) view.findViewById(R.id.session_date);
        TextView textViewLocation = (TextView) view.findViewById(R.id.session_location);

        String title = getArguments().getString("Title");
        String description = getArguments().getString("Description");
        String location = getArguments().getString("Location");
        String startTime = getArguments().getString("Start Date");
        String endTime = getArguments().getString("End Date");
        final String evalURL = getArguments().getString("Evaluation URL");
        Integer sessionID = getArguments().getInt("Session ID");
        Integer concurrentID = getArguments().getInt("Concurrent ID");
        Integer day = getArguments().getInt("Day");
        String speakID = getArguments().getString("Speaker ID");


        textViewTime.setText(startTime + " - " + endTime);

        //convert start date & end date string to int
        int starttime_integer = Integer.parseInt(startTime);
        int endtime_integer = Integer.parseInt(endTime);

        //convert epoch unix time to regular time stamp
        Date starttime_converted = new Date(starttime_integer * 1000L);
        Date endtime_converted = new Date(endtime_integer * 1000L);

        //format time hh:mm a
        String start_time = new java.text.SimpleDateFormat("h:mm a").format(starttime_converted);
        String end_time = new java.text.SimpleDateFormat("h:mm a").format(endtime_converted);
        String month_day = new java.text.SimpleDateFormat("EEE, MMM d").format(starttime_converted);

        textViewTime.setText(start_time + " - " + end_time);
        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewLocation.setText(location);
        textViewDate.setText(month_day);

        if (!evalURL.isEmpty()) {
            Button buttonSessionEval = (Button) view.findViewById(R.id.session_eval);
            buttonSessionEval.setVisibility(View.VISIBLE);
            buttonSessionEval.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(evalURL));
                    startActivity(intent);

                }
            });
        }

        //Pulling speakerIDs from agenda JSON and then looking for that ID in the people JSON and pushing it into a new array

        speakersArr = getSpeakersJsonArr();
        JSONArray speakerIDs;

        try {
            int session_speakerIDs;
            speakerIDs = new JSONArray(speakID);
            Log.d("SpeakerID", speakerIDs.toString());


            for (int p = 0; p < speakerIDs.length(); p++) {
                session_speakerIDs = Integer.parseInt(speakerIDs.getString(p));


                try {

                    for (int i = 0; i < speakersArr.length(); i++) {
                        JSONObject speaker = speakersArr.getJSONObject(i);


                        if (speaker.getInt("id") == session_speakerIDs){

                            sessionSpeakers.add(new Speaker(speaker));
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

        //Click of speaker item in session details navigates to person details

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonDetailsFragment persondetFrag = new PersonDetailsFragment();

                Bundle args = new Bundle();

                args.putInt("Person ID", sessionSpeakers.get(position).getId());
                args.putString("Image URL", sessionSpeakers.get(position).getImageURL());
                args.putString("Image Name", sessionSpeakers.get(position).getImageName());
                args.putString("Person Name", sessionSpeakers.get(position).getName());
                args.putString("Person Description", sessionSpeakers.get(position).getDescription());
                args.putString("Session IDs", sessionSpeakers.get(position).getSessionIDs());
                args.putString("Title", sessionSpeakers.get(position).getPeopleTitle());


                persondetFrag.setArguments(args);


                getFragmentManager().beginTransaction().replace(android.R.id.tabhost, persondetFrag).addToBackStack(null).commit();

            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        JSONArray speakersArr;
        sessionSpeakers = new ArrayList<Speaker>();
        String speakID = getArguments().getString("Speaker ID");



        Bundle args = this.getArguments();
        //Pulling speakerIDs from agenda JSON and then looking for that ID in the people JSON and pushing it into a new array

        speakersArr = getSpeakersJsonArr();
        JSONArray speakerIDs;

        try {
            int session_speakerIDs;
            speakerIDs = new JSONArray(speakID);
            Log.d("SpeakerID", speakerIDs.toString());


            for (int p = 0; p < speakerIDs.length(); p++) {
                session_speakerIDs = Integer.parseInt(speakerIDs.getString(p));


                try {

                    for (int i = 0; i < speakersArr.length(); i++) {
                        JSONObject speaker = speakersArr.getJSONObject(i);


                        if (speaker.getInt("id") == session_speakerIDs){

                            sessionSpeakers.add(new Speaker(speaker));
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

            int size = sessionSpeakers.size();

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


                view = getLayoutInflater().inflate(R.layout.person_item, null);
                view.setBackgroundResource(R.color.lists);


                TextView textViewName = (TextView) view.findViewById(R.id.item);
                ImageView imageViewPerson = (ImageView) view.findViewById(R.id.icon);


                if (position >= sessionSpeakers.size()) {
                    return view;
                }


                textViewName.setText(sessionSpeakers.get(position).getName());


                if (!sessionSpeakers.get(position).getImageName().isEmpty()) {

                    String imagename = sessionSpeakers.get(position).getImageName();
                    int person_image = getResources().getIdentifier(imagename, "drawable", getActivity().getPackageName());
                    imageViewPerson.setImageResource(person_image);

                } else {
                    Picasso.get().load(sessionSpeakers.get(position).getImageURL()).into(imageViewPerson);
                }



            return view;
        }
    }

    //Setting height of listview based on the number of items in listview

    public static void setListViewHeightBasedOnChildren (ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int expectedWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(expectedWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(expectedWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount()-1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private String getSpeakersJsonStr() {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = getContext().openFileInput( speakersFileName );
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

    private JSONArray getSpeakersJsonArr() {
        JSONArray speakers = null;

        try {
            speakers = (new JSONArray(getSpeakersJsonStr()));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return speakers;
    }


}
