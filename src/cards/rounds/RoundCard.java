package cards.rounds;

import cards.common.Card;
import models.Player;
import java.util.Map;

public interface RoundCard extends Card {
    void reveal(); // 카드를 공개하는 메서드
    int getCycle(); // 주기 정보를 가져오는 메서드
    boolean isAccumulative();
    boolean isRevealed();

    // 변경된 효과를 저장할 데이터 구조를 제공하는 메서드들
    Map<Player, Map<String, Integer>> getResourceModifications();
    Map<Player, String> getPaymentResources();
    Map<Player, Integer> getAnimalCapacities();

    @Override
    default void modifyEffect(String effectType, Player player, Object value) {
        switch (effectType) {
            case "addExtraResource":
                getResourceModifications().put(player, (Map<String, Integer>) value);
                break;
            case "changePaymentResource":
                getPaymentResources().put(player, (String) value);
                break;
            case "increaseAnimalCapacity":
                getAnimalCapacities().put(player, (int) value);
                break;
            default:
                throw new IllegalArgumentException("Unknown effect type: " + effectType);
        }
    }
}
