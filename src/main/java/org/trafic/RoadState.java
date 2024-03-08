package org.trafic;

public enum RoadState {
    OPEN("open"),
    CLOSED("closed");

    private final String state;

    RoadState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
