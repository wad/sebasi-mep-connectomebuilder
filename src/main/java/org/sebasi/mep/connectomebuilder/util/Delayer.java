package org.sebasi.mep.connectomebuilder.util;

public class Delayer {

    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            TextOutput.showMessage("Delay interrupted! Exiting.");
            System.exit(1);
        }
    }
}
