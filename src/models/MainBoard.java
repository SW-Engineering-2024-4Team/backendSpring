package models;

import actions.Action;
import cards.ActionCard;
import cards.Card;
import cards.MajorImprovementCard;
import cards.RoundCard;

import java.util.List;

public class MainBoard {
    private List<Card> actionCards;
    private List<Card> roundCards;
    private List<Card> majorImprovementCards;

    public void initializeBoard(List<Card> actionCards, List<Card> roundCards, List<Card> majorImprovementCards) {
        this.actionCards = actionCards;
        this.roundCards = roundCards;
        this.majorImprovementCards = majorImprovementCards;
    }

    public void revealRoundCard(int round) {
        RoundCard roundCard = (RoundCard) roundCards.get(round - 1);
        roundCard.setRevealed(true);
        // 프론트엔드에 라운드 카드 공개 메시지 전송
//        WebSocketService.sendMessageToClient("roundCardRevealed", roundCard);
    }

    public void accumulateResources() {
        for (Card card : actionCards) {
            ((ActionCard) card).accumulateResources();
        }
        for (Card card : roundCards) {
            if (card instanceof RoundCard && ((RoundCard) card).isRevealed()) {
                ((RoundCard) card).accumulateResources();
            }
        }
    }
    public List<Card> getActionCards() {
        return actionCards;
    }
    public List<Card> getRoundCards() {
        return roundCards;
    }
    public List<Card> getMajorImprovementCards() {
        return majorImprovementCards;
    }

    public void removeMajorImprovementCard(MajorImprovementCard card) {
        majorImprovementCards.remove(card);
    }


}
