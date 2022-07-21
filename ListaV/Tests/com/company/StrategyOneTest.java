package com.company;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrategyOneTest {
    public static void main(String[] args) {

        assertEquals(1, 3-2);

        // strategia 1: przykład normalny
        Processor P1 = new Processor("P1", 0);
        Processor P2 = new Processor("P2", 0);
        Processor P3 = new Processor("P3", 0);
        Processor P4 = new Processor("P4", 0);
        Processor P5 = new Processor("P5", 0);

        Process p1 = new Process(1,20,3,P1);
        Process p2 = new Process(1,50,5,P5);
        Process p3 = new Process(2,30,7,P2);
        Process p4 = new Process(2,10,5,P2);

        ArrayList<Processor> processors = new ArrayList<>();
        processors.add(P1);
        processors.add(P2);
        processors.add(P3);
        processors.add(P4);
        processors.add(P5);

        ArrayList<Process> processes = new ArrayList<>();
        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);

        StrategyOne strategyOne = new StrategyOne(processors, processes, 40, 3);
        strategyOne.showResults();


        // strategia 1: przykład przeciążony
        System.out.println();
        Processor P1a = new Processor("P1", 0);
        Processor P2b = new Processor("P2", 0);


        Process p1a = new Process(1,100,3,P1a);
        Process p2b = new Process(1,100,5,P2b);
        Process p3c = new Process(2,100,7,P1a);
        Process p4d = new Process(2,100,5,P2b);

        ArrayList<Processor> processors2 = new ArrayList<>();
        processors2.add(P1a);
        processors2.add(P2b);

        ArrayList<Process> processes2 = new ArrayList<>();
        processes2.add(p1a);
        processes2.add(p2b);
        processes2.add(p3c);
        processes2.add(p4d);

        StrategyOne strategyOne2 = new StrategyOne(processors2, processes2, 40, 1);
        strategyOne2.showResults();

        // strategia 1: przykład wredny
        System.out.println();
        Processor P1c = new Processor("P1c", 0);
        Processor P2c = new Processor("P2c", 0);

        Process p1c = new Process(1, 30, 5, P1c);
        Process p2c = new Process(1, 30, 5, P2c);
        Process p3cd = new Process(1, 80, 5, P1c);

        ArrayList<Processor> processors3 = new ArrayList<>();
        processors3.add(P1c);
        processors3.add(P2c);

        ArrayList<Process> processes3 = new ArrayList<>();
        processes3.add(p1c);
        processes3.add(p2c);
        processes3.add(p3cd);

        StrategyOne strategyOne3 = new StrategyOne(processors3, processes3, 40, 2);
        strategyOne3.showResults();



    }

}