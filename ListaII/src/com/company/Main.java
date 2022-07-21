package com.company;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        int diskSize = 20;

        /*

        // różnica między FCFS a SCAN
        Process p1 = new Process(10,1);
        Process p2 = new Process(15,2);
        Process p3 = new Process(3, 3);
        Process p4 = new Process(4,3);

         */

        /*

        // jak jeden mąż wszyscy liczą tak samo
        Process p1 = new Process(1,1);
        Process p2 = new Process(3,2);
        Process p3 = new Process(6,3);
        Process p4 = new Process(8,4);
        Process p5 = new Process(10,5);
        Process p6 = new Process(15,6);
        Process p7 = new Process(20,7);

         */


        // główny zestaw testowy
        Process p1 = new Process(12,1);
        Process p2 = new Process(18,2);
        Process p3 = new Process(7,3);
        Process p4 = new Process(5,4);
        Process p5 = new Process(1,5);
        Process p6 = new Process(20,6);
        Process p7 = new Process(14,7);





        ArrayList<Process> processes = new ArrayList<>();
        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);
        processes.add(p5);
        processes.add(p6);
        processes.add(p7);



        FCFS fcfs = new FCFS(processes);
        System.out.println(fcfs.amountOfChangingPosition());
        System.out.println();

        SSTF sstf = new SSTF(processes);
        System.out.println(sstf.amountOfChangingPosition());
        System.out.println();

        Scan scan = new Scan(diskSize, processes);
        System.out.println(scan.amountOfChangingPosition());
        System.out.println();

        Cscan cscan = new Cscan(diskSize, processes);
        System.out.println(cscan.amountOfChangingPosition());
        System.out.println();

        EDF edf = new EDF(diskSize, processes);
        System.out.println(edf.amountOfChangingPosition());
        System.out.println();

        FdScan fdScan = new FdScan(diskSize, processes);
        System.out.println(fdScan.amountOfChangingPosition());
        System.out.println();
    }
}
