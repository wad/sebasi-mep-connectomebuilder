package org.sebasi.mep.connectomebuilder.util;

import java.util.Random;

public class RandomUtil {

    Random random;

    public RandomUtil() {
        random = new Random();
    }

    public boolean shouldEventTrigger(Chance chance) {
        return shouldEventTrigger(
                chance.numerator,
                chance.denominator);
    }

    public boolean shouldEventTrigger(
            int numerator,
            int denominator) {

        if (denominator <= 0 || numerator <= 0) {
            return false;
        }

        if (denominator <= numerator) {
            return true;
        }

        return numerator <= random.nextInt(denominator);
    }

    // returns a value between 0 and max, inclusive
    public int getRandomNumber(int max) {
        return getRandomNumber(0, max);
    }

    public int getRandomNumber(
            int min,
            int max) {
        int range = max - min;
        if (range < 1) {
            return min;
        }
        return min + random.nextInt(range + 1);
    }

    public int getRandomNumberInNormalDistribution(
            int mean,
            int sd) {

        /*
        Z = (X - u) / s
        where:
        Z = value on the standard normal distribution
        X = value on the original distribution
        u = mean of the original distribution
        s = standard deviation of the original distribution

         Z = (X - u) / s;
         Zs = X - u
         Zs+u = X
         */
        double Z = random.nextGaussian();
        return (int) ((Z * (double) sd) + (double) mean);
    }
}
