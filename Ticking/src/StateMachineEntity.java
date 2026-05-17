import java.util.ArrayList;
import java.util.List;

public class StateMachineEntity implements TickListener {
    private final String entityName;
    private final int startTick;
    private final List<State> states;
    private int[] entryPoints;
    private int cycleLength = 0;

    public StateMachineEntity(String entityName,
                              int startTick,
                              State... states){
        if(entityName == null || startTick < 0 || states == null) {
            throw new IllegalArgumentException("Illegal elements entered.");
        }
        if (states.length == 0) {
            throw new IllegalArgumentException("at least one state required");}
        this.entityName = entityName;
        this.startTick = startTick;
        this.entryPoints = new int[states.length];
        this.states = new ArrayList<>();
        for (State s: states){
            this.states.add(s);
        }
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
            if(n !=-1){
                System.out.println(states.get(n).name());
            }
        }
    }
    @Override public String toString(){
        return entityName;
    }
}