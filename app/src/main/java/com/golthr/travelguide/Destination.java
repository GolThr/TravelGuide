package com.golthr.travelguide;

public class Destination {
    private int id;
    //城市
    private String city;
    //景区
    private String spot;
    //纬度
    private double Latitude;
    //经度
    private double Longitude;

    public Destination(int id, String city, String spot, double latitude, double longitude) {
        this.id = id;
        this.city = city;
        this.spot = spot;
        Latitude = latitude;
        Longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
