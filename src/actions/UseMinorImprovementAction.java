package actions;

import models.Player;
import cards.Card;

public class UseMinorImprovementAction implements Action {
    @Override
    public void execute(Player player) {
        if (!player.getMinorImprovementCards().isEmpty()) {
            Card card = player.getMinorImprovementCards().remove(0);
            player.addCard(card, "active");
            card.execute(player);
        }
    }
}
