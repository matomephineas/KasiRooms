package com.example.kasirooms.Models;

public class LocationModel {
    private String locationID,town,section;

    public LocationModel() {
    }

    public LocationModel(String locationID, String town, String section) {
        this.locationID = locationID;
        this.town = town;
        this.section = section;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
