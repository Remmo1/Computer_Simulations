package com.company;

import java.util.ArrayList;

public class OPT {

    private int pageErrors;
    private final int amountOfFrames;
    private int endedPages;

    private final ArrayList<Page> frames;
    private final ArrayList<Page> list;
    private final int[] framesNumbers;
    private final int[][] distance;

    private static boolean containsForTable(int[] t, int search) {
        for (int j : t) {
            if (j == search)
                return true;
        }
        return false;
    }


    public OPT(ArrayList<Page> pages, int amountOfFrames) {

        list = new ArrayList<>();
        frames = new ArrayList<>(amountOfFrames);
        framesNumbers = new int[amountOfFrames];
        distance = new int[amountOfFrames][1];

        this.amountOfFrames = amountOfFrames;
        pageErrors = 0;
        endedPages = 0;

        for (Page p : pages){
            list.add(new Page(p.getPageNumber()));
        }
    }

    public int amountOfPageErrors() {

        int i = 0;
        int actualSizeOfFrames = 0;
        Page actPage;

        while(endedPages < list.size()) {

            actPage = list.get(i);

            for (int j = 0; j < actualSizeOfFrames; j++) {
                framesNumbers[j] = frames.get(j).getPageNumber();
                distance[j][0] = frames.get(j).getPageNumber();
            }

            if (!containsForTable(framesNumbers, actPage.getPageNumber())) {


                if (frames.size() == amountOfFrames) {
                    int p;
                    boolean isInList;
                    boolean exit = false;
                    int distanceFrom;
                    int index;
                    int maximum;

                    // trzeba policzyć odległość między kolejnymi użyciami dla każdej strony w ramce
                    for (int j = 0; j < amountOfFrames; j++) {
                        p = framesNumbers[j];
                        distanceFrom = 0;
                        isInList = false;
                        exit = false;

                        for (int k = i; k < list.size(); k++) {

                            if (list.get(k).getPageNumber() == p) {
                                isInList = true;
                                break;
                            }

                            else {
                                distanceFrom++;
                            }

                        }

                        // jeśli dana strona nie będzie już w ogóle używana to można ją od razu wymienić
                        if (!isInList) {
                            frames.set(j, actPage);
                            exit = true;
                            break;
                        }

                        distance[j][0] = distanceFrom;
                    }

                    if (!exit) {
                        maximum = 0;
                        index = 0;

                        for (int j = 0; j < amountOfFrames; j++) {
                            if(distance[j][0] > maximum) {
                                maximum = distance[j][0];
                                index = j;
                            }
                        }

                        frames.set(index, actPage);

                    }

                }

                else {
                    frames.add(actPage);
                    actualSizeOfFrames++;
                }

                pageErrors++;

            }




            i++;
            endedPages++;
            actPage.setDone(true);
        }


        return pageErrors;
    }
}
