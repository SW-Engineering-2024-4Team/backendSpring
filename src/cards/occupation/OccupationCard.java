package cards.occupation;

import cards.actions.ActionCard;
import cards.common.ExchangeableCard;
import cards.rounds.RoundCard;
import models.Player;

import java.util.Map;

public interface OccupationCard extends ExchangeableCard {
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

}
