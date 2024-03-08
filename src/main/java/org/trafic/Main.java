package org.trafic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static int numberOfRoads;
    public static int interval;
    public static boolean isExit = false;
    public static final LinkedList<Road> ROADS = new LinkedList<>();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Pattern NUMBERS_PATTERN = Pattern.compile("\\D+");
    private static final String MENU_INFO = "\n1. Add road\n" +
            "2. Delete road\n" +
            "3. Open system\n" +
            "0. Quit";


    public static void main(String[] args) {
        System.out.println("Welcome to the traffic management system!");
        managementSystem();
    }

    public static void managementSystem() {
        inputNumberOfRoads();
        inputInterval();
        startSystemInfo();
        menu();
    }

    private static void startSystemInfo() {
        Thread systemInfo = new Thread(new SystemInfo());
        systemInfo.start();
    }

    public static void clearScreen() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        while (!isExit) {
            clearScreen();
            System.out.println(MENU_INFO);
            int optionInTheMenu = SCANNER.nextInt();
            switch (optionInTheMenu) {
                case 1:
                    addRoad();
                    clearScreen();
                    break;
                case 2:
                    deleteRoad();
                    clearScreen();
                    break;
                case 3:
                    openSystem();
                    clearScreen();
                    break;
                case 0:
                    quit();
                    break;
                default:
                    System.out.println("Incorrect option");
            }
        }
    }

    public static void addRoad() {
        if (ROADS.size() == numberOfRoads) {
            System.out.println("Queue is full");
            return;
        }

        System.out.println("Input road name: ");
        String roadName = SCANNER.next();

        Road road = new Road(roadName, RoadState.CLOSED, Integer.MAX_VALUE);
        synchronized (ROADS) {
            ROADS.add(road);
        }
        System.out.println(roadName + " added");
    }

    public static void deleteRoad() {
        synchronized (ROADS) {
            if (ROADS.isEmpty()) {
                System.out.println("Queue is empty");
            } else {
                System.out.println("Road deleted " + ROADS.removeFirst().getName());
            }
        }
    }

    public static void openSystem() {
        SystemInfo.isSystem = true;
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SystemInfo.isSystem = false;
    }

    public static void quit() {
        System.out.println("Bye!");
        isExit = true;
    }

    public static void inputInterval() {
        System.out.print("Input the interval: ");
        String inputInterval;
        while (NUMBERS_PATTERN.matcher(inputInterval = SCANNER.next()).matches() || (interval = Integer.parseInt(inputInterval)) <= 0) {
            System.out.print("Error! Incorrect Input. Try again: ");
        }
    }

    public static void inputNumberOfRoads() {
        System.out.print("Input the number of roads: ");
        String inputNumberOfRoads;
        while (NUMBERS_PATTERN.matcher(inputNumberOfRoads = SCANNER.next()).matches() || (numberOfRoads = Integer.parseInt(inputNumberOfRoads)) <= 0) {
            System.out.print("Error! Incorrect Input. Try again: ");
        }
    }
}