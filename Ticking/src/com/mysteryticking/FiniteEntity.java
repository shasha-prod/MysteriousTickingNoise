package com.mysteryticking;

public class FiniteEntity implements TickListener {
    private final TickListener wrapped;
    private final Observable observable;
    private final int maxFires;
    private int fireCount;

    public FiniteEntity(TickListener wrapped, Observable observable, int maxFires) {
        this.wrapped = wrapped;
        this.observable = observable;
        this.maxFires = maxFires;
        this.fireCount = 0;
    }

    @Override
    public void onTick(int tick) {
        if (fireCount >= maxFires) return;
        wrapped.onTick(tick);
        fireCount++;
        if (fireCount >= maxFires) {
            observable.unregister(this);
        }
    }
}
