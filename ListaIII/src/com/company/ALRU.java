package com.company;

import java.util.ArrayList;

public class ALRU {

    private int pageErrors;
    private final int amountOfFrames;
    private int endedPages;

    private final ArrayList<Page> frames;
    private final ArrayList<Page> list;
    private final ArrayList<Integer> framesNumbers;
    private final ArrayList<Integer> queueOfBits;

    private static boolean containsForTable(int[] t, int search) {
        for (int j : t) {
            if (j == search)
                return true;
        }
        return false;
    }

    public ALRU(ArrayList<Page> pages, int amountOfFrames) {

        list = new ArrayList<>();
        frames = new ArrayList<>(amountOfFrames);
        framesNumbers = new ArrayList<>();
        queueOfBits = new ArrayList<>(amountOfFrames);

        for (int i = 0; i < amountOfFrames; i++) {
            queueOfBits.add(0);
            framesNumbers.add(0);
        }

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



            if (!framesNumbers.contains( actPage.getPageNumber()) ) {


                if (frames.size() == amountOfFrames) {

                    int actPageNumer = 0;

                    for (int j = 0; j < amountOfFrames; j++) {
                        actPageNumer = framesNumbers.get(j);
                        if (queueOfBits.get(j) == 0) {
                            break;
                        }

                        else {
                            for (int k = 0; k < amountOfFrames-1; k++) {
                                queueOfBits.set(k,queueOfBits.get(k+1));
                            }
                            queueOfBits.set(actualSizeOfFrames-1, 0);


                            for (int k = 0; k < amountOfFrames-1; k++) {
                                framesNumbers.set(k,framesNumbers.get(k+1));
                            }
                            framesNumbers.set(actualSizeOfFrames-1, actPageNumer);
                        }
                    }

                    for (int k = 0; k < amountOfFrames-1; k++) {
                        queueOfBits.set(k,queueOfBits.get(k+1));
                    }
                    queueOfBits.set(actualSizeOfFrames-1, 1);


                    for (int k = 0; k < amountOfFrames-1; k++) {
                        framesNumbers.set(k,framesNumbers.get(k+1));
                    }
                    framesNumbers.set(actualSizeOfFrames-1, actPage.getPageNumber());


                }

                else {
                    frames.add(actPage);
                    framesNumbers.set(h, actPage.getPageNumber());
                    queueOfBits.set(h,1);
                    actualSizeOfFrames++;

                    h++;
                }

                pageErrors++;

            }

            else {
                for (int j = 0; j < amountOfFrames; j++) {
                    if (actPage.getPageNumber() == framesNumbers.get(j)) {
                        queueOfBits.set(j,1);
                        break;
                    }

                }
            }


            int counter = 0;

            for (int j = 0; j < actualSizeOfFrames; j++) {
                if (queueOfBits.get(j) == 1) {
                    counter++;
                }
            }

            if (counter == amountOfFrames) {
                for (int j = 0; j < actualSizeOfFrames; j++) {
                    queueOfBits.set(j,0);
                }
            }



            i++;
            endedPages++;
            actPage.setDone(true);
        }


        return pageErrors;
    }
}
