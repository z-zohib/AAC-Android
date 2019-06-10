package com.aac.events;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Speaker {
    private int id;
    private String peopleTitle;
    private String imageURL;
    private String imageName;
    private String name;
    private String description;
    private JSONArray sessionIDs;

    public Speaker(JSONObject speaker) throws JSONException {
        this.id = speaker.getInt("id");
        this.imageURL = speaker.getString("imageURL");
        this.imageName = speaker.getString("imageName");
        this.name = speaker.getString("name");
        this.description = speaker.getString("personDescription");
        this.sessionIDs = speaker.getJSONArray("sessionIDs");
        this.peopleTitle = speaker.getString("peopleTitle");
    }

    public int getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Integer> getSessionIDs() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < sessionIDs.length(); i++) {
            try {
                list.add(Integer.parseInt(sessionIDs.getString(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public String getPeopleTitle() {
        return peopleTitle;
    }

}
