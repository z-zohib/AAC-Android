package com.aac.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

public class SessionDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sessiondetails, container,false);

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
        String evalURL = getArguments().getString("Evaluation URL");
        Integer sessionID = getArguments().getInt("Evaluation URL");
        Integer concurrentID = getArguments().getInt("Concurrent ID");
        Integer day = getArguments().getInt("Day");




        textViewTime.setText(startTime + " - " + endTime);
        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewLocation.setText(location);


        return view;




    }
}
