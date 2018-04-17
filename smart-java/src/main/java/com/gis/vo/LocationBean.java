package com.gis.vo;

/**
 * Created by fc.w on 2017/12/13.
 */
public class LocationBean {

    private double lat;
    private double lng;

    public LocationBean() {
    }

    public LocationBean(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
