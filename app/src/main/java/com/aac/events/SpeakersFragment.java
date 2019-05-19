package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

public class SpeakersFragment extends Fragment {
    private static View summaryView;
    private FragmentTabHost mTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        {

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
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        mTabHost = null;
    }
}