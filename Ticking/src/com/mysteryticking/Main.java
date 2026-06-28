package com.mysteryticking;

public class Main {
    public static void main(String[] args) {
        int countdown;
        if(args.length == 0) {countdown = 160;}
        else{
            countdown = Integer.parseInt(args[0]);
        }
        //create the com.mysteryticking.PipeBomb timing:
        PipeBomb pd = new PipeBomb(countdown);

        //creates the states:
        StateMachineEntity snape = new StateMachineEntity("Snape", 1,
                new State("Snape", 2),
                new State("Snape", 2),
                new State("Severus Snape", 4));
        StateMachineEntity dumbledore = new StateMachineEntity("Dumbledore", 16,
                new State("Dumbledore", 8));
        StateMachineEntity ron = new StateMachineEntity("Ron", 33,
                new State("Ron", 2),
                new State("Ron", 2),
                new State("Ron Weasly", 4));
        StateMachineEntity hermione = new StateMachineEntity("Hermione", 49,
                new State("Hermione", 4),
                new State("Hermione", 4),
                new State("Hermione", 2),
                new State("Hermione", 2),
                new State("Hermione", 4));
        StateMachineEntity harry = new StateMachineEntity("Harry Potter", 65,
                new State("Harry Potter", 1),
                new State("Harry Potter", 1),
                new State("OoOohhhh", 2),
                new State("Harry Potter", 1),
                new State("Harry Potter", 1),
                new State("YeeeEeaahH", 2));

        // FiniteEntity: Ron fires at most 3 times, then unregisters itself
        TickListener finiteron = new FiniteEntity(ron, pd, 3);

        pd.register(snape);
        pd.register(dumbledore);
        pd.register(finiteron);
        pd.register(hermione);
        pd.register(harry);
        pd.register(new ConditionalEntity(t -> t % 7 == 0, "Dobby is free!"));
        pd.register(new ConditionalEntity(t -> t == 13 || t == 42, "Avada Kedavra!"));
        pd.register(tick -> {
            if (tick % 8 == 0) {
                System.out.println();
            }});

        pd.start();
        }
    }
