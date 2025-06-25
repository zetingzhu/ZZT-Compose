package com.example.zt_composttest.entiy;

import java.util.List;

/**
 * @author: zeting
 * @date: 2025/4/11
 * 搞的天气接口
 */
public class GaodeWeather {

    private String status;

    private String count;

    private String info;

    private String infocode;

    private List<ForecastsDTO> forecasts; // 单个城市的天气预报

    private List<LivesDTO> lives;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<ForecastsDTO> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastsDTO> forecasts) {
        this.forecasts = forecasts;
    }

    public List<LivesDTO> getLives() {
        return lives;
    }

    public void setLives(List<LivesDTO> lives) {
        this.lives = lives;
    }

    @Override
    public String toString() {
        return "GaodeWeather{" +
                ", status='" + status + '\'' +
                ", count='" + count + '\'' +
                ", info='" + info + '\'' +
                ", infocode='" + infocode + '\'' +
                "\n forecasts=" + forecasts +
                "\n lives=" + lives +
                '}';
    }
}
