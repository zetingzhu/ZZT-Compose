package com.zzt.compose.entity;


import java.util.List;

/**
 * @author: zeting
 * @date: 2025/6/25
 */
public class ForecastsDTO {

    private String city;

    private String adcode;

    private String province;

    private String reporttime;

    private List<CastsDTO> casts;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public List<CastsDTO> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsDTO> casts) {
        this.casts = casts;
    }

    @Override
    public String toString() {
        return "\n  ForecastsDTO{" +
                ", city='" + city + '\'' +
                ", adcode='" + adcode + '\'' +
                ", province='" + province + '\'' +
                ", reporttime='" + reporttime + '\'' +
                "\n casts=" + casts +
                '}';
    }
}
