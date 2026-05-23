package com.mysteryticking;

import java.util.Arrays;
import java.util.List;

public class StateMachineEntity implements TickListener {
    private final String entityName;
    private final int startTick;
    private final List<State> states;
    private final int[] entryPoints;
    private final int cycleLength;

    public StateMachineEntity(String entityName,
                              int startTick,
                              State... states){
        if(entityName == null || entityName.isEmpty()) {
            throw new IllegalArgumentException("Entity name has been entered as null/empty.");
        }
        if(startTick < 0) {
            throw new IllegalArgumentException("Starting tick has to be greater than zero.");
        }
        if(states == null) {
            throw new IllegalArgumentException("States have been entered as null.");
        }
        if (states.length == 0) {
            throw new IllegalArgumentException("at least one state required");}
        this.entityName = entityName;
        this.startTick = startTick;
        this.entryPoints = new int[states.length];
        this.states = List.copyOf(Arrays.asList(states));
        int sum = 0;
        for (int i = 0; i < states.length; i++) {
            entryPoints[i] = sum;
            sum += states[i].cost();
        }
        this.cycleLength = sum;
    }

    private int checkPosition(int position){
        for(int j = 0; j < entryPoints.length; j++){
            if(entryPoints[j] == position){
                return j;
            }
        }
        return -1;
    }

    @Override public void onTick(int tick){
        if(tick >= startTick){
            int posInCycle = (tick - startTick)% cycleLength;
            int n = checkPosition(posInCycle);
            if(n !=-1 && !states.get(n).name().isEmpty()){
                System.out.println(states.get(n).name());
            }
        }
    }
    @Override public String toString(){
        return entityName;
    }
}