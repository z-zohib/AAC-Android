package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.aac.events.MainActivity.agendaFileName;

public class SpeakersFragment extends Fragment {
    private static View summaryView;
    private FragmentTabHost mTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment1);

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

    private String getAgendaJsonStr() {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = getContext().openFileInput( agendaFileName );
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

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        mTabHost = null;
    }
}