package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Scan implements Algorithm {

    private final int diskSize;
    ArrayList<Process> list;
    ArrayList<Process> queue;

    private int wholeAmountOfChangingPosition;
    private int actPosition;
    private int actualTime;
    private int endedProcesses;

    private boolean isGoingRight;


    public Scan(int diskSize, ArrayList<Process> processes) {
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
        wholeAmountOfChangingPosition = 0;

        isGoingRight = true;
    }

    @Override
    public int amountOfChangingPosition() {

        Process actProcess;

        while (endedProcesses < list.size()) {

            // ================= pomysł z flagą ===============


            for (Process p:
                    list) {
                if (!p.getisDone() && !queue.contains(p) && p.getMomentOfEnter() <= actualTime)
                    queue.add(p);
            }

            if (!queue.isEmpty()) {

                // idziemy w prawo
                if (isGoingRight) {

                    queue.sort(Comparator.comparingInt(Process::getPlaceOnDisk));

                    // odbicie się od prawej i pójście w lewo
                    if(queue.get(queue.size()-1).getPlaceOnDisk() < actPosition) {
                        isGoingRight = false;
                    }

                    else {
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

                }

                // idziemy w lewo
                else {

                    queue.sort((o1, o2) -> Integer.compare(o2.getPlaceOnDisk(), o1.getPlaceOnDisk()));

                    // odbicie się od ściany i pójście w prawo
                    if (queue.get(queue.size()-1).getPlaceOnDisk() > actPosition) {
                        isGoingRight = true;
                    }

                    else {
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

            }

            actualTime++;
        }


        return wholeAmountOfChangingPosition;
    }
}
