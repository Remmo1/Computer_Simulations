package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Result {
    private final int amountOfTests;
    private final int amountOfProcesses;
    private final int amountOfFrames;
    private final int lengthOfPages;
    private final int maximalRange;

    private int avrSameAmountOfProces;
    private int avrProportional;
    private int avrSteering;
    private int avrZone;


    public Result(int amountOfTests, int amountOfProceses, int amountOfFrames, int lengthOfPages, int maximalRange) {
        this.amountOfTests = amountOfTests;
        this.amountOfProcesses = amountOfProceses;
        this.amountOfFrames = amountOfFrames;
        this.lengthOfPages = lengthOfPages;
        this.maximalRange = maximalRange;

        avrSameAmountOfProces = 0;
        avrProportional = 0;
        avrSteering = 0;
        avrZone = 0;
    }

    public void amountOfPageErrors() {



        for (int i = 0; i < amountOfTests; i++) {

            ArrayList<Process> processes = new ArrayList<>();
            for (int j = 0; j < amountOfProcesses; j++) {
                processes.add(new Process());
            }


            ArrayList<ArrayList<Page>> pages = new ArrayList<>(amountOfProcesses);
            for (int j = 0; j < amountOfProcesses; j++) {
                pages.add(new ArrayList<>());
            }

            // generator
            Random random = new Random();
            int a = maximalRange/amountOfProcesses;
            int delta = 0;

            for (int j = 0; j < amountOfProcesses; j++) {

                HashSet<Integer> hashSet = new HashSet<>();

                for (int k = 0; k < lengthOfPages/2; k++) {

                    Page p = new Page(random.nextInt(a) + delta + 1);
                    pages.get(j).add(p);
                    hashSet.add(p.getPageNumber());

                }

                Integer[] t = hashSet.toArray(new Integer[0]);

                for (int k = 0; k < lengthOfPages / 2; k++) {
                    int r = random.nextInt();
                    if (r < 0)
                        r *= (-1);

                    Page p = new Page(t[r%t.length]);
                    pages.get(j).add(p);
                }

                processes.get(j).add(pages.get(j));


                delta += a;
            }

            // algorytmy

            ArrayList<Integer> results;
            int sum = 0;

            // przydział równy:
            SameAmountOfFramesPerProcess sameAmountOfFramesPerProcess = new SameAmountOfFramesPerProcess(amountOfFrames, processes);
            results = sameAmountOfFramesPerProcess.amountOfPageErrors();

            System.out.print("Przydział równy: ");
            for (Integer k :
                    results) {
                sum += k;
            }
            System.out.println(sum);
            avrSameAmountOfProces += sum;
            results.clear();
            for (Process p :
                    processes) {
                p.setAmountOfPageErrors(0);
                p.getActualPagesInFrames().clear();
            }
            sum = 0;


            // przydział proporcjonalny:
            Proportional proportional = new Proportional(amountOfFrames, processes);
            results = proportional.amountOfPageErrors();
            System.out.print("Przydział proporcjonalny: ");
            for (Integer k :
                    results) {
                sum += k;
            }
            System.out.println(sum);
            avrProportional += sum;
            results.clear();
            for (Process p :
                    processes) {
                p.setAmountOfPageErrors(0);
                p.getActualPagesInFrames().clear();
            }
            sum = 0;


            // sterowanie częstością błędów strony
            Steering steering = new Steering(amountOfFrames, processes);
            results = steering.amountOfPageErrors();
            System.out.print("Przydział sterowany: ");
            for (Integer k :
                    results) {
                sum += k;
            }
            System.out.println(sum);
            avrSteering += sum;
            results.clear();
            for (Process p :
                    processes) {
                p.setAmountOfPageErrors(0);
                p.getActualPagesInFrames().clear();
            }
            sum = 0;



            // przydział strefowy:
            Zone zone = new Zone(amountOfFrames, processes);
            results = zone.amountOfPageErrors();
            System.out.print("Przydział strefowy: ");
            for (Integer k :
                    results) {
                sum += k;
            }
            System.out.println(sum);
            avrZone += sum;
            results.clear();
            for (Process p :
                    processes) {
                p.setAmountOfPageErrors(0);
                p.getActualPagesInFrames().clear();
            }


        }


        System.out.println("\nWyniki średnie:");
        System.out.println("Równy: " + avrSameAmountOfProces/amountOfTests);
        System.out.println("Proporcjonalny: " + avrProportional/amountOfTests);
        System.out.println("Sterowany: " + avrSteering/amountOfTests);
        System.out.println("Strefowy: " + avrZone/amountOfTests);

    }
}
