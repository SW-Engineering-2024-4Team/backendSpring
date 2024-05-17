package cards.actions;

import models.Player;

import java.util.Map;

public class ExampleActionCard implements ActionCard {
    private String name;
    private String description;
    private boolean accumulative;

    public ExampleActionCard(String name, String description, boolean accumulative) {
        this.name = name;
        this.description = description;
        this.accumulative = accumulative;
    }

    @Override
    public void execute(Player player) {
        executeThen(player,
                () -> gainResources(player, Map.of("wood", 2, "clay", 1)),
                () -> buildFence(player)
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
    public boolean isAccumulative() {
        return accumulative;
    }
}


