package org.trafic;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Thread threadClass = new Thread(new ThreadClass());
    static String numberOfRoads;
    static ArrayDeque<String> roads = new ArrayDeque<>();
    static String interval;
    static Scanner scanner = new Scanner(System.in);
    static String regex = "\\D+";
    static Pattern pattern = Pattern.compile(regex);
    static String message = "MENU_INFO";/*+"\n1. Add road\n" +
            "2. Delete road\n" +
            "3. Open system\n" +
            "0. Quit";*/

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the traffic management system!");
        managementSystem();
    }

    public static void managementSystem() {
        inputNumberOfRoads();
        while (pattern.matcher(numberOfRoads = scanner.next()).matches() || Integer.parseInt(numberOfRoads) <= 0) {
            System.out.print("Error! Incorrect Input. Try again: ");
        }

        interval();
        while (pattern.matcher(interval = scanner.next()).matches() || Integer.parseInt(interval) <= 0) {
            System.out.print("Error! Incorrect Input. Try again: ");
        }

        threadClass.start();
        menu();
    }

    public static void clearScreen() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
        }
    }

    public static void menu() {
        while (true) {
            clearScreen();
            System.out.println(message);
            int optionInTheMenu = scanner.nextInt();
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
                    return;
                default:
                    System.out.println("Incorrect option");
            }
        }
    }


    public static void addRoad() {
        System.out.println("Input road name:");
        String nameRoad = scanner.next();
        roads.add(nameRoad);

        if (roads.size() > Integer.parseInt(numberOfRoads)) {
            System.out.println("Queue is full");
        } else System.out.println(nameRoad + " added");

    }

    public static void deleteRoad() {
        if (roads.isEmpty()) {
            System.out.println("Queue is empty");
        } else System.out.println("Road deleted " + roads.removeFirst());
    }

    public static void openSystem() {
        ThreadClass.isSystem = true;
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ThreadClass.isSystem = false;
    }

    public static void quit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    public static void interval() {
        System.out.print("Input the interval: ");
    }

    public static void inputNumberOfRoads() {
        System.out.print("Input the number of roads: ");
    }
}