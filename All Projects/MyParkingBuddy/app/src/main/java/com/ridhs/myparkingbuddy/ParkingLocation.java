package com.ridhs.myparkingbuddy;

import java.util.Map;

/**
 * Created by RIDHS on 9/30/2017.
 */

public class ParkingLocation {
    String loc_name, loc_address, loc_latitude, loc_longitude;

    public ParkingLocation() {
    }

    public ParkingLocation(String loc_name, String loc_address, String loc_latitude, String loc_longitude) {
        this.loc_name = loc_name;
        this.loc_address = loc_address;
        this.loc_latitude = loc_latitude;
        this.loc_longitude = loc_longitude;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getLoc_address() {
        return loc_address;
    }

    public void setLoc_address(String loc_address) {
        this.loc_address = loc_address;
    }

    public String getLoc_latitude() {
        return loc_latitude;
    }

    public void setLoc_latitude(String loc_latitude) {
        this.loc_latitude = loc_latitude;
    }

    public String getLoc_longitude() {
        return loc_longitude;
    }

    public void setLoc_longitude(String loc_longitude) {
        this.loc_longitude = loc_longitude;
    }
}
