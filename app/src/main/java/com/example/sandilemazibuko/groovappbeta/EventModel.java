package com.example.sandilemazibuko.groovappbeta;

/**
 * Created by sandilemazibuko on 15/11/30.
 */
public class EventModel {

    public String id;
    public String place_id;
    public String event_description;
    public String getEvent_banner_url;
    public String start_date;
    public String end_date;

    public EventModel() {
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getGetEvent_banner_url() {
        return getEvent_banner_url;
    }

    public void setGetEvent_banner_url(String getEvent_banner_url) {
        this.getEvent_banner_url = getEvent_banner_url;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
