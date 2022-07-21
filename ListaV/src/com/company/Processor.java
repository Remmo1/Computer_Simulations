package com.company;

import java.util.ArrayList;

public class Processor {
    private final String name;
    private int powerUsedCurrently;
    ArrayList<Process> processesOnProcessor;

    public Processor(String name, int powerUsed) {
        this.name = name;
        this.powerUsedCurrently = powerUsed;
        processesOnProcessor = new ArrayList<>();
    }

    public int getPowerUsedCurrently() { return powerUsedCurrently; }

    public void setPowerUsedCurrently(int powerUsedCurrently) { this.powerUsedCurrently = powerUsedCurrently; }

    @Override
    public String toString() {
        return name;
    }
}