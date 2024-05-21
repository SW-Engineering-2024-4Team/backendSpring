package models;

import cards.common.*;
import cards.majorimprovement.MajorImprovementCard;
import controllers.GameController;
import enums.ExchangeTiming;
import java.util.*;

public class Player {
    private final ArrayList<CommonCard> majorImprovementCards;
    private String id;
    private String name;
    private Map<String, Integer> resources;
    private List<CommonCard> occupationCards;
    private List<CommonCard> minorImprovementCards;
    private List<CommonCard> activeCards;
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

    public void addCard(CommonCard card, String type) {
        if (type.equals("occupation")) {
            occupationCards.add(card);
        } else if (type.equals("minorImprovement")) {
            minorImprovementCards.add(card);
        } else {
            activeCards.add(card);
        }
    }

    public List<CommonCard> getOccupationCards() {
        return occupationCards;
    }

    public List<CommonCard> getMinorImprovementCards() {
        return minorImprovementCards;
    }

    public List<CommonCard> getActiveCards() {
        return activeCards;
    }

    public List<CommonCard> getMajorImprovementCards() {
        return majorImprovementCards;
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

    public void placeFamilyMember(int x, int y, ActionRoundCard card) {
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

    public void moveToActiveCards(CommonCard card) {
        if (card instanceof MajorImprovementCard) {
            majorImprovementCards.remove(card);
        } else if (card instanceof UnifiedCard) {
            occupationCards.remove(card);
        } else if (card instanceof UnifiedCard) {
            minorImprovementCards.remove(card);
        }
        activeCards.add(card);
    }

    public List<ExchangeableCard> getExchangeableCards(ExchangeTiming timing) {
        List<ExchangeableCard> exchangeableCards = new ArrayList<>();
        for (CommonCard card : activeCards) {
            if (card instanceof ExchangeableCard) {
                ExchangeableCard exchangeableCard = (ExchangeableCard) card;
                if (exchangeableCard.canExchange(timing) || exchangeableCard.canExchange(ExchangeTiming.ANYTIME)) {
                    exchangeableCards.add(exchangeableCard);
                }
            }
        }
        return exchangeableCards;
    }

    public void executeExchange(ExchangeableCard card, String fromResource, String toResource, int amount) {
        card.executeExchange(this, fromResource, toResource, amount);
    }

    public void useBakingCard(BakingCard card) {
        card.triggerBreadBaking(this);
    }

    public void addMajorImprovementCard(CommonCard card) {
        majorImprovementCards.add(card);
    }

    public void removeMajorImprovementCard(CommonCard card) {
        majorImprovementCards.remove(card);
    }

    public boolean checkResources(Map<String, Integer> cost) {
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            if (resources.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void payResources(Map<String, Integer> cost) {
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            addResource(entry.getKey(), -entry.getValue());
        }
    }

    public void useUnifiedCard(UnifiedCard card) {
        card.execute(this);
        moveToActiveCards(card);
    }

    // 주요 설비 카드 선택 로직
    public BakingCard selectBakingCard(List<BakingCard> bakingCards) {
        // 플레이어가 선택하는 로직 (여기서는 예시로 랜덤 선택)
        Random random = new Random();
        return bakingCards.get(random.nextInt(bakingCards.size()));
    }

}
