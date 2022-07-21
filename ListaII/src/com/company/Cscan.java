package com.company;

import java.util.ArrayList;

public class Cscan implements Algorithm {

    private final int diskSize;
    ArrayList<Process> list;
    ArrayList<Process> queue;

    private int wholeAmountOfChangingPosition;
    private int actPosition;
    private int actualTime;
    private int endedProcesses;

    public Cscan(int diskSize, ArrayList<Process> processes) {
        this.diskSize = diskSize;
        list = new ArrayList<>();
        queue = new ArrayList<>();

        for (Process p :
                processes) {
            list.add(new Process(p.getPlaceOnDisk(), p.getMomentOfEnter()));
        }

        actPosition = 1;
        actualTime = 0;
        endedProcesses = 0;
    }

    @Override
    public int amountOfChangingPosition() {

        Process actualProcess;

        while (endedProcesses < list.size()) {

            actualProcess = null;

            if (list.get(0).getMomentOfEnter() > actualTime)
                actualTime = list.get(0).getMomentOfEnter();

            for (Process p:
                 list) {
                if (!p.getisDone() && p.getMomentOfEnter() <= actualTime)
                    queue.add(p);
            }

            queue.sort((o1, o2) -> {
                if (o1.getMomentOfEnter() == o2.getMomentOfEnter())
                    return Integer.compare(o1.getPlaceOnDisk(), o2.getPlaceOnDisk());
                return Integer.compare(o1.getMomentOfEnter(), o2.getMomentOfEnter());
            });

            for (Process p :
                    queue) {

                if (p.getPlaceOnDisk() >= actPosition) {
                    actualProcess = p;
                    break;
                }

            }


            if (!queue.isEmpty()) {
                if (actualProcess == null) {
                    wholeAmountOfChangingPosition += Math.abs(diskSize - actPosition) + queue.get(0).getPlaceOnDisk();
                }

                else {
                    wholeAmountOfChangingPosition += Math.abs(queue.get(0).getPlaceOnDisk() - actPosition);
                }

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
