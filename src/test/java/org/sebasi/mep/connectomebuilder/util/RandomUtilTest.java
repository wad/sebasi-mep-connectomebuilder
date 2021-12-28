package org.sebasi.mep.connectomebuilder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomUtilTest {

    @Test
    public void testGetRandomNumberInRange() {
        RandomUtil randomUtil = new RandomUtil();
        for (int i = 0; i < 200; i++) {
            int randomNumber = randomUtil.getRandomNumber(1);
            assertTrue(
                    randomNumber >= 0 && randomNumber <= 1,
                    "Random number was " + randomNumber);
        }
    }

    @Test
    public void testGetRandomNumberWhenMaxIsLow() {
        RandomUtil randomUtil = new RandomUtil();
        for (int i = 0; i < 10; i++) {
            int randomNumber = randomUtil.getRandomNumber(0);
            assertEquals(
                    0,
                    randomNumber,
                    "Random number was " + randomNumber);
        }
        for (int i = 0; i < 10; i++) {
            int randomNumber = randomUtil.getRandomNumber(-1);
            assertEquals(
                    0,
                    randomNumber,
                    "Random number was " + randomNumber);
        }
    }

    @Test
    public void testChanceAsFraction() {
        RandomUtil randomUtil = new RandomUtil();

        assertFalse(randomUtil.shouldEventTrigger(-1, -1));
        assertFalse(randomUtil.shouldEventTrigger(0, -1));
        assertFalse(randomUtil.shouldEventTrigger(1, -1));

        assertFalse(randomUtil.shouldEventTrigger(-1, 0));
        assertFalse(randomUtil.shouldEventTrigger(0, 0));
        assertFalse(randomUtil.shouldEventTrigger(1, 0));

        assertFalse(randomUtil.shouldEventTrigger(-1, 1));
        assertFalse(randomUtil.shouldEventTrigger(0, 1));
        assertTrue(randomUtil.shouldEventTrigger(1, 1));
        assertTrue(randomUtil.shouldEventTrigger(100, 100));
    }

    @Test
    public void testNormalDistributionRandomValues() {
        Histogram histogram = new Histogram();
        RandomUtil randomUtil = new RandomUtil();
        for (int i = 0; i < 10000; i++) {
            int randomNumberInNormalDistribution = randomUtil.getRandomNumberInNormalDistribution(0, 25);
            histogram.addDataPoint(randomNumberInNormalDistribution);
        }
        System.out.println(histogram.show());
    }
}
