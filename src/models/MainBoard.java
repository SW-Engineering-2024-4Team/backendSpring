//package models;
//
//import cards.common.AccumulativeCard;
//import cards.common.ActionRoundCard;
//import cards.common.CommonCard;
//
//// MainBoard.java
//
//import java.util.*;
//
//public class MainBoard {
//    private List<ActionRoundCard> actionCards;
//    private List<ActionRoundCard> roundCards;
//    private List<CommonCard> majorImprovementCards;
//    private Map<CommonCard, FamilyMember> familyMembersOnCards;
//    private Map<ActionRoundCard, FamilyMember> occupyingFamilyMembers;
//    private final List<OccupiedCard> occupiedCards = new ArrayList<>();
//
//    public MainBoard() {
//        this.familyMembersOnCards = new HashMap<>();
//        this.occupyingFamilyMembers = new HashMap<>();
//        this.actionCards = new ArrayList<>();
//        this.roundCards = new ArrayList<>();
//        this.majorImprovementCards = new ArrayList<>();
//    }
//
//    public void initializeBoard(List<ActionRoundCard> actionCards, List<List<ActionRoundCard>> roundCycles, List<CommonCard> majorImprovementCards) {
//        this.familyMembersOnCards = new HashMap<>();
//        this.actionCards = actionCards;
//        this.roundCards = new ArrayList<>();
//        for (List<ActionRoundCard> cycle : roundCycles) {
//            this.roundCards.addAll(cycle);
//        }
//        this.majorImprovementCards = majorImprovementCards;
//        this.occupyingFamilyMembers = new HashMap<>(); // 초기화 추가
//    }
//
//    public void revealRoundCard(int round) {
//        ActionRoundCard roundCard = roundCards.get(round - 1);
//        roundCard.reveal();
//        // 프론트엔드에 라운드 카드 공개 메시지 전송
//        // WebSocketService.sendMessageToClient("roundCardRevealed", roundCard);
//    }
//
//    public List<ActionRoundCard> getRevealedRoundCards() {
//        List<ActionRoundCard> revealedRoundCards = new ArrayList<>();
//        for (ActionRoundCard card : roundCards) {
//            if (card.isRevealed()) {
//                revealedRoundCards.add(card);
//            }
//        }
//        return revealedRoundCards;
//    }
//
//    public void accumulateResources() {
//        for (ActionRoundCard card : actionCards) {
//            if (card instanceof AccumulativeCard) {
//                ((AccumulativeCard) card).accumulateResources();
//            }
//        }
//        for (ActionRoundCard card : roundCards) {
//            if (card instanceof AccumulativeCard && card.isRevealed()) {
//                ((AccumulativeCard) card).accumulateResources();
//            }
//        }
//    }
//
//    public List<ActionRoundCard> getActionCards() {
//        return actionCards;
//    }
//
//    public List<ActionRoundCard> getRoundCards() {
//        return roundCards;
//    }
//
//    public List<CommonCard> getMajorImprovementCards() {
//        return majorImprovementCards;
//    }
//
//    public List<CommonCard> getAllCards() {
//        List<CommonCard> allCards = new ArrayList<>();
//        allCards.addAll(actionCards);
//        allCards.addAll(roundCards);
//        allCards.addAll(majorImprovementCards);
//        return allCards;
//    }
//
//    public void removeMajorImprovementCard(CommonCard card) {
//        majorImprovementCards.remove(card);
//    }
//
////    public void placeFamilyMember(ActionRoundCard card, FamilyMember familyMember) {
////        if (canPlaceFamilyMember(card)) {
////            occupyingFamilyMembers.put(card, familyMember);
////        } else {
////            throw new IllegalStateException("Card is already occupied.");
////        }
////    }
//
//    public boolean canPlaceFamilyMember(ActionRoundCard card) {
//        return occupiedCards.stream().noneMatch(occupiedCard -> occupiedCard.getCard().equals(card));
//    }
//
//    public void placeFamilyMember(ActionRoundCard card, FamilyMember familyMember) {
//        occupiedCards.add(new OccupiedCard(card, familyMember));
//    }
//
//    public boolean isCardOccupied(ActionRoundCard card) {
//        return occupiedCards.stream().anyMatch(occupiedCard -> occupiedCard.getCard().equals(card));
//    }
//
//    public void resetFamilyMembersOnCards() {
//        occupiedCards.clear();
//    }
//
//    // Inner class to store occupied card information
//    private static class OccupiedCard {
//        private final ActionRoundCard card;
//        private final FamilyMember familyMember;
//
//        public OccupiedCard(ActionRoundCard card, FamilyMember familyMember) {
//            this.card = card;
//            this.familyMember = familyMember;
//        }
//
//        public ActionRoundCard getCard() {
//            return card;
//        }
//
//        public FamilyMember getFamilyMember() {
//            return familyMember;
//        }
//    }
//
//
//}

package models;

import cards.common.AccumulativeCard;
import cards.common.ActionRoundCard;
import cards.common.CommonCard;
import java.util.*;

public class MainBoard {
    private List<ActionRoundCard> actionCards;
    private List<ActionRoundCard> roundCards;
    private List<CommonCard> majorImprovementCards;
    private Map<ActionRoundCard, FamilyMember> occupyingFamilyMembers;
    private final List<OccupiedCard> occupiedCards = new ArrayList<>();

    public MainBoard() {
        this.occupyingFamilyMembers = new HashMap<>();
        this.actionCards = new ArrayList<>();
        this.roundCards = new ArrayList<>();
        this.majorImprovementCards = new ArrayList<>();
    }

    public void initializeBoard(List<ActionRoundCard> actionCards, List<List<ActionRoundCard>> roundCycles, List<CommonCard> majorImprovementCards) {
        this.occupyingFamilyMembers = new HashMap<>();
        this.actionCards = actionCards;
        this.roundCards = new ArrayList<>();
        for (List<ActionRoundCard> cycle : roundCycles) {
            this.roundCards.addAll(cycle);
        }
        this.majorImprovementCards = majorImprovementCards;
    }

    public void revealRoundCard(int round) {
        ActionRoundCard roundCard = roundCards.get(round - 1);
        roundCard.reveal();
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

    public boolean canPlaceFamilyMember(ActionRoundCard card) {
        return !occupyingFamilyMembers.containsKey(card);
    }

    public void placeFamilyMember(ActionRoundCard card, FamilyMember familyMember) {
        if (canPlaceFamilyMember(card)) {
            occupyingFamilyMembers.put(card, familyMember);
        } else {
            throw new IllegalStateException("Card is already occupied.");
        }
    }

    public boolean isCardOccupied(ActionRoundCard card) {
        return occupyingFamilyMembers.containsKey(card);
    }

    public void resetFamilyMembersOnCards() {
        occupyingFamilyMembers.clear();
    }

    private static class OccupiedCard {
        private final ActionRoundCard card;
        private final FamilyMember familyMember;

        public OccupiedCard(ActionRoundCard card, FamilyMember familyMember) {
            this.card = card;
            this.familyMember = familyMember;
        }

        public ActionRoundCard getCard() {
            return card;
        }

        public FamilyMember getFamilyMember() {
            return familyMember;
        }
    }
}
