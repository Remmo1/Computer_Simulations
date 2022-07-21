package com.company;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        ArrayList<Integer> results;
        int allFrames = 12;

        Process p1 = new Process();
        Process p2 = new Process();
        Process p3 = new Process();

        // test
        ArrayList<Page> pages1 = new ArrayList<>();
        pages1.add(new Page(1));
        pages1.add(new Page(2));
        pages1.add(new Page(3));
        pages1.add(new Page(4));
        pages1.add(new Page(3));
        pages1.add(new Page(2));
        pages1.add(new Page(5));
        pages1.add(new Page(6));
        pages1.add(new Page(2));
        pages1.add(new Page(4));
        pages1.add(new Page(6));
        pages1.add(new Page(3));
        pages1.add(new Page(1));
        pages1.add(new Page(5));
        p1.add(pages1);

        ArrayList<Page> pages2 = new ArrayList<>();
        pages2.add(new Page(10));
        pages2.add(new Page(10));
        pages2.add(new Page(11));
        pages2.add(new Page(10));
        pages2.add(new Page(11));
        pages2.add(new Page(10));
        pages2.add(new Page(11));
        pages2.add(new Page(10));
        pages2.add(new Page(10));
        pages2.add(new Page(11));
        pages2.add(new Page(10));
        pages2.add(new Page(11));
        pages2.add(new Page(10));
        pages2.add(new Page(11));
        p2.add(pages2);

        ArrayList<Page> pages3 = new ArrayList<>();
        pages3.add(new Page(20));
        pages3.add(new Page(21));
        pages3.add(new Page(22));
        pages3.add(new Page(23));
        pages3.add(new Page(24));
        pages3.add(new Page(25));
        pages3.add(new Page(20));
        pages3.add(new Page(21));
        pages3.add(new Page(22));
        pages3.add(new Page(23));
        pages3.add(new Page(24));
        pages3.add(new Page(25));
        pages3.add(new Page(20));
        pages3.add(new Page(21));
        p3.add(pages3);

        processes.add(p1);
        processes.add(p2);
        processes.add(p3);



        // przydział równy:
        SameAmountOfFramesPerProcess sameAmountOfFramesPerProcess = new SameAmountOfFramesPerProcess(allFrames, processes);
        results = sameAmountOfFramesPerProcess.amountOfPageErrors();

        System.out.println("Przydział równy:");
        int counter = 1;
        for (Integer i :
                results) {
            System.out.println("Proces " + counter + " : " + i);
            counter++;
        }
        results.clear();
        p1.setAmountOfPageErrors(0);
        p1.getActualPagesInFrames().clear();
        p2.setAmountOfPageErrors(0);
        p2.getActualPagesInFrames().clear();
        p3.setAmountOfPageErrors(0);
        p3.getActualPagesInFrames().clear();
        System.out.println();


        // przydział proporcjonalny:
        Proportional proportional = new Proportional(allFrames, processes);
        results = proportional.amountOfPageErrors();
        System.out.println("Przydział proporcjonalny:");
        counter = 1;
        for (Integer i :
                results) {
            System.out.println("Proces " + counter + " : " + i);
            counter++;
        }
        results.clear();
        p1.setAmountOfPageErrors(0);
        p1.getActualPagesInFrames().clear();
        p2.setAmountOfPageErrors(0);
        p2.getActualPagesInFrames().clear();
        p3.setAmountOfPageErrors(0);
        p3.getActualPagesInFrames().clear();
        System.out.println();


        // sterowanie częstością błędów strony
        Steering steering = new Steering(allFrames, processes);
        results = steering.amountOfPageErrors();
        System.out.println("Przydział sterowany:");
        counter = 1;
        for (Integer i :
                results) {
            System.out.println("Proces " + counter + " : " + i);
            counter++;
        }
        results.clear();
        p1.setAmountOfPageErrors(0);
        p1.getActualPagesInFrames().clear();
        p2.setAmountOfPageErrors(0);
        p2.getActualPagesInFrames().clear();
        p3.setAmountOfPageErrors(0);
        p3.getActualPagesInFrames().clear();
        System.out.println();




        // przydział strefowy:
        Zone zone = new Zone(allFrames, processes);
        results = zone.amountOfPageErrors();
        System.out.println("Przydział strefowy:");
        counter = 1;
        for (Integer i :
                results) {
            System.out.println("Proces " + counter + " : " + i);
            counter++;
        }
        results.clear();
        p1.setAmountOfPageErrors(0);
        p1.getActualPagesInFrames().clear();
        p2.setAmountOfPageErrors(0);
        p2.getActualPagesInFrames().clear();
        p3.setAmountOfPageErrors(0);
        p3.getActualPagesInFrames().clear();
        System.out.println();


    }
}
