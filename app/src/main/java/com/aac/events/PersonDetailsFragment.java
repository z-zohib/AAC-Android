package com.aac.events;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;


public class PersonDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.speakerdetails, container,false);
        view.setBackgroundResource(R.color.lists);

        Bundle args = this.getArguments();
        TextView textViewPersonName = (TextView) view.findViewById(R.id.person_name);
        TextView textViewPersonTitle = (TextView) view.findViewById(R.id.persontitle);
        TextView textViewPersonBio = (TextView) view.findViewById(R.id.person_bio);
        ImageView imageViewPerson = (ImageView) view.findViewById(R.id.personimage);


        Integer personID = getArguments().getInt("Person ID");
        String imageURL = getArguments().getString("Image URL");
        String imageName = getArguments().getString("Image Name");
        String personName = getArguments().getString("Person Name");
        String personBio = getArguments().getString("Person Description");
        String sids = getArguments().getString("Session IDs");
        String personTitle = getArguments().getString("Title");

        if (!imageName.isEmpty()){

            int person_image = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
            imageViewPerson.setImageResource(person_image);

        }
        else {
            Picasso.get().load(imageURL).into(imageViewPerson);
        }

        textViewPersonName.setText(personName);
        textViewPersonTitle.setText(personTitle);
        textViewPersonBio.setText(personBio);

        JSONArray sessionIDs;
        try {
            sessionIDs = new JSONArray(sids);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < sessionIDs.length(); i++) {

        }

        return view;


    }



}
