package com.aac.events;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {
    private int id;
    private int day;
    private int concurrentSessionId;
    private String title;
    private String location;
    private String startDate;
    private String endDate;
    private String description;
    private String evaluationURL;
    private String speakerIDs;

    public Event(JSONObject event) throws JSONException {
        this.id = event.getInt("id");
        this.day = event.getInt("day");
        this.concurrentSessionId = event.getInt("concurrentSessionId");
        this.title = event.getString("title");
        this.location = event.getString("location");
        this.startDate = event.getString("startDate");
        this.endDate = event.getString("endDate");
        this.description = event.getString("description");
        this.evaluationURL = event.getString("evaluationURL");
        this.speakerIDs = event.getString("speakerIDs");
    }

    public int getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getConcurrentSessionId() {
        return concurrentSessionId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getEvaluationURL() {
        return evaluationURL;
    }

    public String getSpeakerIDs() {
        return speakerIDs;
    }
}
