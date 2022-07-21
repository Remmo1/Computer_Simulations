package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
        ogólny wzór na obliczenie czasu oczekiwania:
        tw = ta - tc - tp

        gdzie
        tw - czas oczekiwania
        ta - czas aktualny
        tc - czas trwania
        tp - czas przyjścia
*/



public class FCFS {

    private float wholeTimeWaiting = 0;

    public float FCFS_algorithm(ArrayList<Process> processes) {

        // copying original list
        List<Process> processList = new ArrayList<>(processes);
        for (Process p :
                processList) {
            p.setIsDone(false);
            p.setRemainingTime(p.getPhaseLength());
            p.setWaitingTime(0);
        }

        // sorting: momentOfEnter
        processList.sort(Comparator.comparingInt(Process::getMomentOfEnter));

        // algorithm
        int finishedProcesses = 0;
        Process actualProcess = processList.get(0);
        int actualTime = 0;
        int i = 1;

        while (finishedProcesses < processList.size()) {
            if (actualTime < actualProcess.getMomentOfEnter())
                actualTime = actualProcess.getMomentOfEnter();

            actualTime += actualProcess.getPhaseLength();
            actualProcess.setWaitingTime(actualTime);
            actualProcess.setIsDone(true);

            if (i == processList.size())
                break;

            actualProcess = processList.get(i);

            i++;
            finishedProcesses++;
        }

        for (Process p :
                processList) {
            float x = p.getWaitingTime() - p.getMomentOfEnter() - p.getPhaseLength();
            wholeTimeWaiting += x;
        }

        return wholeTimeWaiting/processList.size();
    }
}
