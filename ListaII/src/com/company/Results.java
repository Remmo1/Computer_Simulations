package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Results {

    private final int amountOfDisks;
    private final int diskSize;
    private final int percentOfProcessesOnRight;
    private final int amountOfRequests;

    private int FCFS_AVR;
    private int SSTF_AVR;
    private int SCAN_AVR;
    private int CSCAN_AVR;
    private int EDF_AVR;
    private int FDSCAN_AVR;

    public Results(int amountOfDisks, int amountOfRequests, int diskSize, int percentOfProcessesOnRight) {
        this.amountOfDisks = amountOfDisks;
        this.diskSize = diskSize;
        this.percentOfProcessesOnRight = percentOfProcessesOnRight;
        this.amountOfRequests = amountOfRequests;

        FCFS_AVR = 0;
        SSTF_AVR = 0;
        SCAN_AVR = 0;
        CSCAN_AVR = 0;
        EDF_AVR = 0;
        FDSCAN_AVR = 0;
    }

    public void ShowResults() {

        ArrayList<Process> processes = new ArrayList<>();

        FCFS fcfs;
        SSTF sstf;
        Scan scan;
        Cscan cscan;
        EDF edf;
        FdScan fdScan;

        Random r = new Random();

        Random rightR = new Random();
        Random leftR = new Random();


        for (int i = 0; i < amountOfDisks; i++) {

            // tworzenie procesów

            // procesy po prawej:
            for (int j = 0; j < ((0.01 * percentOfProcessesOnRight) * amountOfRequests); j++) {

                Process p = new Process(rightR.nextInt(diskSize/2) + diskSize/2, rightR.nextInt(diskSize));
                p.setDeadlineTime(p.getMomentOfEnter() + 5);
                processes.add(p);
            }

            // procesy po lewej:
            for (int j = 0; j < amountOfRequests - ((0.01 * percentOfProcessesOnRight) * amountOfRequests); j++) {

                Process p = new Process(leftR.nextInt(diskSize/2) + 1, leftR.nextInt(diskSize));
                p.setDeadlineTime(p.getMomentOfEnter() + 5);
                processes.add(p);
            }

            /* pokazanie że algorytm tworzenia procesów działa
            for (Process p :
                    processes) {
                System.out.println(p.toString());
            }


             */


            fcfs = new FCFS(processes);
            sstf = new SSTF(processes);
            scan = new Scan(diskSize, processes);
            cscan = new Cscan(diskSize, processes);
            edf = new EDF(diskSize, processes);
            fdScan = new FdScan(diskSize, processes);

            FCFS_AVR += fcfs.amountOfChangingPosition();
            SSTF_AVR += sstf.amountOfChangingPosition();
            SCAN_AVR += scan.amountOfChangingPosition();
            CSCAN_AVR += cscan.amountOfChangingPosition();
            EDF_AVR += edf.amountOfChangingPosition();
            FDSCAN_AVR += fdScan.amountOfChangingPosition();

            System.out.print("FCFS: " + fcfs.amountOfChangingPosition() + " ");
            System.out.print("SSTF: " + sstf.amountOfChangingPosition() + " ");
            System.out.print("SCAN: " + scan.amountOfChangingPosition() + " ");
            System.out.print("C-SCAN: " + cscan.amountOfChangingPosition() + " ");
            System.out.print("EDF: " + edf.amountOfChangingPosition() + " ");
            System.out.print("FDSCAN: " + fdScan.amountOfChangingPosition() + " ");
            System.out.println();
        }


        System.out.println();
        System.out.println("========== średnie wyniki =============");
        System.out.print("FCFS: " + FCFS_AVR/amountOfDisks + " ");
        System.out.print("SSTF: " + SSTF_AVR/amountOfDisks + " ");
        System.out.print("SCAN: " + SCAN_AVR/amountOfDisks + " ");
        System.out.print("C-SCAN: " + CSCAN_AVR/amountOfDisks + " ");
        System.out.print("EDF: " + EDF_AVR/amountOfDisks + " ");
        System.out.print("FDSCAN: " + FDSCAN_AVR/amountOfDisks + " ");
        System.out.println();
    }

}
