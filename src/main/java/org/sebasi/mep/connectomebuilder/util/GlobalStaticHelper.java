package org.sebasi.mep.connectomebuilder.util;

public class GlobalStaticHelper {

    private static final RandomUtil randomUtil;

    static {
        randomUtil = new RandomUtil();
    }

    public static RandomUtil getRandomUtil() {
        return randomUtil;
    }
}
