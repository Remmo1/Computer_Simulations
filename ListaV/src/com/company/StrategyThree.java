package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class StrategyThree {
    private final ArrayList<Processor> processors;
    private final ArrayList<Process> processes;
    private final int p;
    private final int r;
    private final double part;

    private int globalTime;

    private int migrationCounter;
    private int questionCounter;

    ArrayList <Double> avrList = new ArrayList<>();

    public StrategyThree(ArrayList<Processor> processors, ArrayList<Process> processes, int p, int r, double part) {
        this.processors = processors;
        this.processes = processes;
        this.p = p;
        this.r = r;
        this.part = part/100;

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
        int sizeOfAllProcesses = processes.size();

        while (endedProcesses < sizeOfAllProcesses) {

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

                    boolean thereIsNoMoreSpace = true;
                    Processor thisProcessor = pr.getProcessorOnWhichAppears();

                    // jeśli obciążnie na procesorze na którym pojawia się proces jest mniejsze od p
                    // i jeszcze jeden warunek:
                    // nie można wrzucić procesu jeśli po wrzuceniu go moc procesora przekroczy 100%
                    if (thisProcessor.getPowerUsedCurrently() < p && pr.getPowerNeeded() + thisProcessor.getPowerUsedCurrently() <= 100) {
                        thisProcessor.setPowerUsedCurrently(thisProcessor.getPowerUsedCurrently() + pr.getPowerNeeded());
                        thisProcessor.processesOnProcessor.add(pr);
                        notNewProcesses.add(pr);

                        thereIsNoMoreSpace = false;
                    }

                    // jeśli jest większe to szukamy do skutku
                    else {

                        for (int j = 0; j < processors.size() - 1; j++) {
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
                                thereIsNoMoreSpace = false;
                                break;
                            }
                        }
                    }

                    // jeśli nie ma miejsca to wrzucamy do procesów czekających
                    if (thereIsNoMoreSpace) {
                        waitingProcesses.add(pr);
                    }
                }


                queueNewProcesses.removeIf(notNewProcesses::contains);

                // strategia 3, procesory o obciążeniu < r pytają losowo inne o obciążenie i jeśli jakiś
                // ma ponad p to dzielą się robotą
                for (Processor pro :
                        processors) {

                    // pro jest pytającym
                    if (pro.getPowerUsedCurrently() < r) {

                        // asked jest zapytanym
                        Random random = new Random();
                        int processorNumber = (random.nextInt(processors.size())+1) % processors.size();
                        Processor asked = processors.get(processorNumber);

                        // założenie: nie może pytać samego siebie
                        while (pro == asked) {
                            processorNumber = (random.nextInt(processors.size())+1) % processors.size();
                            asked = processors.get(processorNumber);
                        }

                        questionCounter++;

                        // jeśli obciążenie zapytanego większe od p to się dzielą
                        if (asked.getPowerUsedCurrently() > p) {

                            ArrayList<Process> processesStay = new ArrayList<>();
                            ArrayList<Process> processesGo = new ArrayList<>();

                            // rozbijamy procesy (może być ich kilka) na dwie kolejki
                            for (Process process :
                                    asked.processesOnProcessor) {
                                int h = (int) (process.getPowerNeeded() * (1-part));
                                processesStay.add(new Process(process.getMomentOfEnter(), h, process.getMomentOfFinish(), process.getProcessorOnWhichAppears()));
                                processesGo.add(new Process(process.getMomentOfEnter(), process.getPowerNeeded() - h, process.getMomentOfFinish(), process.getProcessorOnWhichAppears()));
                            }

                            asked.processesOnProcessor = processesStay;
                            pro.processesOnProcessor.addAll(processesGo);

                            // aktualizacja obciążenia procesora
                            int sum = 0;
                            for (Process process :
                                    processesStay) {
                                sum += process.getPowerNeeded();
                            }
                            asked.setPowerUsedCurrently(sum);

                            sum = 0;
                            for (Process process :
                                    processesGo) {
                                sum += process.getPowerNeeded();
                            }
                            pro.setPowerUsedCurrently(sum);

                            // aktualizacja liczby procesów
                            sizeOfAllProcesses += processesGo.size();

                            // zwiększenie liczby migracji
                            migrationCounter += processesGo.size();

                        }
                    }
                }

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
