package cards.minorimprovement;

import cards.common.ExchangeableCard;
import models.Player;

import java.util.Map;

public interface MinorImprovementCard extends ExchangeableCard {
    Map<String, Integer> getCost();
    int getAdditionalPoints();
    boolean checkCondition(Player player);

    default void payCost(Player player) {
        Map<String, Integer> cost = getCost();
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            player.addResource(entry.getKey(), -entry.getValue());
        }
    }

    default void applyEffect(Player player) {
        // 기본 효과 적용
    }
}
