package com.mysteryticking;

public record State(String name, int cost) {
    public State {
        if(name == null) {
            throw new IllegalArgumentException("The name entered cannot be null");
        }
        if(cost < 1) {
            throw new IllegalArgumentException("The cost entered cannot be zero or negative");
        }
    }
}
