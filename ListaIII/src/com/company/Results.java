package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Results {

    private final ArrayList<Page> pages;
    private final int amountOfFrames;
    private final int amountOfPages;
    private final int amountOfTests;
    private final int maximalPageNumber;
    
    private final ArrayList<Double> FifoResult;
    private final ArrayList<Double> OptResult;
    private final ArrayList<Double> LruResult;
    private final ArrayList<Double> AlruResult;
    private final ArrayList<Double> RandResult;

    private double FIFO;
    private double OPT;
    private double LRU;
    private double ALRU;
    private double RAND;

    public Results(int amountOfTests, int amountOfFrames, int amountOfPages, int maximalPageNumber) {
        this.amountOfTests = amountOfTests;
        this.amountOfFrames = amountOfFrames;
        this.amountOfPages = amountOfPages;
        this.maximalPageNumber = maximalPageNumber;

        pages = new ArrayList<>(amountOfPages);

        FIFO = 0;
        OPT = 0;
        LRU = 0;
        ALRU = 0;
        RAND = 0;
        
        FifoResult = new ArrayList<>(amountOfTests);
        OptResult = new ArrayList<>(amountOfTests);
        LruResult = new ArrayList<>(amountOfTests);
        AlruResult = new ArrayList<>(amountOfTests);
        RandResult = new ArrayList<>(amountOfTests);
    }

    public void avrResults() {

        for (int i = 0; i < amountOfTests; i++) {

            Random r = new Random();
            for (int j = 0; j < amountOfPages; j++) {
                pages.add(new Page(r.nextInt(maximalPageNumber)));
            }

            double h;
            com.company.FIFO fifo = new FIFO(pages, amountOfFrames);
            com.company.OPT opt = new OPT(pages,amountOfFrames);
            com.company.LRU lru = new LRU(pages,amountOfFrames);
            com.company.ALRU alru = new ALRU(pages, amountOfFrames);
            com.company.RAND rand = new RAND(pages, amountOfFrames);

            h = fifo.amountOfPageErrors();
            FIFO += h;
            FifoResult.add(h);
            System.out.print("FIFO: " + h + " ");

            h = opt.amountOfPageErrors();
            OPT += h;
            OptResult.add(h);
            System.out.print("OPT: " + h + " ");

            h = lru.amountOfPageErrors();
            LRU += h;
            LruResult.add(h);
            System.out.print("LRU: " + h + " ");

            h = alru.amountOfPageErrors();
            ALRU += h;
            AlruResult.add(h);
            System.out.print("ALRU: " + h + " ");
            
            h = rand.amountOfPageErrors();
            RAND += h;
            RandResult.add(h);
            System.out.print("RAND: " + h + " ");

            System.out.println();
            pages.clear();

        }

        System.out.println("\nśrednie wyniki:");
        System.out.print("FIFO: " + FIFO/amountOfTests + " ");
        System.out.print("OPT: " + OPT/amountOfTests + " ");
        System.out.print("LRU: " + LRU/amountOfTests + " ");
        System.out.print("ALRU: " + ALRU/amountOfTests + " ");
        System.out.print("RAND: " + RAND/amountOfTests + " ");
        System.out.println("\n");

        System.out.println("odchylenie standardowe:");
        double p = 0;
        for (Double i :
                FifoResult) {
            p += Math.pow( (i - (FIFO/amountOfTests)),2);
        }
        System.out.print("FIFO: " + (int)Math.sqrt(p / (amountOfTests * (amountOfTests-1))) + " ");

        p = 0;
        for (Double i :
                OptResult) {
            p += Math.pow( (i - (OPT/amountOfTests)),2);
        }
        System.out.print("OPT: " + (int)Math.sqrt(p / (amountOfTests * (amountOfTests-1))) + " ");

        p = 0;
        for (Double i :
                LruResult) {
            p += Math.pow( (i - (LRU/amountOfTests)),2);
        }
        System.out.print("LRU: " + (int)Math.sqrt(p / (amountOfTests * (amountOfTests-1))) + " ");

        p = 0;
        for (Double i :
                AlruResult) {
            p += Math.pow( (i - (ALRU/amountOfTests)),2);
        }
        System.out.print("ALRU: " + (int)Math.sqrt(p / (amountOfTests * (amountOfTests-1))) + " ");

        p = 0;
        for (Double i :
                RandResult) {
            p += Math.pow( (i - (RAND/amountOfTests)),2);
        }
        System.out.println("RAND: " + (int)Math.sqrt(p / (amountOfTests * (amountOfTests-1))) + " ");



    }
}
