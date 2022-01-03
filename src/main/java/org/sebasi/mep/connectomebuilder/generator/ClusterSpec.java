package org.sebasi.mep.connectomebuilder.generator;

public class ClusterSpec {

    int numNeuronsMin;
    int numNeuronsMax;
    int numSynapsesLocalMin;
    int numSynapsesLocalMax;
    int initialSynapticStrengthMin;
    int initialSynapticStrengthMax;

    public ClusterSpec() {
    }

    public int getNumNeuronsMin() {
        return numNeuronsMin;
    }

    public void setNumNeuronsMin(int numNeuronsMin) {
        this.numNeuronsMin = numNeuronsMin;
    }

    public int getNumNeuronsMax() {
        return numNeuronsMax;
    }

    public void setNumNeuronsMax(int numNeuronsMax) {
        this.numNeuronsMax = numNeuronsMax;
    }

    public int getNumSynapsesLocalMin() {
        return numSynapsesLocalMin;
    }

    public void setNumSynapsesLocalMin(int numSynapsesLocalMin) {
        this.numSynapsesLocalMin = numSynapsesLocalMin;
    }

    public int getNumSynapsesLocalMax() {
        return numSynapsesLocalMax;
    }

    public void setNumSynapsesLocalMax(int numSynapsesLocalMax) {
        this.numSynapsesLocalMax = numSynapsesLocalMax;
    }

    public int getInitialSynapticStrengthMin() {
        return initialSynapticStrengthMin;
    }

    public void setInitialSynapticStrengthMin(int initialSynapticStrengthMin) {
        this.initialSynapticStrengthMin = initialSynapticStrengthMin;
    }

    public int getInitialSynapticStrengthMax() {
        return initialSynapticStrengthMax;
    }

    public void setInitialSynapticStrengthMax(int initialSynapticStrengthMax) {
        this.initialSynapticStrengthMax = initialSynapticStrengthMax;
    }
}
