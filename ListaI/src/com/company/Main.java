package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private final static int amountOfProcesses = 5;
    private final static List<Process> list = new ArrayList<>(amountOfProcesses);

    public static void Generate() {

        Random numer = new Random();
        Random numer2 = new Random();
        int p, p2;


        list.add(new Process(1, 4, 1, 1, 0));
        list.add(new Process(2, 2, 5, 5, 0));
        list.add(new Process(3, 0, 8, 8, 0));
        list.add(new Process(4, 5, 2, 2, 0));
        list.add(new Process(5, 7, 4, 4, 0));
       // list.add(new Process(6, 7, 3, 3, 0));





        /*
        for (int i = 0; i < amountOfProcesses; i++) {
            p = numer.nextInt(8);
            p2 = numer2.nextInt(4)+1;

            list.add(new Process(i+1,p ,p2 , p2, 0));
        }

         */
    }

    public static void main(String[] args) {
        Generate();

        System.out.println("=========Processes============");
        for (Process p :
                list) {
            System.out.println(p.toString());
        }
        System.out.println();

        System.out.println("=========FCFS============");
        FCFS fcfs = new FCFS();
        System.out.println("średni czas oczekiwania: " + fcfs.FCFS_algorithm((ArrayList<Process>) list));
        System.out.println();

        System.out.println("==========SJF============");
        SJF sjf = new SJF();
        System.out.println("średni czas oczekiwania: " + sjf.SJF_algorithm((ArrayList<Process>) list));
        System.out.println();


        System.out.println("==========SRTF============");
        SRTF srtf = new SRTF();
        System.out.println("średni czas oczekiwania: " + srtf.SRTF_algorithm((ArrayList<Process>) list));
        System.out.println();

        System.out.println("===========RR=============");
        RR rr = new RR();
        System.out.println("średni czas oczekiwania: " + rr.RR_algorithm(5f, (ArrayList<Process>) list));
        System.out.println();


    }
}
