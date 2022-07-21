package com.company;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTwoTest {
    public static void main(String[] args) {

        assertEquals(4-2, 2);

        // strategia 2: przykład normalny
        Processor P1a = new Processor("P1b", 0);
        Processor P2a = new Processor("P2a", 0);
        Processor P3a = new Processor("P3a", 0);
        Processor P4a = new Processor("P4a", 0);
        Processor P5a = new Processor("P5a", 0);

        Process p1a = new Process(1,20,3,P1a);
        Process p2a = new Process(1,50,5,P5a);
        Process p3a = new Process(2,30,7,P2a);
        Process p4a = new Process(2,10,5,P2a);
        Process p5a  = new Process(3,70,5,P2a);

        ArrayList<Processor> processors = new ArrayList<>();
        processors.add(P1a);
        processors.add(P2a);
        processors.add(P3a);
        processors.add(P4a);
        processors.add(P5a);

        ArrayList<Process> processes = new ArrayList<>();
        processes.add(p1a);
        processes.add(p2a);
        processes.add(p3a);
        processes.add(p4a);
        processes.add(p5a);

        StrategyTwo strategyTwo = new StrategyTwo(processors, processes, 40);
        strategyTwo.showResults();



        // strategia 2: przykład przeciążony
        System.out.println();
        Processor P1b = new Processor("P1b", 0);
        Processor P2b = new Processor("P2b", 0);


        Process p1b = new Process(1,100,3,P1b);
        Process p2b = new Process(1,100,5,P2b);
        Process p3b = new Process(2,100,7,P1b);
        Process p4b = new Process(2,100,5,P2b);

        ArrayList<Processor> processors2 = new ArrayList<>();
        processors2.add(P1b);
        processors2.add(P2b);

        ArrayList<Process> processes2 = new ArrayList<>();
        processes2.add(p1b);
        processes2.add(p2b);
        processes2.add(p3b);
        processes2.add(p4b);

        StrategyTwo strategyTwo2 = new StrategyTwo(processors2, processes2, 40);
        strategyTwo2.showResults();


        // strategia 2: przykład wredny
        System.out.println();
        Processor P1c = new Processor("P1c", 0);
        Processor P2c = new Processor("P2c", 0);

        Process p1c = new Process(1, 30, 5, P1c);
        Process p2c = new Process(1, 30, 5, P2c);
        Process p3c = new Process(1, 80, 5, P1c);

        ArrayList<Processor> processors3 = new ArrayList<>();
        processors3.add(P1c);
        processors3.add(P2c);

        ArrayList<Process> processes3 = new ArrayList<>();
        processes3.add(p1c);
        processes3.add(p2c);
        processes3.add(p3c);

        StrategyTwo strategyTwo3 = new StrategyTwo(processors3, processes3, 40);
        strategyTwo3.showResults();


    }
}