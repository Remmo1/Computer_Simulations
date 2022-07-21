package com.company;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        ArrayList<Page> list = new ArrayList<>(12);

        Page p1 = new Page(1);
        Page p2 = new Page(2);
        Page p3 = new Page(3);
        Page p4 = new Page(4);
        Page p5 = new Page(1);
        Page p6 = new Page(2);
        Page p7 = new Page(5);
        Page p8 = new Page(1);
        Page p9 = new Page(2);
        Page p10 = new Page(3);
        Page p11 = new Page(4);
        Page p12 = new Page(5);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        list.add(p7);
        list.add(p8);
        list.add(p9);
        list.add(p10);
        list.add(p11);
        list.add(p12);


        FIFO fifo = new FIFO(list, 4);
        System.out.println(fifo.amountOfPageErrors());

        OPT opt = new OPT(list, 4);
        System.out.println(opt.amountOfPageErrors());

        LRU lru = new LRU(list, 4);
        System.out.println(lru.amountOfPageErrors());

        ALRU alru = new ALRU(list,4);
        System.out.println(alru.amountOfPageErrors());

        RAND rand = new RAND(list,4);
        System.out.println(rand.amountOfPageErrors());


    }
}
