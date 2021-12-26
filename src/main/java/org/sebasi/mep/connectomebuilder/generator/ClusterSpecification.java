package org.sebasi.mep.connectomebuilder.generator;

public class ClusterSpecification {

    int numNeuronsMin;
    int numNeuronsMax;
    int eachNeuronListensToHowManyOtherNeuronsInThisCluster;

    public ClusterSpecification() {
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

    public int getEachNeuronListensToHowManyOtherNeuronsInThisCluster() {
        return eachNeuronListensToHowManyOtherNeuronsInThisCluster;
    }

    public void setEachNeuronListensToHowManyOtherNeuronsInThisCluster(int eachNeuronListensToHowManyOtherNeuronsInThisCluster) {
        this.eachNeuronListensToHowManyOtherNeuronsInThisCluster = eachNeuronListensToHowManyOtherNeuronsInThisCluster;
    }
}
