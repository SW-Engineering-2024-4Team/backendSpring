package cards.majorimprovement;

import cards.common.ExchangeableCard;
import models.Player;
import java.util.Map;

public interface MajorImprovementCard extends ExchangeableCard {
    Map<String, Integer> getCost(); // 자원 지불
    int getAdditionalPoints(); // 추가 점수

    default void payCost(Player player) {
        Map<String, Integer> cost = getCost();
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            player.addResource(entry.getKey(), -entry.getValue());
        }
    }

    default int calculateScore(Player player) {
        // 점수 계산 로직
        return 0;
    }

    default boolean hasBreadBakingFunction() {
        return false;
    }

    default void bakeBread(Player player, Map<String, Integer> exchangeRate) {
        // 빵굽기 로직
    }

    void applyEffect(Player player);
}
