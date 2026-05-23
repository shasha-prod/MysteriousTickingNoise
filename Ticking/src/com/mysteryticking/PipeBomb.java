package com.mysteryticking;

import java.util.ArrayList;
import java.util.List;

public class PipeBomb implements Observable {
    private final int countdown;
    private final List<TickListener> listeners;

    public PipeBomb(int countdown){
        if (countdown < 1){ throw new IllegalArgumentException("The countdown entered must be at least 1."); }
        this.countdown = countdown;
        this.listeners = new ArrayList<>();
    }
    @Override public void register(TickListener listener){
        listeners.add(listener);
    }
    @Override public void notifyListeners(int tick){
        for(TickListener listener : listeners){
            listener.onTick(tick);
        }
    }
    public void start(){
        for (int tick = 1; tick <= countdown; tick++) {
            notifyListeners(tick);
        }
        System.out.println("BOOM!");
    }
}
