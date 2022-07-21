package com.company;

import java.util.ArrayList;

public class Zone {
    private final int amountOfFrames;
    private final int workingSetSize = 6;

    private final ArrayList<Process> processes;
    private final ArrayList<Integer> results;

    private final ArrayList<Page> actualPages;


    public Zone(int amountOfFrames, ArrayList<Process> processes) {
        this.amountOfFrames = amountOfFrames;
        this.processes = processes;
        results = new ArrayList<>();
        actualPages = new ArrayList<>();

        for (Process p :
                processes) {
            p.setAmountOfFrames(amountOfFrames / processes.size());
        }

    }

    public ArrayList<Integer> amountOfPageErrors() {

        Proportional start = new Proportional(amountOfFrames, processes);
        start.sharingFrames();

        for (int i = 0; i < processes.get(0).getPages().size(); i++) {

            // przechowanie aktualnych stron dla każdego procesu
            for (Process p :
                    processes) {
                actualPages.add(p.getPages().get(i));
            }

            int j = 0;
            for (Process p :
                    processes) {

                // brak strony i można ją dodać
                if (!p.containsPageInFrame(actualPages.get(j)) && p.getActualPagesInFrames().size() < p.getAmountOfFrames()) {
                    p.setAmountOfPageErrors(p.getAmountOfPageErrors()+1);
                    p.getActualPagesInFrames().add(actualPages.get(j));
                    p.workingSet.add(actualPages.get(j));
                }

                // brak strony i NIE można jej dodać
                else if (!p.containsPageInFrame(actualPages.get(j))) {
                    p.setAmountOfPageErrors(p.getAmountOfPageErrors()+1);
                    p.workingSet.add(actualPages.get(j));

                    for (int k = 0; k < p.getActualPagesInFrames().size()-1; k++) {
                        p.getActualPagesInFrames().set(k, p.getActualPagesInFrames().get(k+1));
                    }
                    p.getActualPagesInFrames().set(p.getActualPagesInFrames().size()-1, actualPages.get(j));
                }

                // strona jest
                else {
                    for (int k = 0; k < p.getActualPagesInFrames().size(); k++) {
                        if (p.getActualPagesInFrames().get(k).getPageNumber() == actualPages.get(j).getPageNumber()) {
                            for (int l = k; l < p.getActualPagesInFrames().size() - 1; l++) {
                                p.getActualPagesInFrames().set(l, p.getActualPagesInFrames().get(l+1));
                            }
                            p.getActualPagesInFrames().set(p.getActualPagesInFrames().size()-1, actualPages.get(j));
                            break;
                        }
                    }
                }

                j++;
            }


            if ((i+1) % workingSetSize == 0) {
                for (Process p :
                        processes) {
                    p.setAmountOfFrames(p.workingSet.size());
                    p.workingSet.clear();
                }
            }

            actualPages.clear();

        }

        for (Process p :
                processes) {
            results.add(p.getAmountOfPageErrors());
        }

        return results;
    }
}
