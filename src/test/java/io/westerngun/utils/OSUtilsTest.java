package io.westerngun.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OSUtilsTest {

    @Test
    public void shouldPrintAllSystemProperties() {
        OSUtils.printAllSystemProps();
        System.out.println("OK!");
    }
}