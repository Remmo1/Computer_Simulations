package com.company;

import java.util.ArrayList;
import java.util.HashSet;

public class Proportional {

    private final int amountOfFrames;
    private final ArrayList<Process> processes;
    private final ArrayList<Integer> results;

    private final ArrayList<HashSet<Integer>> uniqePageNumbers;

    public Proportional(int amountOfFrames, ArrayList<Process> processes) {
        this.amountOfFrames = amountOfFrames;
        this.processes = processes;
        results = new ArrayList<>();

        uniqePageNumbers = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            uniqePageNumbers.add(new HashSet<>());
        }
    }

    private int maximum(int[] t) {
        int ma = 0;
        int index = 0;

        for (int i = 0; i < t.length; i++) {
            if (t[i] > ma) {
                ma = t[i];
                index = i;
            }
        }

        return index;
    }

    public void sharingFrames() {
        int i = 0;

        for (Process p :
                processes) {
            for (int j = 0; j < p.getPages().size(); j++) {
                uniqePageNumbers.get(i).add(p.getPages().get(j).getPageNumber());
            }

            i++;
        }


        int k = 0;
        int[] t = new int[uniqePageNumbers.size()];

        for (int j = 0; j < uniqePageNumbers.size(); j++) {
            k += uniqePageNumbers.get(j).size();
            t[j] = uniqePageNumbers.get(j).size();
        }

        int counter = maximum(t);
        while (k > amountOfFrames) {
            t[counter]--;
            counter = maximum(t);
            k--;
        }

        counter = 0;
        for (Process pr :
                processes) {
            pr.setAmountOfFrames(t[counter]);
            counter++;
        }


    }

    public ArrayList<Integer> amountOfPageErrors() {

        LRU lru;
        sharingFrames();

        for (Process p :
                processes) {
            lru = new LRU(p.getPages(), p.getAmountOfFrames());
            int h = lru.amountOfPageErrors();
            results.add(h);
        }

        return results;
    }

    /*
    private final int amountOfFrames;

    private final ArrayList<Process> processes;
    private final ArrayList<Integer> results;

    private final ArrayList<Integer> framesNeeded;
    private final ArrayList<Page> pagesToChange;
    private final ArrayList<Page> pagesToAdd;

    private final ArrayList<Boolean> isMoreNeededSite;
    private final ArrayList<Page> actualPages;


    public Proportional(int amountOfFrames, ArrayList<Process> processes) {
        this.amountOfFrames = amountOfFrames;
        this.processes = processes;
        results = new ArrayList<>();
        framesNeeded = new ArrayList<>();
        pagesToChange = new ArrayList<>();
        pagesToAdd = new ArrayList<>();
        isMoreNeededSite = new ArrayList<>();
        actualPages = new ArrayList<>();
    }

    public ArrayList<Integer> amountOfPageErrors() {




        for (int i = 0; i < processes.get(0).getPages().size(); i++) {

            // przechowanie aktualnych stron dla każdego procesu
            for (Process p :
                    processes) {
                actualPages.add(p.getPages().get(i));
                isMoreNeededSite.add(true);
            }

            // zwiększanie errorów i łapanie stron do dodania/wymiany
            for (Process p:
                 processes) {
                if (!p.containsPageInFrame(p.getPages().get(i))) {
                    p.setAmountOfPageErrors(p.getAmountOfPageErrors()+1);

                    pagesToAdd.add(p.getPages().get(i));
                    pagesToChange.add(p.getPages().get(i));
                }

                else {
                    pagesToAdd.add(new Page(-1));
                    pagesToChange.add(new Page(-1));
                }

            }

            // liczenie ile ogółem potrzeba ramek
            int allNeeded = 0;
            for (Process p :
                    processes) {
                framesNeeded.add(p.getActualPagesInFrames().size());
                allNeeded += p.getActualPagesInFrames().size();
            }

            // jeśli potrzeba więcej niż jest to lru
            if (allNeeded >= amountOfFrames) {
                for (int j = 0; j < pagesToChange.size(); j++) {
                    if (pagesToChange.get(j).getPageNumber() != -1)
                        processes.get(j).lru(pagesToChange.get(j));
                }
            }

            // jeśli nie trzeba to dodajmy strony
            else {
                for (int j = 0; j < pagesToAdd.size(); j++) {
                    if (pagesToAdd.get(j).getPageNumber() != -1)
                        processes.get(j).addToActualPageInFrames(processes.get(j).getPages().get(i));
                }
            }

            // sprawdzenie czy strona będzie dalej używana jeśli nie to można ją wyrzucić
            int counter = 0;
            boolean exitEarlier;

            for (Process p :
                    processes) {
                exitEarlier = false;

                for (int j = i+1; j < processes.get(0).getPages().size(); j++) {
                    if (actualPages.get(counter).getPageNumber() == p.getPages().get(j).getPageNumber()) {
                        exitEarlier = true;
                        break;
                    }
                }

                if (!exitEarlier) {
                    p.actualPagesInFrames.remove(actualPages.get(counter));
                }

                counter++;
            }

            actualPages.clear();
            isMoreNeededSite.clear();
            pagesToChange.clear();
            framesNeeded.clear();
            pagesToAdd.clear();
        }

        for (Process p :
                processes) {
            results.add(p.getAmountOfPageErrors());
        }

        return results;
    }

     */
}
