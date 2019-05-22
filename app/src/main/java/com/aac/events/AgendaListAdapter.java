package com.aac.events;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AgendaListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] sessionname;
    private final String[] sessionlocation;
    private final String[] starttime;
    private final String[] endtime;

    public AgendaListAdapter(Activity context, String[] sessionname, String[] sessionlocation,String[] starttime, String[] endtime) {
        super(context, R.layout.agenda_item, sessionname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.sessionname = sessionname;
        this.sessionlocation = sessionlocation;
        this.starttime = starttime;
        this.endtime = endtime;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.agenda_item, null, true);

        TextView sessionTitle = (TextView) rowView.findViewById(R.id.session);
        TextView sessionLocation = (TextView) rowView.findViewById(R.id.location);
        TextView startTime = (TextView) rowView.findViewById(R.id.starttime);
        TextView endTime = (TextView) rowView.findViewById(R.id.endtime);

        sessionTitle.setText(sessionname[position]);
        sessionLocation.setText(sessionlocation[position]);
        startTime.setText(starttime[position]);
        endTime.setText(endtime[position]);
        return rowView;

    }
}