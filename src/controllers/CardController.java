package controllers;

import cards.common.Card;
import cards.common.CardFactory;

import java.util.*;

public class CardController {
    private static CardController instance;
    private List<Card> actionCards;
    private List<Card> roundCards;
    private List<Card> minorImprovementCards;
    private List<Card> occupationCards;
    private List<Card> majorImprovementCards;

    private CardController() {
        this.actionCards = new ArrayList<>();
        this.roundCards = new ArrayList<>();
        this.minorImprovementCards = new ArrayList<>();
        this.occupationCards = new ArrayList<>();
        this.majorImprovementCards = new ArrayList<>();
        initializeDecks();
    }

    public static CardController getInstance() {
        if (instance == null) {
            instance = new CardController();
        }
        return instance;
    }

    private void initializeDecks() {
        CardFactory cardFactory = new CardFactory();

        for (int i = 0; i < 30; i++) {
            actionCards.add(cardFactory.createCard("ActionCard", 0));
        }

        // 라운드 카드를 주기별로 생성
        for (int i = 0; i < 4; i++) {
            roundCards.add(cardFactory.createCard("RoundCard", 1));
        }
        for (int i = 0; i < 3; i++) {
            roundCards.add(cardFactory.createCard("RoundCard", 2));
        }
        for (int i = 0; i < 2; i++) {
            roundCards.add(cardFactory.createCard("RoundCard", 3));
        }
        for (int i = 0; i < 2; i++) {
            roundCards.add(cardFactory.createCard("RoundCard", 4));
        }
        for (int i = 0; i < 2; i++) {
            roundCards.add(cardFactory.createCard("RoundCard", 5));
        }
        roundCards.add(cardFactory.createCard("RoundCard", 6));

        for (int i = 0; i < 100; i++) {
            minorImprovementCards.add(cardFactory.createCard("MinorImprovementCard", 0));
            occupationCards.add(cardFactory.createCard("OccupationCard", 0));
            majorImprovementCards.add(cardFactory.createCard("MajorImprovementCard", 0));
        }
    }

    public List<Card> getDeck(String deckType) {
        switch (deckType) {
            case "actionCards":
                return actionCards;
            case "roundCards":
                return roundCards;
            case "minorImprovementCards":
                return minorImprovementCards;
            case "occupationCards":
                return occupationCards;
            case "majorImprovementCards":
                return majorImprovementCards;
            default:
                return new ArrayList<>();
        }
    }

    public void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    public List<List<Card>> getShuffledRoundCardsByCycle() {
        List<List<Card>> cycles = new ArrayList<>();
        int[] roundsPerCycle = {4, 3, 2, 2, 2, 1};
        int startIndex = 0;

        for (int rounds : roundsPerCycle) {
            List<Card> cycle = new ArrayList<>(roundCards.subList(startIndex, startIndex + rounds));
            shuffleDeck(cycle);
            cycles.add(cycle);
            startIndex += rounds;
        }

        return cycles;
    }
}
