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
import android.view.View.OnClickListener;
import android.content.Intent;
import android.net.Uri;

import java.util.Date;

public class SessionDetailsFragment extends Fragment {
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
        Integer sessionID = getArguments().getInt("Evaluation URL");
        Integer concurrentID = getArguments().getInt("Concurrent ID");
        Integer day = getArguments().getInt("Day");

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


        return view;
    }
}
