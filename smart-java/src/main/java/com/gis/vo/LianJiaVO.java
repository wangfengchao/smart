package com.gis.vo;

/**
 * Created by fc.w on 2017/12/5.
 */
public class LianJiaVO {

    private Integer id;
    private String polyline;
    private Float lng;
    private Float lat;

    public LianJiaVO() {
    }

    public LianJiaVO(Integer id, String polyline, Float lng, Float lat) {
        this.id = id;
        this.polyline = polyline;
        this.lng = lng;
        this.lat = lat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }
}
