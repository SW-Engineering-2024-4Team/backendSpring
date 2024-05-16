package actions;

import models.Player;
import cards.Card;

public class UseOccupationCardAction implements Action {
    @Override
    public void execute(Player player) {
        if (!player.getOccupationCards().isEmpty()) {
            Card card = player.getOccupationCards().remove(0);
            player.addCard(card, "active");
            card.execute(player);
        }
    }
}
