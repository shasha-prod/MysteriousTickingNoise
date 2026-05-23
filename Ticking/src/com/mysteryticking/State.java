package com.mysteryticking;

public record State(String name, int cost) {
    public State {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name entered cannot be null or empty");
        }
        if(cost < 1) {
            throw new IllegalArgumentException("The cost entered cannot be zero or negative");
        }
    }
}
