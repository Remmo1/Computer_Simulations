package com.company;

import java.util.ArrayList;
import java.util.Random;

public class FdScan implements Algorithm {

    private final int diskSize;
    ArrayList<Process> list;
    ArrayList<Process> queue;

    private int wholeAmountOfChangingPosition;
    private int actPosition;
    private int actualTime;
    private int endedProcesses;

    private boolean isGoingRight;

    public FdScan(int diskSize, ArrayList<Process> processes) {
        this.diskSize = diskSize;
        list = new ArrayList<>();
        queue = new ArrayList<>();
        Random r = new Random();


        for (Process p :
                processes) {
            list.add(new Process(p.getPlaceOnDisk(), p.getMomentOfEnter()));
        }


        actPosition = 1;
        endedProcesses = 0;
        actualTime = 0;
        wholeAmountOfChangingPosition = 0;

        isGoingRight = true;
    }

    @Override
    public int amountOfChangingPosition() {

        Process actProcess;
        int actDeadline;

        while (endedProcesses < list.size()) {

            for (Process p :
                    list) {
                if (!p.getisDone() && !queue.contains(p) && p.getMomentOfEnter() <= actualTime) {
                    queue.add(p);
                }
            }

            if (!queue.isEmpty()) {

                // sprawdzenie w którą stronę iść:
                if (isGoingRight) {

                    queue.sort((o1, o2) -> {
                        if (o1.getDeadlineTime() == o2.getDeadlineTime())
                            return Integer.compare(o1.getPlaceOnDisk(), o2.getPlaceOnDisk());
                        return Integer.compare(o1.getDeadlineTime(), o2.getDeadlineTime());
                    });

                    actDeadline = queue.get(0).getDeadlineTime();
                    actProcess = null;

                    for (Process p :
                            queue) {
                        if (p.getDeadlineTime() != actDeadline)
                            break;
                        actProcess = p;
                        actDeadline = p.getDeadlineTime();
                    }

                    if (actProcess.getPlaceOnDisk() < actPosition)
                        isGoingRight = false;
                }

                else {

                    queue.sort((o1, o2) -> {
                        if (o1.getDeadlineTime() == o2.getDeadlineTime())
                            return Integer.compare(o2.getPlaceOnDisk(), o1.getPlaceOnDisk());
                        return Integer.compare(o1.getDeadlineTime(), o2.getDeadlineTime());
                    });

                    actDeadline = queue.get(0).getDeadlineTime();
                    actProcess = null;

                    for (Process p :
                            queue) {
                        if (p.getDeadlineTime() != actDeadline)
                            break;
                        actProcess = p;
                        actDeadline = p.getDeadlineTime();
                    }

                    if (actProcess.getPlaceOnDisk() > actPosition)
                        isGoingRight = true;
                }

                // przemieszczenie igły w prawo
                if (isGoingRight) {

                    queue.sort((o1, o2) -> {
                        if (o1.getDeadlineTime() == o2.getDeadlineTime())
                            return Integer.compare(o1.getPlaceOnDisk(), o2.getPlaceOnDisk());
                        return Integer.compare(o1.getDeadlineTime(), o2.getDeadlineTime());
                    });

                    actProcess = null;

                    for (Process p :
                            queue) {
                        if (p.getPlaceOnDisk() >= actPosition) {
                            actProcess = p;
                            break;
                        }
                    }

                    wholeAmountOfChangingPosition += Math.abs(actProcess.getPlaceOnDisk() - actPosition);
                    actProcess.setDone(true);
                    actPosition = actProcess.getPlaceOnDisk();
                    queue.remove(actProcess);
                    endedProcesses++;

                }

                // przemieszczenie igły w lewo
                else {

                    queue.sort((o1, o2) -> {
                        if (o1.getDeadlineTime() == o2.getDeadlineTime())
                            return Integer.compare(o2.getPlaceOnDisk(), o1.getPlaceOnDisk());
                        return Integer.compare(o1.getDeadlineTime(), o2.getDeadlineTime());
                    });

                    actProcess = null;

                    for (Process p :
                            queue) {
                        if (p.getPlaceOnDisk() <= actPosition) {
                            actProcess = p;
                            break;
                        }
                    }

                    wholeAmountOfChangingPosition += Math.abs(actProcess.getPlaceOnDisk() - actPosition);
                    actProcess.setDone(true);
                    actPosition = actProcess.getPlaceOnDisk();
                    queue.remove(actProcess);
                    endedProcesses++;
                }
            }


            actualTime++;
        }


        return wholeAmountOfChangingPosition;
    }
}
