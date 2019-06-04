package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.support.v4.app.Fragment;
import android.widget.TabHost;



public class AgendaHostFragment extends Fragment {
    private static View summaryView;
    private FragmentTabHost agendaTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Bundle args = new Bundle();

        {

            agendaTabHost = new FragmentTabHost(getActivity());
            agendaTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment_container);





            //Friday Agenda Tab Fragment
            TabHost.TabSpec fridayTab = agendaTabHost.newTabSpec("Friday");
            AgendaFragment fridayFragment = new AgendaFragment();
            args.putString("Sessions", "First time");
            fridayFragment.setArguments(args);
            fridayTab.setIndicator("Friday");
            agendaTabHost.addTab(fridayTab,fridayFragment.getClass(), args);

            //Saturday Agenda Tab Fragment
            TabHost.TabSpec saturdayTab = agendaTabHost.newTabSpec("Saturday");
            AgendaFragment saturdayFragment = new AgendaFragment();
            saturdayFragment.setArguments(args);
            saturdayTab.setIndicator("Saturday");
            agendaTabHost.addTab(saturdayTab,saturdayFragment.getClass(), args);

            //Sunday Agenda Tab Fragment
            TabHost.TabSpec sundayTab = agendaTabHost.newTabSpec("Sunday");
            AgendaFragment sundayFragment = new AgendaFragment();
            sundayFragment.setArguments(args);
            sundayTab.setIndicator("Sunday");
            agendaTabHost.addTab(sundayTab,sundayFragment.getClass(), args);


            agendaTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
                @Override
                public void onTabChanged(String tabId) {

                    int i = agendaTabHost.getCurrentTab();

                    if (i == 0) {
                        args.putString("Sessions", "FRIDAY SESSIONS");
                    }
                    else if (i ==1) {
                        args.putString("Sessions", "SATURDAY SESSIONS");

                    }
                    else if (i ==2) {
                        args.putString("Sessions", "Sunday SESSIONS");
                    }

                }
            });





            return agendaTabHost;
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        agendaTabHost = null;
    }
}