package com.company;

public class Process {
    private final int momentOfEnter;
    private final int powerNeeded;
    private final int momentOfFinish;
    private final Processor processorOnWhichAppears;
    private Processor onThisIsWorking;

    public Process(int momentOfEnter, int powerNeeded, int momentOfFinish, Processor processorOnWhichAppears) {
        this.momentOfEnter = momentOfEnter;
        this.powerNeeded = powerNeeded;
        this.momentOfFinish = momentOfFinish;
        this.processorOnWhichAppears = processorOnWhichAppears;
    }

    public int getMomentOfEnter() { return momentOfEnter; }
    public int getMomentOfFinish() { return momentOfFinish; }
    public int getPowerNeeded() { return powerNeeded; }
    public Processor getProcessorOnWhichAppears() { return processorOnWhichAppears; }
    public Processor getOnThisIsWorking() { return onThisIsWorking; }

    public void setOnThisIsWorking(Processor onThisIsWorking) { this.onThisIsWorking = onThisIsWorking; }

    @Override
    public String toString() {
        return momentOfEnter + " " + powerNeeded + " " + momentOfFinish + " " + processorOnWhichAppears;
    }
}
