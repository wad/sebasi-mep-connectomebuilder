package org.sebasi.mep.connectomebuilder;

public class Ticker {

    NeuronGroup neuronGroup = new NeuronGroup();

    public static void main(String... args) {
        Ticker ticker = new Ticker();
        ticker.startTicking();
    }

    public void startTicking() {
        for (int tick = 0; tick < 100; tick++) {
            neuronGroup.processTick();
        }
    }
}
