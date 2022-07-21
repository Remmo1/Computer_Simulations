package com.company;

public class Process {

    private int processNumber;
    private int momentOfEnter;
    private int phaseLength;
    private float remainingTime;
    private float waitingTime;
    private boolean IsDone;

    public Process(int processNumber, int momentOfEnter, int phaseLength, int remainingTime, int waitingTime) {
        this.processNumber = processNumber;
        this.momentOfEnter = momentOfEnter;
        this.phaseLength = phaseLength;
        this.remainingTime = remainingTime;
        IsDone = false;
    }

    public int getMomentOfEnter() { return momentOfEnter; }
    public int getPhaseLength() { return phaseLength; }
    public int getProcessNumber() { return processNumber; }
    public float getRemainingTime() { return remainingTime; }
    public float getWaitingTime() { return waitingTime; }
    public boolean getIsDone() { return IsDone; }

    public void setMomentOfEnter(int momentOfEnter) { this.momentOfEnter = momentOfEnter; }
    public void setPhaseLength(int phaseLength) { this.phaseLength = phaseLength; }
    public void setProcessNumber(int processNumber) { this.processNumber = processNumber; }
    public void setRemainingTime(float remainingTime) { this.remainingTime = remainingTime; }
    public void setWaitingTime(float waitingTime) { this.waitingTime = waitingTime; }
    public void setIsDone(boolean done) { IsDone = done; }

    @Override
    public String toString() {
        return processNumber + " " + momentOfEnter + " " + phaseLength + " " + remainingTime + " " + waitingTime;
    }
}
