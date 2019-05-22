package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AgendaFragment extends Fragment {

    public AgendaFragment (){}



    //private static String[] MOBILE_MODELS = {"iPhone 6","Nexus 6","Moto G","HTC One","Galaxy S5","Sony Xperia Z2","Lumia 830","Galaxy Grand 2"};

    //int[] images = new int [] {R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter,R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter};

    public static AgendaFragment newInstance() {
        Bundle args = new Bundle();
        AgendaFragment fragment = new AgendaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.agenda, container, false);

        //added

        final String[] sessionname = {
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand",
                "Morning Plennary",
                "Professional Panels & Networking",
                "Arts & Media",
                "Skill Building Workshop",
                "AAAWC Presents: Femail Leadership in Workplace",
                "Building your Brand"
        };

        final String[] sessionlocation = {
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210",
                "Main Street Hall",
                "Vanderbilt Lounge",
                "Powell Hall Room 2",
                "Powell Hall Room 3",
                "Powell Hall Room 4",
                "Android Hall Room 1000",
                "Lean Hall 210"
        };

        final String[] starttime = {
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM",
                "10:00AM",
                "12:00PM",
                "2:00PM",
                "4:00PM",
                "6:00PM",
                "8:00PM",
                "10:00PM"
        };

        final String[] endtime = {
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM",
                "11:00AM",
                "1:00PM",
                "3:00PM",
                "5:00PM",
                "7:00PM",
                "9:00PM",
                "11:00PM"
        };



        AgendaListAdapter adapter = new AgendaListAdapter(getActivity(), sessionname, sessionlocation, starttime, endtime);
        ListView listView = (ListView) mainView.findViewById(R.id.listView_agenda);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity().getApplicationContext(),"ListView Clicked Go Ahead",Toast.LENGTH_LONG).show();


            }
        });

        listView.setAdapter(adapter);




        return mainView;
    }
}