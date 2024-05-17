package models;

import cards.common.ExchangeTiming;
import cards.common.ExchangeableCard;
import cards.majorimprovement.MajorImprovementCard;
import cards.minorimprovement.MinorImprovementCard;
import cards.occupation.OccupationCard;
import cards.common.Card;
import controllers.GameController;

import java.util.*;

public class Player {
    private final ArrayList<Card> majorImprovementCards;
    private String id;
    private String name;
    private Map<String, Integer> resources;
    private List<Card> occupationCards;
    private List<Card> minorImprovementCards;
    private List<Card> activeCards;
    private PlayerBoard playerBoard;
    private int score;
    private boolean isFirstPlayer;
    private GameController gameController;

    public Player(String id, String name, GameController gameController) {
        this.id = id;
        this.name = name;
        this.resources = new HashMap<>();
        this.occupationCards = new ArrayList<>();
        this.minorImprovementCards = new ArrayList<>();
        this.majorImprovementCards = new ArrayList<>();
        this.activeCards = new ArrayList<>();
        this.playerBoard = new PlayerBoard();
        this.isFirstPlayer = false;
        this.gameController = gameController;
        initializeResources();
    }

    private void initializeResources() {
        resources.put("wood", 0);
        resources.put("clay", 0);
        resources.put("stone", 0);
        resources.put("grain", 0);
        resources.put("food", 0);
        resources.put("beggingCard", 0);
    }

    public void addCard(Card card, String type) {
        if (type.equals("occupation")) {
            occupationCards.add(card);
        } else if (type.equals("minorImprovement")) {
            minorImprovementCards.add(card);
        } else {
            activeCards.add(card);
        }
    }

    public boolean useCard(Card card) {
        return true;
    }

    public List<Card> getOccupationCards() {
        return occupationCards;
    }

    public List<Card> getMinorImprovementCards() {
        return minorImprovementCards;
    }

    public List<Card> getActiveCards() {
        return activeCards;
    }

    public void modifyAction(String actionType, int newValue) {
        for (Card card : activeCards) {
        }
    }

    public int getModifiedAction(String actionType, int defaultValue) {
        int modifiedValue = defaultValue;
        for (Card card : activeCards) {
        }
        return modifiedValue;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean hasAvailableFamilyMembers() {
        for (FamilyMember[] row : playerBoard.getFamilyMembers()) {
            for (FamilyMember member : row) {
                if (member != null && member.isAdult()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void resetFamilyMembers() {
        playerBoard.resetFamilyMembers();
    }

    public void placeFamilyMember(int x, int y, Card card) {
        if (playerBoard.getFamilyMembers()[x][y] != null) {
            card.execute(this);
            playerBoard.removeFamilyMember(x, y);
        }
    }

    public void addResource(String resource, int amount) {
        resources.put(resource, resources.getOrDefault(resource, 0) + amount);
    }

    public int getResource(String resource) {
        return resources.getOrDefault(resource, 0);
    }

    public void convertBabiesToAdults() {
        for (FamilyMember[] row : playerBoard.getFamilyMembers()) {
            for (FamilyMember member : row) {
                if (member != null && !member.isAdult()) {
                    member.setAdult(true);
                }
            }
        }
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean isFirstPlayer) {
        this.isFirstPlayer = isFirstPlayer;
    }

    public boolean chooseOption() {
        return new Random().nextBoolean();
    }

    public GameController getGameController() {
        return gameController;
    }

    public void moveToActiveCards(Card card) {
        if (card instanceof MajorImprovementCard) {
            majorImprovementCards.remove(card);
        } else if (card instanceof OccupationCard) {
            occupationCards.remove(card);
        } else if (card instanceof MinorImprovementCard) {
            minorImprovementCards.remove(card);
        }
        activeCards.add(card);
    }


    public List<ExchangeableCard> getExchangeableCards(ExchangeTiming timing) {
        List<ExchangeableCard> exchangeableCards = new ArrayList<>();
        for (Card card : activeCards) {
            if (card instanceof ExchangeableCard) {
                ExchangeableCard exchangeableCard = (ExchangeableCard) card;
                if (exchangeableCard.hasExchangeResourceFunction() && (exchangeableCard.getExchangeTiming() == timing || exchangeableCard.getExchangeTiming() == ExchangeTiming.ANYTIME)) {
                    exchangeableCards.add(exchangeableCard);
                }
            }
        }
        return exchangeableCards;
    }

    public void executeExchange(ExchangeableCard card, String fromResource, String toResource, int amount) {
        if (activeCards.contains(card)) {
            card.exchangeResources(this, fromResource, toResource, amount);
        }
    }

    public void useMinorImprovementCard(MinorImprovementCard card) {
        if (card.checkCondition(this)) {
            card.payCost(this);
            card.applyEffect(this);
            moveToActiveCards(card);
        }
    }

    public void useMajorImprovementCard(MajorImprovementCard card) {
        card.payCost(this);
        card.applyEffect(this);
        moveToActiveCards(card);
    }

    public List<MajorImprovementCard> getBreadBakingCards() {
        List<MajorImprovementCard> breadBakingCards = new ArrayList<>();
        for (Card card : activeCards) {
            if (card instanceof MajorImprovementCard && ((MajorImprovementCard) card).hasBreadBakingFunction()) {
                breadBakingCards.add((MajorImprovementCard) card);
            }
        }
        return breadBakingCards;
    }

    public void bakeBread(MajorImprovementCard card, Map<String, Integer> exchangeRate) {
        if (activeCards.contains(card)) {
            card.bakeBread(this, exchangeRate);
        }
    }





}
