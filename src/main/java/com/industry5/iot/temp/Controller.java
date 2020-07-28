package com.industry5.iot.temp;

import com.industry5.iot.temp.domain.Temperature;
import com.industry5.iot.temp.repo.TemperatureRepository;

import java.util.Date;
import java.util.List;

public class Controller {

    TemperatureRepository repository;

    public Controller(TemperatureRepository repository) {
        this.repository = repository;
    }

    public String processPing() {
        return "OK: " + new Date();
    }

    public List<Temperature> listTemperatures() {
        return repository.listRecent();
    }

    public Temperature getTemperature(String id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public List<Temperature> createTemperature(List<Temperature> list) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
