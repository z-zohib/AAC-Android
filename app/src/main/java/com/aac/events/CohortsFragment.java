package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

public class CohortsFragment extends Fragment {

    public CohortsFragment (){}



    //private static String[] MOBILE_MODELS = {"iPhone 6","Nexus 6","Moto G","HTC One","Galaxy S5","Sony Xperia Z2","Lumia 830","Galaxy Grand 2"};

    //int[] images = new int [] {R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter,R.drawable.aac_newsletter, R.drawable.aac_newsletter, R.drawable.aac_newsletter};

    public static CohortsFragment newInstance() {
        Bundle args = new Bundle();
        CohortsFragment fragment = new CohortsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.cohorts, container, false);

        //added

        final String[] itemname = {
                "Cohort1",
                "Sara Noorzay",
                "Sara Noorzay",
                "Cohort 2",
                "Sara Noorzay",
                "Sara Noorzay",
                "Cohort 3",
                "Sara Noorzay",
                "Sara Noorzay",
                "Cohort 4",
                "Sara Noorzay",
                "Sara Noorzay",
                "Cohort 5",
                "Sara Noorzay",
                "Sara Noorzay"
        };

        Integer[] imgid = {
                R.drawable.whiteimg,
                R.drawable.sara,
                R.drawable.sara,
                R.drawable.whiteimg,
                R.drawable.sara,
                R.drawable.sara,
                R.drawable.whiteimg,
                R.drawable.sara,
                R.drawable.sara,
                R.drawable.whiteimg,
                R.drawable.sara,
                R.drawable.sara,
                R.drawable.whiteimg,
                R.drawable.sara,
                R.drawable.sara
        };

        CohortListAdapter adapter = new CohortListAdapter(getActivity(), itemname, imgid);
        ListView listView = (ListView) mainView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // listView.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.listview_activity,MOBILE_MODELS));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

        return mainView;
    }
}