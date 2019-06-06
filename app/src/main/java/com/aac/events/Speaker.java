package com.aac.events;

import org.json.JSONException;
import org.json.JSONObject;

public class Speaker {
    private int id;
    private String speakerTitle;
    private String imageURL;
    private String imageName;
    private String name;
    private String description;
    private String sessionIDs;

    public Speaker(JSONObject speaker) throws JSONException {
        this.id = speaker.getInt("id");
        this.imageURL = speaker.getString("imageURL");
        this.imageName = speaker.getString("imageName");
        this.name = speaker.getString("name");
        this.description = speaker.getString("personDescription");
        this.sessionIDs = speaker.getString("sessionIDs");
        this.speakerTitle = speaker.getString("peopleTitle");
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

    public String getSessionIDs() {
        return sessionIDs;
    }

    public String getSpeakerTitle() {
        return speakerTitle;
    }

}
