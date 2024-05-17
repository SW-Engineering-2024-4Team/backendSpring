package models;

import cards.common.Card;
import java.util.*;

public class Player {
    private String id;
    private String name;
    private Map<String, Integer> resources;
    private List<Card> occupationCards;
    private List<Card> minorImprovementCards;
    private List<Card> activeCards;
    private PlayerBoard playerBoard;
    private int score;
    private boolean isFirstPlayer;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.resources = new HashMap<>();
        this.occupationCards = new ArrayList<>();
        this.minorImprovementCards = new ArrayList<>();
        this.activeCards = new ArrayList<>();
        this.playerBoard = new PlayerBoard();
        this.isFirstPlayer = false; // 기본값으로 설정
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
        // 플레이어가 선택하는 로직 구현
        // 예시로 랜덤하게 선택하는 로직 // 프론트엔드와 통신
        return new Random().nextBoolean();
    }


    // 기타 메서드
}
