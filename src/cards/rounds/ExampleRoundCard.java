package cards.rounds;

import models.Player;

import java.util.Map;


public class ExampleRoundCard implements RoundCard {
    private String name;
    private String description;
    private int cycle;
    private boolean revealed;
    private boolean accumulative;

    public ExampleRoundCard(String name, String description, int cycle, boolean accumulative) {
        this.name = name;
        this.description = description;
        this.cycle = cycle;
        this.revealed = false;
        this.accumulative = accumulative;
    }

    @Override
    public void execute(Player player) {
        executeAndOr(player,
                () -> gainResources(player, Map.of("wood", 2)),
                () -> becomeFirstPlayer(player)
        );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void reveal() {
        this.revealed = true;
    }

    @Override
    public int getCycle() {
        return cycle;
    }

    @Override
    public boolean isAccumulative() {
        return accumulative;
    }
}

