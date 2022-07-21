package com.company;

import java.util.ArrayList;

public class Steering {
    private final int amountOfFrames;

    private final ArrayList<Process> processes;
    private final ArrayList<Integer> results;

    private final int timeDelta = 4;
    private final ArrayList<ArrayList<Boolean>> wasErrorOrNot;
    private final ArrayList<Integer> errorsInDeltaTime;

    private final ArrayList<Page> actualPages;

    private final int minimal;
    private final int maximal;


    public Steering(int amountOfFrames, ArrayList<Process> processes) {
        this.amountOfFrames = amountOfFrames;
        this.processes = processes;
        results = new ArrayList<>();
        actualPages = new ArrayList<>();

        errorsInDeltaTime = new ArrayList<>(processes.size());
        for (int i = 0; i < processes.size(); i++) {
            errorsInDeltaTime.add(0);
        }

        wasErrorOrNot = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            wasErrorOrNot.add(new ArrayList<>());
        }
        for (int j = 0; j < processes.size(); j++) {
            for (int i = 0; i < processes.get(0).getPages().size(); i++) {
                wasErrorOrNot.get(j).add(false);
            }
        }


        maximal = timeDelta;
        minimal = 0;
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
                    p.setAmountOfPageErrors(p.getAmountOfPageErrors() + 1);
                    p.getActualPagesInFrames().add(actualPages.get(j));

                    wasErrorOrNot.get(j).set(i, true);
                }

                // brak strony i NIE można jej dodać
                else if (!p.containsPageInFrame(actualPages.get(j))) {
                    p.setAmountOfPageErrors(p.getAmountOfPageErrors() + 1);

                    for (int k = 0; k < p.getActualPagesInFrames().size() - 1; k++) {
                        p.getActualPagesInFrames().set(k, p.getActualPagesInFrames().get(k + 1));
                    }
                    p.getActualPagesInFrames().set(p.getActualPagesInFrames().size() - 1, actualPages.get(j));

                    wasErrorOrNot.get(j).set(i, true);
                }

                // strona jest
                else {
                    for (int k = 0; k < p.getActualPagesInFrames().size(); k++) {
                        if (p.getActualPagesInFrames().get(k).getPageNumber() == actualPages.get(j).getPageNumber()) {
                            for (int l = k; l < p.getActualPagesInFrames().size() - 1; l++) {
                                p.getActualPagesInFrames().set(l, p.getActualPagesInFrames().get(l + 1));
                            }
                            p.getActualPagesInFrames().set(p.getActualPagesInFrames().size() - 1, actualPages.get(j));
                            break;
                        }
                    }
                }

                j++;
            }

            // sprawdzenie ile było błędów strony w ostatnich deltaTime jednostkach czasu
            if (i+1 >= timeDelta) {
                for (int k = 0; k < processes.size(); k++) {
                    errorsInDeltaTime.set(k, 0);
                }

                int k = 0;
                for (Process ignored :
                        processes) {

                    for (int l = 0; l < timeDelta; l++) {
                        if (wasErrorOrNot.get(k).get(i - l))
                            errorsInDeltaTime.set(k, errorsInDeltaTime.get(k) + 1);
                    }

                    k++;
                }


                // dzielenie ramek
                k = 0;
                boolean steal = false;

                for (Integer ignored : errorsInDeltaTime) {
                    if (errorsInDeltaTime.get(k) <= minimal) {
                        processes.get(k).setAmountOfFrames(processes.get(k).getAmountOfFrames() - 1);
                        steal = true;
                        break;
                    }
                    k++;
                }

                k = 0;
                if (steal) {
                    for (Integer ignored : errorsInDeltaTime) {
                        if (errorsInDeltaTime.get(k) >= maximal) {
                            processes.get(k).setAmountOfFrames(processes.get(k).getAmountOfFrames() + 1);
                            break;
                        }
                        k++;
                    }
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

