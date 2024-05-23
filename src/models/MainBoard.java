package models;

import cards.common.AccumulativeCard;
import cards.common.ActionRoundCard;
import cards.common.CommonCard;

// MainBoard.java

import java.util.*;

public class MainBoard {
    private List<ActionRoundCard> actionCards;
    private List<ActionRoundCard> roundCards;
    private List<CommonCard> majorImprovementCards;
    private Map<CommonCard, FamilyMember> familyMembersOnCards;
    private Map<ActionRoundCard, FamilyMember> occupyingFamilyMembers;

    public MainBoard() {
        this.familyMembersOnCards = new HashMap<>();
        this.occupyingFamilyMembers = new HashMap<>();
        this.actionCards = new ArrayList<>();
        this.roundCards = new ArrayList<>();
        this.majorImprovementCards = new ArrayList<>();
    }

    public void initializeBoard(List<ActionRoundCard> actionCards, List<List<ActionRoundCard>> roundCycles, List<CommonCard> majorImprovementCards) {
        this.familyMembersOnCards = new HashMap<>();
        this.actionCards = actionCards;
        this.roundCards = new ArrayList<>();
        for (List<ActionRoundCard> cycle : roundCycles) {
            this.roundCards.addAll(cycle);
        }
        this.majorImprovementCards = majorImprovementCards;
        this.occupyingFamilyMembers = new HashMap<>(); // 초기화 추가
    }

    public void revealRoundCard(int round) {
        ActionRoundCard roundCard = roundCards.get(round - 1);
        roundCard.reveal();
        // 프론트엔드에 라운드 카드 공개 메시지 전송
        // WebSocketService.sendMessageToClient("roundCardRevealed", roundCard);
    }

    public List<ActionRoundCard> getRevealedRoundCards() {
        List<ActionRoundCard> revealedRoundCards = new ArrayList<>();
        for (ActionRoundCard card : roundCards) {
            if (card.isRevealed()) {
                revealedRoundCards.add(card);
            }
        }
        return revealedRoundCards;
    }

    public void accumulateResources() {
        for (ActionRoundCard card : actionCards) {
            if (card instanceof AccumulativeCard) {
                ((AccumulativeCard) card).accumulateResources();
            }
        }
        for (ActionRoundCard card : roundCards) {
            if (card instanceof AccumulativeCard && card.isRevealed()) {
                ((AccumulativeCard) card).accumulateResources();
            }
        }
    }

    public List<ActionRoundCard> getActionCards() {
        return actionCards;
    }

    public List<ActionRoundCard> getRoundCards() {
        return roundCards;
    }

    public List<CommonCard> getMajorImprovementCards() {
        return majorImprovementCards;
    }

    public List<CommonCard> getAllCards() {
        List<CommonCard> allCards = new ArrayList<>();
        allCards.addAll(actionCards);
        allCards.addAll(roundCards);
        allCards.addAll(majorImprovementCards);
        return allCards;
    }

    public void removeMajorImprovementCard(CommonCard card) {
        majorImprovementCards.remove(card);
    }

    public boolean canPlaceFamilyMember(CommonCard card) {
        return !familyMembersOnCards.containsKey(card);
    }

    public void placeFamilyMember(CommonCard card, FamilyMember familyMember) {
        familyMembersOnCards.put(card, familyMember);
    }

    public void resetFamilyMembersOnCards() {
        occupyingFamilyMembers.clear();
        familyMembersOnCards.clear();
        System.out.println("Family members on cards have been reset.");
    }

    public FamilyMember getOccupyingFamilyMember(ActionRoundCard card) {
        return occupyingFamilyMembers.getOrDefault(card, null);
    }

    public void setOccupyingFamilyMember(ActionRoundCard card, FamilyMember familyMember) {
        occupyingFamilyMembers.put(card, familyMember);
    }

    public void clearOccupyingFamilyMembers() {
        occupyingFamilyMembers.clear();
    }

    public boolean isCardOccupied(ActionRoundCard card) {
        return occupyingFamilyMembers.containsKey(card);
    }
}
