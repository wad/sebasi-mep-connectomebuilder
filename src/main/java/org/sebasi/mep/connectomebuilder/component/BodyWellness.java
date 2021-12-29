package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

// This is an abstraction for all kinds of attributes associated with a body.
// It's here to represent the goal of homeostasis, which appears to be an important aspect of brain function.
// The dimensions represent physical things like oxygen availability, health, hunger, sleep, hormone balance, health, etc.
// They also represent things like emotional well-being, sanity, etc.
public class BodyWellness {

    private static final int NUM_WELLNESS_DIMENSIONS = 4;
    private static final int INITIAL_VALUE_MIN = -1000;
    private static final int INITIAL_VALUE_MAX = 1000;

    // All zero values indicates perfect wellness
    // They can be negative or positive. The brain tends to want to get them close to zero.
    int[] wellnessDimensions = new int[NUM_WELLNESS_DIMENSIONS];

    // todo: also include some momentum behind changing values.

    public BodyWellness() {
        for (int i = 0; i < NUM_WELLNESS_DIMENSIONS; i++) {
            wellnessDimensions[i] = 0;
        }
    }

    public void initializeWithRandomValues() {
        for (int i = 0; i < NUM_WELLNESS_DIMENSIONS; i++) {
            wellnessDimensions[i] = GlobalStaticHelper.getRandomUtil().getRandomNumber(
                    INITIAL_VALUE_MIN,
                    INITIAL_VALUE_MAX);
        }
    }

    public void report(StringBuilder builder) {
        builder.append("\nWellness: ").append(Arrays.toString(wellnessDimensions));
    }
}
