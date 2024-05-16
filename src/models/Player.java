package models;

import cards.Card;
import java.util.*;

public class Player {
    private String id;
    private String name;
    private Map<String, Integer> resources;
    private List<Card> occupationCards; // 직업 카드
    private List<Card> minorImprovementCards; // 보조 설비 카드
    private List<Card> activeCards; // 사용된 직업카드, 보조 설비 카드, 구매한 주요 설비 카드
    private PlayerBoard playerBoard;
    private int score;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.resources = new HashMap<>();
        this.occupationCards = new ArrayList<>();
        this.minorImprovementCards = new ArrayList<>();
        this.activeCards = new ArrayList<>();
        this.playerBoard = new PlayerBoard();
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
        if (card.execute(this)) {
            addCard(card, "active");
            return true;
        } else {
            return false;
        }
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
            card.modifyAction(actionType, newValue);
        }
    }

    public int getModifiedAction(String actionType, int defaultValue) {
        int modifiedValue = defaultValue;
        for (Card card : activeCards) {
            modifiedValue = card.getModifiedAction(actionType, modifiedValue);
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
            if (card.execute(this)) {
                playerBoard.removeFamilyMember(x, y);
            }
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
}
