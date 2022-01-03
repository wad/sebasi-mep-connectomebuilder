package org.sebasi.mep.connectomebuilder.util;

import java.util.Date;

public class TextOutput {

    public static void showMessage(String message) {
        System.out.println(getTimestampPrefix() + message);
    }

    static String getTimestampPrefix() {
        Date date = new Date();
        return date.toString() + " ";
    }
}
