package org.trafic;

public class Road {
    private String name;
    private RoadState state;
    private int counter;

    public Road(String name, RoadState state, int counter) {
        this.name = name;
        this.state = state;
        this.counter = counter;
    }

    public void decrementCounter() {
        counter--;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoadState getState() {
        return state;
    }

    public void setState(RoadState state) {
        this.state = state;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
