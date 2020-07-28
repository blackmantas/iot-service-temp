package com.industry5.iot.temp.repo;

import com.industry5.iot.temp.domain.Temperature;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TemperatureRepository {

    public Temperature findById(String id) {
        if (id.startsWith("a")) {
            return new Temperature("123", 25.4, "123", new Date(), null);
        } else {
            return new Temperature("666", 66.6, "666", new Date(), Arrays.asList(13, 6));
        }
    }

    public List<Temperature> listRecent() {
        return Arrays.asList(
                new Temperature("123", 25.4, "123", new Date(), null),
                new Temperature("666", 66.6, "666", new Date(), Arrays.asList(13, 6))
        );
    }
}