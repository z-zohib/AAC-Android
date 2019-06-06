package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class SpeakersHostFragment extends Fragment {
    private static View summaryView;
    private FragmentTabHost speakerTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Bundle args = new Bundle();

        {

            speakerTabHost = new FragmentTabHost(getActivity());
            speakerTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment_container);





            //Keynote Tab Fragment
            TabHost.TabSpec keynoteTab = speakerTabHost.newTabSpec("Keynote");
            SpeakersFragment keynoteFragment = new SpeakersFragment();
            args.putString("Speakers", "First time");
            keynoteFragment.setArguments(args);
            keynoteTab.setIndicator("Keynote");
            speakerTabHost.addTab(keynoteTab,keynoteFragment.getClass(), args);

            //SEED Tab Fragment
            TabHost.TabSpec seedTab = speakerTabHost.newTabSpec("S.E.E.D. Talks");
            SpeakersFragment seedFragment = new SpeakersFragment();
            seedFragment.setArguments(args);
            seedTab.setIndicator("S.E.E.D. Talks");
            speakerTabHost.addTab(seedTab,seedFragment.getClass(), args);

            //Speakers Tab Fragment
            TabHost.TabSpec speakersTab = speakerTabHost.newTabSpec("Speakers");
            SpeakersFragment speakersFragment = new SpeakersFragment();
            speakersFragment.setArguments(args);
            speakersTab.setIndicator("Speakers");
            speakerTabHost.addTab(speakersTab,speakersFragment.getClass(), args);

            //Performers Tab Fragment
            TabHost.TabSpec performersTab = speakerTabHost.newTabSpec("Performers");
            SpeakersFragment performersFragment = new SpeakersFragment();
            performersFragment.setArguments(args);
            performersTab.setIndicator("Performers");
            speakerTabHost.addTab(performersTab,performersFragment.getClass(), args);


            speakerTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
                @Override
                public void onTabChanged(String tabId) {

                    int i = speakerTabHost.getCurrentTab();

                    if (i == 0) {
                        args.putString("Speakers", "KEYNOTE");
                    }
                    else if (i ==1) {
                        args.putString("Speakers", "SEED");

                    }
                    if (i ==2) {
                        args.putString("Speakers", "SPEAKERS");
                    }
                    else if (i ==3) {
                        args.putString("Speakers", "PERFORMERS");
                    }

                }
            });





            return speakerTabHost;
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        speakerTabHost = null;
    }
}