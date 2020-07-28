package com.industry5.iot.temp;

import com.industry5.iot.temp.domain.Temperature;

import java.util.Date;
import java.util.List;

public class Controller {

    private String processPing() {
        return "OK: " + new Date();
    }
    private List<Temperature> listTemperatures() {
        throw new UnsupportedOperationException("Not implemented");
    }
    private Temperature getTemperature(String id) {
        throw new UnsupportedOperationException("Not implemented");
    }
    private List<Temperature> createTemperature(List<Temperature> list) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
