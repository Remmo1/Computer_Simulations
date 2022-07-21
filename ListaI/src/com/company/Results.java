package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Results {

    private List<Result> avrResults(float quantum, int amountOfCycles, int amountOfProcesess) {

        List <Result> list = new ArrayList<>();
        ArrayList<Process> processes = new ArrayList<>();

        float sumFCFS = 0;
        float sumSJF = 0;
        float sumSRTF = 0;
        float sumRR = 0;

        FCFS fcfs = new FCFS();
        SJF sjf = new SJF();
        SRTF srtf = new SRTF();
        RR rr = new RR();

        for (int i = 0; i < amountOfCycles; i++) {
            for (int j = 0; j < amountOfProcesess; j++) {
                Random r = new Random();
                int d = r.nextInt(10);
                int m = r.nextInt(20) + 1;
                processes.add(new Process(j+1,d,m,m,0));
            }

            System.out.print("FCFS: " + fcfs.FCFS_algorithm(processes));
            System.out.print(" SJF: " + sjf.SJF_algorithm(processes));
            System.out.print(" SRTF: " + srtf.SRTF_algorithm(processes));
            System.out.print(" RR: " + rr.RR_algorithm(quantum, processes));
            System.out.println();

            sumFCFS += fcfs.FCFS_algorithm(processes);
            sumSJF += sjf.SJF_algorithm(processes);
            sumSRTF += srtf.SRTF_algorithm(processes);
            sumRR += rr.RR_algorithm(quantum, processes);
            processes.clear();
        }

        list.add(new Result("FCFS", sumFCFS/amountOfCycles));
        list.add(new Result("SJF", sumSJF/amountOfCycles));
        list.add(new Result("SRTF", sumSRTF/amountOfCycles));
        list.add(new Result("RR", sumRR/amountOfCycles));
        System.out.println("\nUśrednione wszystkie wyniki:");
        return list;

    }

    public static void main(String[] args) {
        Results r = new Results();

        System.out.println(r.avrResults(5f,20,50));
    }
}
