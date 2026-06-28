package com.mysteryticking;

import java.util.function.Predicate;

public class ConditionalEntity implements TickListener {
    private final Predicate<Integer> predicate;
    private final String message;

    public ConditionalEntity(Predicate<Integer> predicate, String message) {
        this.predicate = predicate;
        this.message = message;
    }

    @Override
    public void onTick(int tick) {
        if (predicate.test(tick)) {
            System.out.println(message);
        }
    }
}
