package com.company;

public class Process {

    private int placeOnDisk;
    private int momentOfEnter;
    private boolean isDone;
    private int deadlineTime;

    public Process(int placeOnDisk, int momentOfEnter) {
        this.placeOnDisk = placeOnDisk;
        this.momentOfEnter = momentOfEnter;
        isDone = false;
    }

    public int getPlaceOnDisk() { return placeOnDisk; }
    public boolean getisDone() { return isDone; }
    public int getMomentOfEnter() { return momentOfEnter; }
    public int getDeadlineTime() { return deadlineTime; }

    public void setPlaceOnDisk(int placeOnDisk) { this.placeOnDisk = placeOnDisk; }
    public void setDone(boolean done) { isDone = done; }
    public void setMomentOfEnter(int momentOfEnter) { this.momentOfEnter = momentOfEnter; }
    public void setDeadlineTime(int deadlineTime) { this.deadlineTime = deadlineTime; }

    @Override
    public String toString() {
        return placeOnDisk + "      " + momentOfEnter + "      " + deadlineTime;
    }
}
