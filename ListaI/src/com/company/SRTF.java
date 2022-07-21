package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRTF {

    private float wholeTimeWaiting = 0;


    public float SRTF_algorithm(ArrayList <Process> processes) {

        List<Process> processList = new ArrayList<>(processes);
        for (Process p :
                processList) {
            p.setIsDone(false);
            p.setRemainingTime(p.getPhaseLength());
            p.setWaitingTime(0);
        }
        processList.sort(Comparator.comparingInt(Process::getMomentOfEnter));

        Process actualProcess = processList.get(0);
        float actualTime = actualProcess.getMomentOfEnter();
        int finishedProcesses = 0;
        int quantum = 2;

        ArrayList<Process> queue = new ArrayList<>();
        queue.add(actualProcess);

        while(finishedProcesses < processList.size()) {

            if (actualTime < actualProcess.getMomentOfEnter())
                actualTime = actualProcess.getMomentOfEnter();

            if (actualProcess.getRemainingTime() >= quantum) {
                actualTime += quantum;
                actualProcess.setRemainingTime(actualProcess.getRemainingTime() - quantum);
            }

            else {
                actualTime += actualProcess.getRemainingTime();
                actualProcess.setRemainingTime(0);
                actualProcess.setIsDone(true);
                actualProcess.setWaitingTime(actualTime);

                finishedProcesses++;

                queue.remove(0);
            }

            int i = 0;
            Process help = processList.get(i);

            while(help.getMomentOfEnter() <= actualTime && i < processList.size()) {
                if (!help.getIsDone() && !queue.contains(help))
                    queue.add(help);
                i++;
                if (i < processList.size())
                    help = processList.get(i);
            }

            queue.sort((o1, o2) -> Float.compare(o1.getRemainingTime(), o2.getRemainingTime()));

            if (!queue.isEmpty())
                actualProcess = queue.get(0);

            else if (finishedProcesses < processList.size()) {

                int j = 0;
                Process shortestProcess = processList.get(0);

                while (shortestProcess.getIsDone() && j < processList.size())
                    shortestProcess = processList.get(++j);

                actualProcess = shortestProcess;
                queue.add(actualProcess);
            }
        }

        for (Process p :
                processList) {
            float x = p.getWaitingTime() - p.getPhaseLength() - p.getMomentOfEnter();
            wholeTimeWaiting += (x);
        }

        return wholeTimeWaiting/processList.size();
    }
}
