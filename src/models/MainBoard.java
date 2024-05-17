package models;

import cards.actions.ActionCard;
import cards.common.Card;
import cards.rounds.RoundCard;

import java.util.ArrayList;
import java.util.List;

public class MainBoard {
    private List<Card> actionCards;
    private List<Card> roundCards;
    private List<Card> majorImprovementCards;

    public void initializeBoard(List<Card> actionCards, List<List<Card>> roundCycles, List<Card> majorImprovementCards) {
        this.actionCards = actionCards;
        this.roundCards = new ArrayList<>();
        for (List<Card> cycle : roundCycles) {
            this.roundCards.addAll(cycle);
        }
        this.majorImprovementCards = majorImprovementCards;
    }

    public void revealRoundCard(int round) {
        RoundCard roundCard = (RoundCard) roundCards.get(round - 1);
        roundCard.reveal();
        // 프론트엔드에 라운드 카드 공개 메시지 전송
        // WebSocketService.sendMessageToClient("roundCardRevealed", roundCard);
    }

    public void accumulateResources() {
       for (Card card : actionCards) {
            if (card instanceof ActionCard && ((ActionCard) card).isAccumulative()) {
                card.accumulate();
            }
        }
        for (Card card : roundCards) {
            if (card instanceof RoundCard && ((RoundCard) card).isAccumulative() && ((RoundCard) card).isRevealed()) {
                card.accumulate();
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

    public List<Card> getAllCards() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(actionCards);
        allCards.addAll(roundCards);
        return allCards;
    }

//    public void removeMajorImprovementCard(MajorImprovementCard card) {
//        majorImprovementCards.remove(card);
//    }


}
