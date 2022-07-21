package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private final int amountOfTests;            // ile razy puszczamy algorytm dla tych samych danych
    private final int N;                        // ilość procesorów
    private final int amountOfProcesses;        // ilość procesów
    private final int p;                        // próg maksymalny
    private final int r;                        // próg minimalny
    private final int z;                        // ilość procesorów do przeszukania w strategii 1
    private final int part;                     // część pracy którą dzielą się procesory w strategii 3

    public Simulation(int amountOfTests, int N, int amountOfProcesses, int p, int r, int z, int part) {
        this.amountOfTests = amountOfTests;
        this.N = N;
        this.amountOfProcesses = amountOfProcesses;
        this.p = p;
        this.r = r;
        this.z = z;
        this.part = part;
    }

    public void runSimulation() {

        for (int i = 0; i < amountOfTests; i++) {

            // generator procesorów
            ArrayList<Processor> processors = new ArrayList<>(N);
            StringBuilder name = new StringBuilder();

            for (int j = 0; j < N; j++) {
                name.append("P");
                name.append(j+1);
                processors.add(new Processor(name.toString(), 0));
                name.delete(0, name.length());
            }


            // generator procesów
            ArrayList<Process> processes = new ArrayList<>(amountOfProcesses);
            Random random = new Random();
            int momentOfEnter;
            int powerNeeded;
            int momentOfFinish;
            int processorNumber;

            for (int j = 0; j < amountOfProcesses; j++) {

                momentOfEnter = random.nextInt(N) + 1;
                powerNeeded = ( random.nextInt(100) + 1 ) % 100;
                momentOfFinish = momentOfEnter + random.nextInt(N);
                processorNumber = (random.nextInt(N) + 1) % N;

                processes.add(new Process(momentOfEnter, powerNeeded, momentOfFinish, processors.get(processorNumber)));
            }



            // strategie
            StrategyOne strategyOne = new StrategyOne(processors, processes, p, z);
            System.out.println("\n========= Strategia 1 ============");
            strategyOne.showData();
            strategyOne.showResults();
            strategyOne.clear();

            StrategyTwo strategyTwo = new StrategyTwo(processors, processes, p);
            System.out.println("\n========= Strategia 2 ============");
            strategyTwo.showData();
            strategyTwo.showResults();
            strategyTwo.clear();

            StrategyThree strategyThree = new StrategyThree(processors, processes, p, r, part);
            System.out.println("\n========= Strategia 3 ============");
            strategyThree.showData();
            strategyThree.showResults();
            strategyThree.clear();



        }

    }
}
