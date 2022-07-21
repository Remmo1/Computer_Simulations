package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class SSTF implements Algorithm {

    private final ArrayList<Process> list = new ArrayList<>();
    private final ArrayList<Process> queue = new ArrayList<>();

    private int wholeAmountOfChangingPosition;
    private int actPosition;
    private int actualTime;
    private int endedProcesses;


    public SSTF(ArrayList<Process> processes) {
        for (Process p :
                processes) {
            list.add(new Process(p.getPlaceOnDisk(), p.getMomentOfEnter()));
        }

        queue.sort(Comparator.comparingInt(Process::getPlaceOnDisk));
        actPosition = 1;
        actualTime = 0;
        endedProcesses = 0;
        wholeAmountOfChangingPosition = 0;
    }

    @Override
    public int amountOfChangingPosition() {

        while(endedProcesses < list.size()) {

            if (actualTime < list.get(0).getMomentOfEnter()) {
                actualTime = list.get(0).getMomentOfEnter();
            }

            for (Process p :
                    list) {
                if (!p.getisDone() && p.getMomentOfEnter() <= actualTime) {
                    queue.add(p);
                }
            }

            if (!queue.isEmpty()) {
                queue.sort((o1, o2) -> {
                    if (o1.getMomentOfEnter() == o2.getMomentOfEnter()) {
                        int difference = Math.abs(o1.getPlaceOnDisk() - actPosition);
                        int difference2 = Math.abs(o2.getPlaceOnDisk() - actPosition);

                        return difference - difference2;
                    }

                    return Integer.compare(o1.getMomentOfEnter(), o2.getMomentOfEnter());
                });

                wholeAmountOfChangingPosition += Math.abs(queue.get(0).getPlaceOnDisk() - actPosition);
                queue.get(0).setDone(true);
                actPosition = queue.get(0).getPlaceOnDisk();
                actualTime = queue.get(0).getMomentOfEnter();
                queue.remove(0);

                endedProcesses++;
            }





            actualTime++;
        }

        return wholeAmountOfChangingPosition;
    }
}
