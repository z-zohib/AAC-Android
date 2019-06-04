package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.aac.events.MainActivity.agendaFileName;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment1);

        speakersArr = getSpeakersJsonArr();

        // initializing speakers Array
        try {
            for (int i = 0; i < speakersArr.length(); i++) {
                JSONObject speaker = speakersArr.getJSONObject(i);

                // initializing sessionArrays
                if (speaker.getString("peopleTitle").equalsIgnoreCase("Keynote Speaker")) {
                    keynoteArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("S.E.E.D. Talks")) {
                    seedArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("Speaker / Facilitator")) {
                    facilitatorArray.add(new Speaker(speaker));
                } else if (speaker.getString("peopleTitle").equalsIgnoreCase("Performer")) {
                    performerArray.add(new Speaker(speaker));
                } else {
                    Log.i(TAG, "Event Day initialized and categorized incorrectly");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mTabHost.addTab(mTabHost.newTabSpec("Keynote").setIndicator("Keynote"),
                KeynoteTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("S.E.E.D. Talks").setIndicator("S.E.E.D. Talks"),
                SeedTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Speakers").setIndicator("Speakers"),
                SpeakerTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Performers").setIndicator("Performers"),
                PerformerTabFragment.class, null);

        return mTabHost;
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

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        mTabHost = null;
    }
}