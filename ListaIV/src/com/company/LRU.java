package com.company;

import java.util.ArrayList;

public class LRU {

    private int pageErrors;
    private final int amountOfFrames;
    private int endedPages;

    private final ArrayList<Page> frames;
    private final ArrayList<Page> list;
    private final int[] framesNumbers;
    private final int[][] framesLastTimeUsed;

    private static boolean containsForTable(int[] t, int search) {
        for (int j : t) {
            if (j == search)
                return true;
        }
        return false;
    }

    public LRU(ArrayList<Page> pages, int amountOfFrames) {

        list = new ArrayList<>();
        frames = new ArrayList<>(amountOfFrames);
        framesNumbers = new int[amountOfFrames];
        framesLastTimeUsed = new int[amountOfFrames][1];

        this.amountOfFrames = amountOfFrames;
        pageErrors = 0;
        endedPages = 0;

        for (Page p : pages){
            list.add(new Page(p.getPageNumber()));
        }
    }

    public int amountOfPageErrors() {

        int i = 0, h = 0;
        int actualSizeOfFrames = 0;
        Page actPage;

        while(endedPages < list.size()) {

            actPage = list.get(i);

            for (int j = 0; j < actualSizeOfFrames; j++) {
                framesNumbers[j] = frames.get(j).getPageNumber();
            }

            if (!containsForTable(framesNumbers, actPage.getPageNumber())) {


                if (frames.size() == amountOfFrames) {

                    int mi = 100000, index = 0;

                    for (int j = 0; j < amountOfFrames; j++) {
                        if (framesLastTimeUsed[j][0] < mi) {
                            mi = framesLastTimeUsed[j][0];
                            index = j;
                        }
                    }

                    frames.set(index, actPage);
                    framesLastTimeUsed[index][0] = i;
                }

                else {
                    actPage.setLastTimeUsed();
                    frames.add(actPage);
                    framesLastTimeUsed[h][0] = i;
                    actualSizeOfFrames++;

                    h++;
                }

                pageErrors++;

            }

            else {
                for (int j = 0; j < amountOfFrames; j++) {
                    if (actPage.getPageNumber() == framesNumbers[j]) {
                        framesLastTimeUsed[j][0] = i;
                        break;
                    }

                }
            }




            i++;
            endedPages++;
            actPage.setDone();
        }


        return pageErrors;
    }
}

