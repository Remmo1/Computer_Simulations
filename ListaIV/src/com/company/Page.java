package com.company;

public class Page {
    private final int pageNumber;
    private final int timeInFrame;

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
        timeInFrame = 0;
    }

    public int getPageNumber() { return pageNumber; }

    public void setDone() {
    }
    public void setLastTimeUsed() {
    }

    @Override
    public String toString() {
        return pageNumber + " " + timeInFrame;
    }
}
