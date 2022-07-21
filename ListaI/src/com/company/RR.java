package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class RR {

    private float wholeTimeWaiting = 0;

    public float RR_algorithm(float q, ArrayList<Process> processes) {

        ArrayList<Process> processList = new ArrayList<>(processes);
        for (Process p :
                processList) {
            p.setIsDone(false);
            p.setRemainingTime(p.getPhaseLength());
            p.setWaitingTime(0);
        }
        processList.sort(Comparator.comparingInt(Process::getMomentOfEnter));

        ArrayList<Process> queue = new ArrayList<>();

        Process actualProcess = processList.get(0);
        int actualProcessIndex = 0;
        queue.add(actualProcess);

        int finishedProcesses = 0;
        float actualTime = actualProcess.getMomentOfEnter();


        while (finishedProcesses < processList.size()) {

            if (actualTime < actualProcess.getMomentOfEnter())
                actualTime = actualProcess.getMomentOfEnter();

            if (actualProcess.getRemainingTime() > q) {
                actualTime += q;
                actualProcess.setRemainingTime(actualProcess.getRemainingTime() - q);
            }

            else {
                actualTime += actualProcess.getRemainingTime();

                actualProcess.setRemainingTime(0);
                actualProcess.setIsDone(true);
                actualProcess.setWaitingTime(actualTime);

                finishedProcesses++;

                queue.remove(actualProcess);
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

            queue.sort(Comparator.comparingInt(Process::getMomentOfEnter));

            if (!queue.isEmpty()) {
                if (!actualProcess.getIsDone())
                    actualProcessIndex++;

                if (actualProcessIndex >= queue.size())
                    actualProcessIndex = 0;

                actualProcess = queue.get(actualProcessIndex);
            }

            else if (finishedProcesses < processList.size()) {

                int k = 0;
                Process next = processList.get(0);

                while (next.getIsDone() && k < processList.size()-1)
                    next = processList.get(++k);

                actualProcess = next;
                actualProcessIndex = 0;

                queue.add(next);
            }

        }

        for (Process p :
                processList) {
            float x = p.getWaitingTime() - p.getPhaseLength() - p.getMomentOfEnter();
            System.out.println(p.toString());
            wholeTimeWaiting += x;
        }

        return wholeTimeWaiting/processList.size();
    }
}
