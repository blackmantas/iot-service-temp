package com.industry5.iot.temp.domain;

import java.util.Date;
import java.util.List;

public class Temperature {

    private String id;
    private double temperature;
    private String sensorId;
    private Date dateTime;
    private List<Integer> errorCodes;

//    public Temperature() {
//    }

    public Temperature(String id, double temperature, String sensorId, Date dateTime, List<Integer> errorCodes) {
        this.id = id;
        this.temperature = temperature;
        this.sensorId = sensorId;
        this.dateTime = dateTime;
        this.errorCodes = errorCodes;
    }

    public Temperature(String id, double temperature, String sensorId, Date dateTime) {
        this.id = id;
        this.temperature = temperature;
        this.sensorId = sensorId;
        this.dateTime = dateTime;
//        this.errorCodes = errorCodes;
    }

    public String getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getSensorId() {
        return sensorId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public List<Integer> getErrorCodes() {
        return errorCodes;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id='" + id + '\'' +
                ", temperature=" + temperature +
                ", sensorId='" + sensorId + '\'' +
                ", dateTime=" + dateTime +
                ", errorCodes=" + errorCodes +
                '}';
    }
}
