package cards.occupation;

import cards.actions.ActionCard;
import cards.common.ExchangeTiming;
import cards.rounds.RoundCard;
import cards.common.Card;
import models.Player;
import java.util.Map;

public interface OccupationCard extends Card {
    int getMaxPlayers();
    int getMinPlayers();

    default void gainOccupationResources(Player player, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            player.addResource(entry.getKey(), entry.getValue());
        }
    }

    default void modifyCardEffect(ActionCard card, String effectType, Player player, Object value) {
        card.modifyEffect(effectType, player, value);
    }

    default void modifyCardEffect(RoundCard card, String effectType, Player player, Object value) {
        card.modifyEffect(effectType, player, value);
    }

    boolean hasExchangeResourceFunction();

    ExchangeTiming getExchangeTiming();

    default void exchangeResources(Player player, String fromResource, String toResource, int amount) {
        if (player.getResource(fromResource) >= amount) {
            player.addResource(fromResource, -amount);
            player.addResource(toResource, amount);
        }
    }
}
