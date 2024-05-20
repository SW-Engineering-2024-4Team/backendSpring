package cards.common;

import models.Player;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCard implements Card {
    private String name;
    private String description;

    public AbstractCard(String name, String description) {
        this.name = name;
        this.description = description;
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
    public void modifyEffect(String effectType, Player player, Object value) {
        switch (effectType) {
            case "addExtraResource":
                getResourceModifications().put(player, (Map<String, Integer>) value);
                break;
            case "changePaymentResource":
                getPaymentResources().put(player, (String) value);
                break;
            case "increaseAnimalCapacity":
                getAnimalCapacities().put(player, (Integer) value);
                break;
            default:
                throw new IllegalArgumentException("Unknown effect type: " + effectType);
        }
    }

    public abstract Map<Player, Map<String, Integer>> getResourceModifications();
    public abstract Map<Player, String> getPaymentResources();
    public abstract Map<Player, Integer> getAnimalCapacities();
}
