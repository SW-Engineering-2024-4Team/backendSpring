package cards.factory.imp.occupation;

import cards.common.AccumulativeCard;
import cards.common.ActionRoundCard;
import cards.decorators.UnifiedDecorator;
import cards.occupation.OccupationCard;
import enums.ExchangeTiming;
import models.MainBoard;
import models.Player;

import java.util.ArrayList;
import java.util.List;

// TODO
public class Plasterer extends OccupationCard {

    public Plasterer(int id) {
        super(id, "초벽질공", "집을 짓거나 방을 고칠 때 추가 자원을 얻습니다..", null, null, 1, 4, ExchangeTiming.NONE);
    }

    @Override
    public void execute(Player player) {
        MainBoard mainBoard = player.getGameController().getMainBoard();
        List<ActionRoundCard> actionCards = mainBoard.getActionCards();
        List<ActionRoundCard> roundCards = mainBoard.getRoundCards();

        List<ActionRoundCard> newActionCards = wrapWithPlastererDecorator(actionCards, player);
        List<ActionRoundCard> newRoundCards = wrapWithPlastererDecorator(roundCards, player);

        // 메인보드의 원래 리스트를 업데이트
        mainBoard.setActionCards(newActionCards);
        mainBoard.setRoundCards(newRoundCards);
    }

    private List<ActionRoundCard> wrapWithPlastererDecorator(List<ActionRoundCard> cards, Player player) {
        List<ActionRoundCard> newCards = new ArrayList<>();
        for (ActionRoundCard card : cards) {
            if (card.executesBuildOrRenovate()) {
                newCards.add(new PlastererDecorator(card, player));
            } else {
                newCards.add(card);
            }
        }
        return newCards;
    }

    private static class PlastererDecorator extends UnifiedDecorator {
        public PlastererDecorator(ActionRoundCard decoratedCard, Player appliedPlayer) {
            super((AccumulativeCard) decoratedCard, appliedPlayer);
        }

        @Override
        public void buildHouse(Player player) {
            decoratedCard.buildHouse(player);
            if (player.equals(appliedPlayer)) {
                int additionalFood = 3;
                player.addResource("food", additionalFood);
                System.out.println("Plasterer effect: Gained an additional 3 food for building a house.");
            }
        }

        @Override
        public void renovateRooms(Player player) {
            decoratedCard.renovateRooms(player);
            if (player.equals(appliedPlayer)) {
                int additionalFood = 3;
                player.addResource("food", additionalFood);
                System.out.println("Plasterer effect: Gained an additional 3 food for renovating the house.");
            }
        }

        @Override
        public void execute(Player player) {
            decoratedCard.execute(player);
        }
    }
}