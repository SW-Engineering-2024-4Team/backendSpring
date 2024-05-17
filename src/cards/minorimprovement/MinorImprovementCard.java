package cards.minorimprovement;

import cards.common.ExchangeableCard;
import models.Player;
import java.util.Map;

public interface MinorImprovementCard extends ExchangeableCard {
    Map<String, Integer> getCost(); // 자원 지불
    int getAdditionalPoints(); // 추가 점수
    boolean checkCondition(Player player); // 특정 조건 확인

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
