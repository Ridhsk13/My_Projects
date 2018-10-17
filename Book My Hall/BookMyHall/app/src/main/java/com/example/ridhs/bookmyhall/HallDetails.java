package com.example.ridhs.bookmyhall;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by RIDHS on 4/22/2017.
 */

public class HallDetails implements Serializable {

    String address;
    Long capacity;
    String floorArea;
    String hallDesc;
    List<String> images;
    String name;
    Map<String, String> reviews;

    public HallDetails() {
    }

    public HallDetails(String address, Long capacity, String floorArea, String hallDecsription, List<String> images, String name, Map<String, String> reviews) {
        this.address = address;
        this.capacity = capacity;
        this.floorArea = floorArea;
        this.hallDesc = hallDecsription;
        this.images = images;
        this.name = name;
        this.reviews = reviews;
    }

    public Map<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getHallDesc() {
        return hallDesc;
    }

    public void setHallDesc(String hallDesc) {
        this.hallDesc = hallDesc;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
