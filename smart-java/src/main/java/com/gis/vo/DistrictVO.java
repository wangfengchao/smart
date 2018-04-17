package com.gis.vo;

/**
 * Created by fc.w on 2017/12/9.
 */
public class DistrictVO {

    private Integer id;
    private String cityCode;
    private String name;

    public DistrictVO() {
    }

    public DistrictVO(Integer id, String cityCode, String name) {
        this.id = id;
        this.cityCode = cityCode;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
