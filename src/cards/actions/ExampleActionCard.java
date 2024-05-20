package cards.actions;

import cards.common.AbstractCard;
import models.Player;
import java.util.Map;
import java.util.HashMap;

public class ExampleActionCard extends AbstractCard implements ActionCard {
    private boolean accumulative;
    private Map<Player, Map<String, Integer>> playerResourceModifications = new HashMap<>();
    private Map<Player, String> playerPaymentResource = new HashMap<>();
    private Map<Player, Integer> playerAnimalCapacity = new HashMap<>();

    public ExampleActionCard(String name, String description, boolean accumulative) {
        super(name, description);
        this.accumulative = accumulative;
    }

    @Override
    public void execute(Player player) {
        Map<String, Integer> resources = new HashMap<>(Map.of("wood", 2, "clay", 1));
        Map<String, Integer> modifications = playerResourceModifications.getOrDefault(player, new HashMap<>());
        for (Map.Entry<String, Integer> entry : modifications.entrySet()) {
            resources.put(entry.getKey(), resources.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
        gainResources(player, resources);
//        buildFence(player);
    }

    @Override
    public void accumulate() {

    }

    @Override
    public boolean isAccumulative() {
        return accumulative;
    }

    @Override
    public Map<Player, Map<String, Integer>> getResourceModifications() {
        return playerResourceModifications;
    }

    @Override
    public Map<Player, String> getPaymentResources() {
        return playerPaymentResource;
    }

    @Override
    public Map<Player, Integer> getAnimalCapacities() {
        return playerAnimalCapacity;
    }
}

