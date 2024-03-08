package org.trafic;

import java.io.IOException;

public class ThreadClass implements Runnable {
    static int time = 0;
    static boolean isSystem;


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time++;
            if (isSystem) {
                System.out.println();
//                System.out.println(time + "s. have passed since system startup !");
//                System.out.println("Number of roads: "+Main.numberOfRoads);
//                System.out.println("Interval: " + Main.interval + "\n");
                for (String road : Main.roads) {
                    System.out.println("Road " + road + " will be ");
                }
                System.out.println();
                System.out.println(" Press \"Enter\" to open menu !");
            }
        }
    }
}
