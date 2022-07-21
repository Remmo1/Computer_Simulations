package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class EDF implements Algorithm {

    private final int diskSize;
    ArrayList<Process> list;
    ArrayList<Process> queue;

    private int wholeAmountOfChangingPosition;
    private int actPosition;
    private int actualTime;
    private int endedProcesses;

    public EDF(int diskSize, ArrayList<Process> processes) {
        this.diskSize = diskSize;
        list = new ArrayList<>();
        queue = new ArrayList<>();
        Random r = new Random();

        int i = 0;
        for (Process p :
                processes) {
            list.add(new Process(p.getPlaceOnDisk(), p.getMomentOfEnter()));
        }


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
                if (!p.getisDone() && !queue.contains(p) && p.getMomentOfEnter() <= actualTime) {
                    queue.add(p);
                }
            }

            if (!queue.isEmpty()) {
                queue.sort(Comparator.comparingInt(Process::getDeadlineTime));

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
