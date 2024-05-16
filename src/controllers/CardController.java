package controllers;

import actions.Action;
import actions.GainResourceAction;
import actions.UseOccupationCardAction;
import cards.Card;
import cards.CardFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CardController {
    private static CardController instance;
    private Map<String, List<Card>> decks;

    private CardController() {
        decks = new HashMap<>();
        initializeCards();
    }

    public static CardController getInstance() {
        if (instance == null) {
            instance = new CardController();
        }
        return instance;
    }

    public List<Card> getDeck(String type) {
        return decks.get(type);
    }

    public void addCardToDeck(String type, Card card) {
        if (!decks.containsKey(type)) {
            decks.put(type, new ArrayList<>());
        }
        decks.get(type).add(card);
    }

    public List<Card> shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }

    private void initializeCards() {
        CardFactory cardFactory = new CardFactory();

        // 행동 카드 생성
        Card actionCard1 = cardFactory.createCard("action");
        List<Action> actionsForActionCard1 = new ArrayList<>();
        actionsForActionCard1.add(new GainResourceAction("wood", 1));
        actionsForActionCard1.add(new UseOccupationCardAction());
        cardFactory.addActionsToCard(actionCard1, actionsForActionCard1);
        addCardToDeck("actionCards", actionCard1);

        // 라운드 카드 생성
        Card roundCard1 = cardFactory.createCard("round");
        List<Action> actionsForRoundCard1 = new ArrayList<>();
        actionsForRoundCard1.add(new GainResourceAction("grain", 1));
        cardFactory.addActionsToCard(roundCard1, actionsForRoundCard1);
        addCardToDeck("roundCards", roundCard1);

        // 다른 카드 생성 및 초기화 로직 추가
    }
}
