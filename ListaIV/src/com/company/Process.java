package com.company;

import java.util.ArrayList;
import java.util.HashSet;

public class Process {
    private final ArrayList<Page> pages;
    private int amountOfPageErrors;
    private int amountOfFrames;
    ArrayList<Page> actualPagesInFrames;

    HashSet<Page> workingSet;

    public Process() {
        pages = new ArrayList<>();
        amountOfPageErrors = 0;

        actualPagesInFrames = new ArrayList<>();

        workingSet = new HashSet<>();
    }

    public int getAmountOfPageErrors() { return amountOfPageErrors; }
    public ArrayList<Page> getPages() { return pages; }
    public int getAmountOfFrames() { return amountOfFrames; }
    public ArrayList<Page> getActualPagesInFrames() { return actualPagesInFrames; }

    public void setAmountOfFrames(int amountOfFrames) { this.amountOfFrames = amountOfFrames; }
    public void setAmountOfPageErrors(int amountOfPageErrors) { this.amountOfPageErrors = amountOfPageErrors; }

    public void add(ArrayList<Page> pagesToAdd) {
        pages.addAll(pagesToAdd);
    }

    public boolean containsPageInFrame(Page page) {
        for (Page p :
                actualPagesInFrames) {
            if (p.getPageNumber() == page.getPageNumber())
                return true;
        }

        return false;
    }

}
