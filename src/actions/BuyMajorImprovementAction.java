package actions;

import cards.MajorImprovementCard;
import models.Player;
import models.MainBoard;

public class BuyMajorImprovementAction implements Action {
    private MajorImprovementCard card;
    private MainBoard mainBoard;

    public BuyMajorImprovementAction(MajorImprovementCard card, MainBoard mainBoard) {
        this.card = card;
        this.mainBoard = mainBoard;
    }

    @Override
    public void execute(Player player) {
        if (mainBoard.getMajorImprovementCards().contains(card) && card.canAfford(player)) {
            card.purchase(player);
            mainBoard.removeMajorImprovementCard(card);
        } else {
            throw new IllegalStateException("Player cannot purchase this card or card is not available.");
        }
    }
}
