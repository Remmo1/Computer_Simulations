package com.company;

import java.util.ArrayList;

public class FIFO {

    private int pageErrors;
    private final int amountOfFrames;
    private int endedPages;

    private final ArrayList<Page> frames;
    private final ArrayList<Page> list;
    private final int[] framesNumbers;

    private static boolean containsForTable(int[] t, int search) {
        for (int j : t) {
            if (j == search)
                return true;
        }
        return false;
    }

    public FIFO(ArrayList<Page> pages, int amountOfFrames) {

        list = new ArrayList<>();
        frames = new ArrayList<>(amountOfFrames);
        framesNumbers = new int[amountOfFrames];

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
            }

            if (!containsForTable(framesNumbers, actPage.getPageNumber())) {

                for (Page p :
                        frames) {
                    p.setTimeInFrame(p.getTimeInFrame()+1);
                }

                if (frames.size() == amountOfFrames) {
                    frames.sort((p1,p2) -> Integer.compare(p2.getTimeInFrame(), p1.getTimeInFrame()));
                    frames.set(0, actPage);

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
