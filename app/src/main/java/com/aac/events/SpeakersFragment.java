package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import com.squareup.picasso.Picasso;


import static com.aac.events.MainActivity.speakersFileName;

public class SpeakersFragment extends Fragment {
    private static View summaryView;
    private FragmentTabHost mTabHost;
    private JSONArray speakersArr;
    private static final String TAG = MainActivity.class.getName();

    protected ArrayList<Speaker> keynoteArray = new ArrayList<>();
    protected ArrayList<Speaker> seedArray = new ArrayList<>();
    protected ArrayList<Speaker> facilitatorArray = new ArrayList<>();
    protected ArrayList<Speaker> performerArray = new ArrayList<>();


    public String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.speaker, container, false);
        view.setBackgroundResource(R.color.lists);

        Bundle args = this.getArguments();
        title = getArguments().getString("Speakers");

        speakersArr = getSpeakersJsonArr();


        // initializing speakers Array
        try {
            for (int i = 0; i < speakersArr.length(); i++) {
                JSONObject speaker = speakersArr.getJSONObject(i);

                // initializing peopleArrays
                if (speaker.getString("peopleTitle").equalsIgnoreCase("Keynote Speaker")) {
                    keynoteArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("S.E.E.D. Talks")) {
                    seedArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("Speaker / Facilitator")) {
                    facilitatorArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("Performer")) {
                    performerArray.add(new Speaker(speaker));
                } else {
                    Log.i(TAG, "Speakers initialized and categorized incorrectly");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) view.findViewById(R.id.speaker_list);
        SpeakersFragment.CustomAdapter customAdapter = new SpeakersFragment.CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonDetailsFragment persondetFrag = new PersonDetailsFragment();

                Bundle args = new Bundle();

                if (title == "First time" || title == "KEYNOTE") {
                    args.putInt("Person ID", keynoteArray.get(position).getId());
                    args.putString("Image URL", keynoteArray.get(position).getImageURL());
                    args.putString("Image Name", keynoteArray.get(position).getImageName());
                    args.putString("Person Name", keynoteArray.get(position).getName());
                    args.putString("Person Description", keynoteArray.get(position).getDescription());
                    args.putString("Session IDs", keynoteArray.get(position).getSessionIDs());
                    args.putString("Title", keynoteArray.get(position).getPeopleTitle());
                } else if (title == "SEED") {
                    args.putInt("Person ID", seedArray.get(position).getId());
                    args.putString("Image URL", seedArray.get(position).getImageURL());
                    args.putString("Image Name", seedArray.get(position).getImageName());
                    args.putString("Person Name", seedArray.get(position).getName());
                    args.putString("Person Description", seedArray.get(position).getDescription());
                    args.putString("Session IDs", seedArray.get(position).getSessionIDs());
                    args.putString("Title", seedArray.get(position).getPeopleTitle());
                } else if (title == "SPEAKERS"){
                    args.putInt("Person ID", facilitatorArray.get(position).getId());
                    args.putString("Image URL", facilitatorArray.get(position).getImageURL());
                    args.putString("Image Name", facilitatorArray.get(position).getImageName());
                    args.putString("Person Name", facilitatorArray.get(position).getName());
                    args.putString("Person Description", facilitatorArray.get(position).getDescription());
                    args.putString("Session IDs", facilitatorArray.get(position).getSessionIDs());
                    args.putString("Title", facilitatorArray.get(position).getPeopleTitle());
                }
                else if (title == "PERFORMERS"){
                    args.putInt("Person ID", performerArray.get(position).getId());
                    args.putString("Image URL", performerArray.get(position).getImageURL());
                    args.putString("Image Name", performerArray.get(position).getImageName());
                    args.putString("Person Name", performerArray.get(position).getName());
                    args.putString("Person Description", performerArray.get(position).getDescription());
                    args.putString("Session IDs", performerArray.get(position).getSessionIDs());
                    args.putString("Title", performerArray.get(position).getPeopleTitle());
                }

                persondetFrag.setArguments(args);


                getParentFragment().getFragmentManager().beginTransaction().replace(android.R.id.tabhost, persondetFrag).addToBackStack(null).commit();
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        keynoteArray = new ArrayList<Speaker>();
        seedArray = new ArrayList<Speaker>();
        facilitatorArray = new ArrayList<Speaker>();
        performerArray = new ArrayList<Speaker>();

        Bundle args = this.getArguments();
        title = getArguments().getString("Speakers");


        speakersArr = getSpeakersJsonArr();


        // initializing speakers Array
        try {
            for (int i = 0; i < speakersArr.length(); i++) {
                JSONObject speaker = speakersArr.getJSONObject(i);


                // initializing peopleArrays

                if (speaker.getString("peopleTitle").equalsIgnoreCase("Keynote Speaker")) {
                    keynoteArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("S.E.E.D. Talks")) {
                    seedArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("Speaker / Facilitator")) {
                    facilitatorArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("Performer")) {
                    performerArray.add(new Speaker(speaker));
                } else {
                    Log.i(TAG, "Speakers initialized and categorized incorrectly");
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

            if (title == "First time" || title == "KEYNOTE"){
                size = keynoteArray.size();
            }
            else if (title == "SEED"){
                size = seedArray.size();
            }
            else if (title == "SPEAKERS"){
                size = facilitatorArray.size();
            }
            else if (title == "PERFORMERS"){
                size = performerArray.size();
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
            view = getLayoutInflater().inflate(R.layout.person_item, null);
            view.setBackgroundResource(R.color.lists);


            TextView textViewName = (TextView) view.findViewById(R.id.item);
            ImageView imageViewPerson = (ImageView) view.findViewById(R.id.icon);


            if (title == "First time" || title == "KEYNOTE"){
                if (position >= keynoteArray.size()) {
                    return view;
                }


                textViewName.setText(keynoteArray.get(position).getName());


                if (!keynoteArray.get(position).getImageName().isEmpty()){

                    String imagename = keynoteArray.get(position).getImageName();
                    int person_image = getResources().getIdentifier(imagename, "drawable", getActivity().getPackageName());
                    imageViewPerson.setImageResource(person_image);

                }
                else {
                    Picasso.get().load(keynoteArray.get(position).getImageURL()).into(imageViewPerson);
                }

            }
            else if (title == "SEED"){
                if (position >= seedArray.size()) {
                    return view;
                }

                textViewName.setText(seedArray.get(position).getName());

                if (!seedArray.get(position).getImageName().isEmpty()){

                    String imagename = seedArray.get(position).getImageName();
                    int person_image = getResources().getIdentifier(imagename, "drawable", getActivity().getPackageName());
                    imageViewPerson.setImageResource(person_image);

                }
                else {
                    Picasso.get().load(seedArray.get(position).getImageURL()).into(imageViewPerson);
                }

            }
            else if (title == "SPEAKERS"){
                if (position >= facilitatorArray.size()) {
                    return view;
                }

                textViewName.setText(facilitatorArray.get(position).getName());

                if (!facilitatorArray.get(position).getImageName().isEmpty()){

                    String imagename = facilitatorArray.get(position).getImageName();
                    int person_image = getResources().getIdentifier(imagename, "drawable", getActivity().getPackageName());
                    imageViewPerson.setImageResource(person_image);

                }
                else {
                    Picasso.get().load(facilitatorArray.get(position).getImageURL()).into(imageViewPerson);
                }

            }
            else if (title == "PERFORMERS"){
                if (position >= performerArray.size()) {
                    return view;
                }

                textViewName.setText(performerArray.get(position).getName());

                if (!performerArray.get(position).getImageName().isEmpty()){

                    String imagename = performerArray.get(position).getImageName();
                    int person_image = getResources().getIdentifier(imagename, "drawable", getActivity().getPackageName());
                    imageViewPerson.setImageResource(person_image);

                }
                else {
                    Picasso.get().load(performerArray.get(position).getImageURL()).into(imageViewPerson);
                }
            }

            return view;
        }
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