package com.company;

public class Page {
    private int pageNumber;
    private int timeInFrame;
    private int lastTimeUsed;
    private boolean isDone;

    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
        isDone = false;
        timeInFrame = 0;
        lastTimeUsed = 0;
    }

    public int getPageNumber() { return pageNumber; }
    public int getTimeInFrame() { return timeInFrame; }
    public int getLastTimeUsed() { return lastTimeUsed; }

    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
    public void setTimeInFrame(int timeInFrame) { this.timeInFrame = timeInFrame; }
    public void setDone(boolean done) { isDone = done; }
    public void setLastTimeUsed(int lastTimeUsed) { this.lastTimeUsed = lastTimeUsed; }

    @Override
    public String toString() {
        return pageNumber + " " + timeInFrame;
    }
}
