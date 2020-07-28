package com.industry5.iot.temp;

import com.industry5.iot.temp.domain.Temperature;

import java.util.Date;
import java.util.List;

public class Controller {

    public String processPing() {
        return "OK: " + new Date();
    }
    public List<Temperature> listTemperatures() {
        throw new UnsupportedOperationException("Not implemented");
    }
    public Temperature getTemperature(String id) {
        throw new UnsupportedOperationException("Not implemented");
    }
    public List<Temperature> createTemperature(List<Temperature> list) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
