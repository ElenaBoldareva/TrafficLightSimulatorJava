package org.trafic;

public class SystemInfo implements Runnable {
    private static int time = 0;
    public static boolean isSystem;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Override
    public void run() {
        while (!Main.isExit) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time++;
            updateRoadsState();
            if (isSystem) {
                printSystemInfo();
                printRoadInfo();
                System.out.println("! Press \"Enter\" to open menu !");
            }
        }
    }

    private void updateRoadsState() {
        synchronized (Main.ROADS) {
            // No roads
            if (Main.ROADS.isEmpty()) {
                return;
            }

            // Only 1 road
            if (Main.ROADS.size() == 1) {
                Road road = Main.ROADS.getFirst();
                switch (road.getState()) {
                    case OPEN:
                        road.decrementCounter();
                        if (road.getCounter() <= 0) {
                            road.setCounter(Main.interval);
                        }
                        break;
                    case CLOSED:
                        road.setState(RoadState.OPEN);
                        road.setCounter(Main.interval);
                        break;
                    default:
                        throw new IllegalStateException();
                }
                return;
            }

            // Many roads
            int openRoadIndex;
            int openRoadCounter;
            Road openRoad = getOpenRoad();
            if (openRoad != null) {
                // There is an open road
                openRoadIndex = Main.ROADS.indexOf(openRoad);
                openRoadCounter = openRoad.getCounter();
                if (--openRoadCounter <= 0) {
                    openRoadIndex++;
                    if (openRoadIndex == Main.ROADS.size()) {
                        openRoadIndex = 0;
                    }
                    openRoadCounter = Main.interval;
                }
            } else {
                // There are no open roads
                openRoadIndex = getMinCounterRoadIndex();
                openRoadCounter = Main.interval;
            }
            recalculateAllCounters(openRoadIndex, openRoadCounter);
        }
    }

    private void recalculateAllCounters(int openRoadIndex, int openRoadCounter) {
        Main.ROADS.get(openRoadIndex).setState(RoadState.OPEN);
        Main.ROADS.get(openRoadIndex).setCounter(openRoadCounter);
        int index = openRoadIndex;
        for (int i = 1; i < Main.ROADS.size(); i++) {
            index++;
            if (index == Main.ROADS.size()) {
                index = 0;
            }
            Main.ROADS.get(index).setState(RoadState.CLOSED);
            Main.ROADS.get(index).setCounter((i - 1) * Main.interval + openRoadCounter);
        }
    }

    private int getMinCounterRoadIndex() {
        int minCounter = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < Main.ROADS.size(); i++) {
            Road road = Main.ROADS.get(i);
            if (road.getCounter() < minCounter) {
                minCounter = road.getCounter();
                index = i;
            }
        }
        return index;
    }

    private Road getOpenRoad() {
        for (Road road : Main.ROADS) {
            if (road.getState() == RoadState.OPEN) {
                return road;
            }
        }
        return null;
    }

    private void printRoadInfo() {
        if (Main.ROADS.isEmpty()) {
            return;
        }
        for (Road road : Main.ROADS) {
            String color = road.getState() == RoadState.OPEN ? ANSI_GREEN : ANSI_RED;
            System.out.println(color + road.getName() + " will be " + road.getState().toString() + " for " + road.getCounter() + "s.");
        }
        System.out.println(ANSI_RESET);
    }

    private static void printSystemInfo() {
        System.out.println();
        System.out.println("! " + time + "s. have passed since system startup !");
        System.out.println("! Number of roads: " + Main.numberOfRoads);
        System.out.println("! Interval: " + Main.interval + "\n");
    }
}
