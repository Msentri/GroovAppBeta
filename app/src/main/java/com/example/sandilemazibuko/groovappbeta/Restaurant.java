package com.example.sandilemazibuko.groovappbeta;

/**
 * Created by msentri on 2015/10/27.
 */
public class Restaurant {

    public String id;
    public String name;
    public double latitude;
    public double longitude;
    public int type;
    public String date_added;
    public String date_modified;
    public String contact_number;
    public String street_name;
    public String city_name;

    public Restaurant(String id, String name, double latitude,
                      double longitude, int type,
                      String contact_number, String street_name,
                      String city_name, String provice_name,
                      String code) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.contact_number = contact_number;
        this.street_name = street_name;
        this.city_name = city_name;
        this.provice_name = provice_name;
        this.code = code;
    }

    public String provice_name;
    public String code;

    public Restaurant(String id, String name, double latitude, double longitude,
                      int type, String date_added, String date_modified,
                      String contact_number, String street_name,
                      String city_name, String provice_name, String code) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.date_added = date_added;
        this.date_modified = date_modified;
        this.contact_number = contact_number;
        this.street_name = street_name;
        this.city_name = city_name;
        this.provice_name = provice_name;
        this.code = code;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvice_name() {
        return provice_name;
    }

    public void setProvice_name(String provice_name) {
        this.provice_name = provice_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }




}
