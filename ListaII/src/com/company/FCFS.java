package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class FCFS implements Algorithm {

    private final ArrayList<Process> list = new ArrayList<>();
    private final ArrayList<Process> queue = new ArrayList<>();

    private int wholeAmountOfChangingPosition;
    private int actPosition;
    private int actualTime;
    private int endedProcesses;


    public FCFS(ArrayList<Process> processes) {
        for (Process p :
                processes) {
            list.add(new Process(p.getPlaceOnDisk(), p.getMomentOfEnter()));
        }

        list.sort(Comparator.comparingInt(Process::getMomentOfEnter));

        actPosition = 1;
        endedProcesses = 0;
        actualTime = 0;
        wholeAmountOfChangingPosition = 0;
    }


    @Override
    public int amountOfChangingPosition() {

        while (endedProcesses < list.size()) {

            for (Process p :
                    list) {
                if (!p.getisDone() && p.getMomentOfEnter() == actualTime && !queue.contains(p)) {
                    queue.add(p);
                }
            }

            if (!queue.isEmpty()) {
                queue.sort(Comparator.comparingInt(Process::getMomentOfEnter));

                wholeAmountOfChangingPosition += Math.abs(queue.get(0).getPlaceOnDisk() - actPosition);
                actPosition = queue.get(0).getPlaceOnDisk();
                actualTime = queue.get(0).getMomentOfEnter();
                queue.get(0).setDone(true);
                queue.remove(0);

                endedProcesses++;
            }

            actualTime++;
        }

        return wholeAmountOfChangingPosition;
    }
}
