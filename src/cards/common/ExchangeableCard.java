package cards.common;

import models.Player;

public interface ExchangeableCard extends Card {
    boolean hasExchangeResourceFunction();
    ExchangeTiming getExchangeTiming();
    void exchangeResources(Player player, String fromResource, String toResource, int amount);
}
