package cards.common;

import enums.ExchangeTiming;
import models.Player;

public interface ExchangeableCard extends Card {
    boolean hasExchangeResourceFunction();
    ExchangeTiming getExchangeTiming();
    void exchangeResources(Player player, String fromResource, String toResource, int amount);
    void modifyEffect(String effectType, Player player, Object value); // 효과 변경 메서드
}
