package com.company;

import java.util.ArrayList;

public class SJF {

    public float SJF_algorithm(ArrayList<Process> processes) {

        ArrayList<Process> processList = new ArrayList<>(processes);
        for (Process p :
                processList) {
            p.setIsDone(false);
            p.setRemainingTime(p.getPhaseLength());
            p.setWaitingTime(0);
        }

        processList.sort((o1, o2) -> {
            if (o1.getMomentOfEnter() == o2.getMomentOfEnter())
                return Float.compare(o1.getRemainingTime(), o2.getRemainingTime());
            return Float.compare(o1.getMomentOfEnter(), o2.getMomentOfEnter());
        });

        FCFS fcfs = new FCFS();
        return fcfs.FCFS_algorithm(processList);
    }
}
