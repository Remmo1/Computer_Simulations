package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class StrategyOne {

    private final ArrayList<Processor> processors;
    private final ArrayList<Process> processes;
    private final int p;
    private final int z;

    private int globalTime;

    private int migrationCounter;
    private int questionCounter;


    ArrayList <Double> avrList = new ArrayList<>();

    public StrategyOne(ArrayList<Processor> processors, ArrayList<Process> processes, int p, int z) {
        this.processors = processors;
        this.processes = processes;
        this.p = p;
        this.z = z;

        migrationCounter = 0;
        questionCounter = 0;

        globalTime = 0;


    }

    private void runStrategy() {

        // sortujemy po momencie wejścia
        processes.sort(Comparator.comparingInt(Process::getMomentOfEnter));

        ArrayList<Process> queueNewProcesses = new ArrayList<>();
        ArrayList<Process> notNewProcesses = new ArrayList<>();

        ArrayList<Process> waitingProcesses = new ArrayList<>();

        int endedProcesses = 0;
        int i = 0;

        double avr;


        while (endedProcesses < processes.size()) {

            // wrzucenie nowych procesów do kolejki
            for (int j = i; j < processes.size(); j++) {
                if (processes.get(j).getMomentOfEnter() <= globalTime) {
                    queueNewProcesses.add(processes.get(j));
                    i++;
                }
            }


            // algorytm
            if (!queueNewProcesses.isEmpty()) {

                avr = 0;

                for (Process pr : queueNewProcesses) {

                    boolean isProcessorWithPowerLessThanP = false;

                    // pytamy z razy czy można wrzucić proces na inny procesor
                    for (int j = 0; j < z; j++) {
                        Random random = new Random();
                        int processorNumber = (random.nextInt(processors.size())+1) % processors.size();
                        Processor asked = processors.get(processorNumber);

                        while (pr.getProcessorOnWhichAppears() == asked) {
                            processorNumber = (random.nextInt(processors.size())+1) % processors.size();
                            asked = processors.get(processorNumber);
                        }

                        questionCounter++;

                        // jeśli można wrzucić tzn. obciążenie mniejsze od p
                        // i jeszcze jeden warunek:
                        // nie można wrzucić procesu jeśli po wrzuceniu go moc procesora przekroczy 100%
                        if (asked.getPowerUsedCurrently() < p && pr.getPowerNeeded() + asked.getPowerUsedCurrently() <= 100) {
                            asked.setPowerUsedCurrently(asked.getPowerUsedCurrently() + pr.getPowerNeeded());
                            asked.processesOnProcessor.add(pr);
                            notNewProcesses.add(pr);

                            migrationCounter++;
                            isProcessorWithPowerLessThanP = true;
                            break;
                        }
                    }

                    // jeśli nie znajdziemy takiego to są dwie możliwości
                    if (!isProcessorWithPowerLessThanP) {

                        // wykonanie na procesorze na którym się pojawił
                        // i jeszcze jeden warunek:
                        // nie można wrzucić procesu jeśli po wrzuceniu go moc procesora przekroczy 100%
                        if (pr.getProcessorOnWhichAppears().getPowerUsedCurrently() < p && pr.getPowerNeeded() + pr.getProcessorOnWhichAppears().getPowerUsedCurrently() <= 100) {
                            Processor thisProcessor = pr.getProcessorOnWhichAppears();
                            thisProcessor.setPowerUsedCurrently(thisProcessor.getPowerUsedCurrently() + pr.getPowerNeeded());
                            thisProcessor.processesOnProcessor.add(pr);
                            notNewProcesses.add(pr);

                        }

                        // jeśli wszystko jest zajęte to wrzucamy do kolejki i czekamy aż coś się zwolni
                        else {
                            waitingProcesses.add(pr);
                        }
                    }
                }


                queueNewProcesses.removeIf(notNewProcesses::contains);



                // obliczenie avr
                for (Processor pr :
                        processors) {
                    avr += (pr.getPowerUsedCurrently() * pr.getPowerUsedCurrently());
                }

                avrList.add(Math.sqrt(avr/processors.size()));

            }

            // zakończenie procesów których moment zakończenia <= globalTime
            for (Processor pro :
                    processors) {
                Iterator<Process> iterator = pro.processesOnProcessor.iterator();

                while (iterator.hasNext()) {
                    Process pr = iterator.next();

                    if (pr.getMomentOfFinish() <= globalTime) {
                        pro.setPowerUsedCurrently(pro.getPowerUsedCurrently() - pr.getPowerNeeded());
                        iterator.remove();
                        endedProcesses++;
                    }
                }
            }

            if (!waitingProcesses.isEmpty()) {
                for (Process pr :
                        waitingProcesses) {
                    if (!queueNewProcesses.contains(pr))
                        queueNewProcesses.add(pr);
                }

                waitingProcesses.clear();
            }

            globalTime++;
        }
    }

    public void showData() {
        runStrategy();

        System.out.println("\nProcesory:");
        for (Processor processor :
                processors) {
            System.out.print(processor.toString() + "; ");
        }

        System.out.println("\nProcesy:");
        for (Process process :
                processes) {
            System.out.print(process.toString() + "; ");
        }

        System.out.println("\nWartości średniej kwadratowej w kolejnych chwilach czasu:");
        for (Double d : avrList){
            System.out.printf("%.2f", d);
            System.out.print(" ");
        }


    }

    public void showResults() {

        System.out.println();
        double avr = 0;
        for (Double d : avrList) {
            avr += (d*d);
        }

        avr = Math.sqrt(avr/avrList.size());
        System.out.print("średnie obciążenie procesorów: " );
        System.out.printf("%.2f", avr);

        // odchylenie standardowe
        double deviation = 0;
        for (Double d :
                avrList) {
            deviation += Math.pow(d - avr, 2);
        }

        deviation = deviation/(avrList.size() * avrList.size()-1);
        System.out.print("\nOdchylenie standardowe: ");
        System.out.printf("%.2f", Math.sqrt(deviation));

        System.out.println("\nilość zapytań: " + questionCounter);
        System.out.println("ilość migracji: " + migrationCounter);
    }



    public void clear() {
        avrList.clear();
        migrationCounter = 0;
        questionCounter = 0;

        for (Processor pro :
                processors) {
            pro.processesOnProcessor.clear();
            pro.setPowerUsedCurrently(0);
        }
    }

}
