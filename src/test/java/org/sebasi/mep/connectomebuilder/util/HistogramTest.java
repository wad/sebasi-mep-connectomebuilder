package org.sebasi.mep.connectomebuilder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.sebasi.mep.connectomebuilder.util.Histogram.DEFAULT_DESIRED_BAR_LENGTH;

public class HistogramTest {

    @Test
    public void testWithSingleDataPoint() {
        Histogram histogram = new Histogram();
        histogram.addDataPoint(10);
        String output = histogram.show();
        assertEquals("10,10 (1)    | ########################################\n", output);
    }

    @Test
    public void testWithTwoDataPoints() {
        Histogram histogram = new Histogram();
        histogram.addDataPoint(1);
        histogram.addDataPoint(10);
        String output = histogram.show();
        System.out.println(output);
        assertEquals(
                "1,1 (1)      | ########################################\n" +
                        "2,2 (0)      | \n" +
                        "3,3 (0)      | \n" +
                        "4,4 (0)      | \n" +
                        "5,5 (0)      | \n" +
                        "6,6 (0)      | \n" +
                        "7,7 (0)      | \n" +
                        "8,8 (0)      | \n" +
                        "9,9 (0)      | \n" +
                        "10,10 (1)    | ########################################\n", output);
    }

    @Test
    public void testRandom() {
        RandomUtil randomUtil = new RandomUtil();
        Histogram histogram = new Histogram();
        for (int i = 0; i < 500; i++) {
            histogram.addDataPoint(randomUtil.getRandomNumber(99));
        }
        System.out.println(histogram.show());
    }

    @Test
    public void testDetermineNumCharsInBar() {
        assertEquals(0, Histogram.determineNumCharsInBar(
                0,
                100,
                DEFAULT_DESIRED_BAR_LENGTH));
        assertEquals(DEFAULT_DESIRED_BAR_LENGTH >> 1, Histogram.determineNumCharsInBar(
                50,
                100,
                DEFAULT_DESIRED_BAR_LENGTH));
        assertEquals(DEFAULT_DESIRED_BAR_LENGTH, Histogram.determineNumCharsInBar(
                100,
                100,
                DEFAULT_DESIRED_BAR_LENGTH));
    }
}
