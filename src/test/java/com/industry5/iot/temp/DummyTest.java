package com.industry5.iot.temp;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DummyTest {

    private static String payLoad;

    @BeforeAll
    static void setUp() {
        System.out.println("@BeforeAll executed");
    }

    @BeforeEach
    void setUpThis() {
        System.out.println("@beforeEach");
    }

    @Test
    void testFooBar() {
    }

    @Disabled
    @Test
    void testDummyTest() {
        System.out.println("I test nothing so far");
    }

}