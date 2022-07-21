package com.company;

import java.util.ArrayList;

public class SameAmountOfFramesPerProcess {
    private final int amountOfFrames;
    private final ArrayList<Process> processes;
    private final ArrayList<Integer> results;

    public SameAmountOfFramesPerProcess(int amountOfFrames, ArrayList<Process> processes) {
        this.amountOfFrames = amountOfFrames;
        this.processes = processes;
        results = new ArrayList<>();
    }

    public void sharingFrames() {
        for (Process p :
             processes) {
            p.setAmountOfFrames(amountOfFrames/processes.size());
        }
    }

    public ArrayList<Integer> amountOfPageErrors() {

        LRU lru;
        sharingFrames();

        for (Process p :
                processes) {
                lru = new LRU(p.getPages(), p.getAmountOfFrames());
                int h = lru.amountOfPageErrors();
                results.add(h);
        }

        return results;
    }
}
