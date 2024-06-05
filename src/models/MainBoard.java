//package models;
//
//import cards.common.AccumulativeCard;
//import cards.common.ActionRoundCard;
//import cards.common.CommonCard;
////import cards.factory.imp.action.WanderingTheaterActionCard;
//import cards.majorimprovement.MajorImprovementCard;
//
//// MainBoard.java
//
//import java.util.*;
//
//public class MainBoard {
//    private List<ActionRoundCard> actionCards;
//    private List<ActionRoundCard> roundCards;
//    private List<CommonCard> majorImprovementCards;
//
//    public void setActionCards(List<ActionRoundCard> actionCards) {
//        this.actionCards = actionCards;
//    }
//
//    public void setRoundCards(List<ActionRoundCard> roundCards) {
//        this.roundCards = roundCards;
//    }
//
//    public void setMajorImprovementCards(List<CommonCard> majorImprovementCards) {
//        this.majorImprovementCards = majorImprovementCards;
//    }
//
//    public MainBoard() {
//        this.actionCards = new ArrayList<>();
//        this.roundCards = new ArrayList<>();
//        this.majorImprovementCards = new ArrayList<>();
//    }
//
//
//    public void initializeBoard(List<ActionRoundCard> actionCards, List<List<ActionRoundCard>> roundCycles, List<CommonCard> majorImprovementCards) {
//        this.actionCards = actionCards;
//        this.roundCards = new ArrayList<>();
//        for (List<ActionRoundCard> cycle : roundCycles) {
//            this.roundCards.addAll(cycle);
//        }
//        this.majorImprovementCards = majorImprovementCards;
//    }
//
//    public void revealRoundCard(int round) {
//        ActionRoundCard roundCard = roundCards.get(round - 1);
//        roundCard.reveal();
//        System.out.println("Round " + round + " card revealed: " + roundCard.getName());
//
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
//                System.out.println("card is accumulated " + card.getName());
//                ((AccumulativeCard) card).accumulateResources();
//            }
//        }
//        for (ActionRoundCard card : roundCards) {
//            if (card instanceof AccumulativeCard && card.isRevealed()) {
//                System.out.println("card is accumulated " + card.getName());
//                ((AccumulativeCard) card).accumulateResources();
//            }
//        }
//    }
//
//    public List<ActionRoundCard> getActionCards() {
////        return new ArrayList<>(actionCards); // Ensure a new list is returned to avoid modification issues
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
//    public List<CommonCard> getAvailableMajorImprovementCards() {
//        List<CommonCard> availableCards = new ArrayList<>();
//        for (CommonCard card : majorImprovementCards) {
//            if (card instanceof MajorImprovementCard && !((MajorImprovementCard) card).isPurchased()) {
//                availableCards.add(card);
//            }
//        }
//        return availableCards;
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
//        System.out.println("Removing card: " + card.getName());
//        majorImprovementCards.remove(card);
//    }
//
//    public boolean canPlaceFamilyMember(ActionRoundCard card) {
//        return !card.isOccupied();
//    }
//    public void placeFamilyMember(ActionRoundCard card) {
//        if (canPlaceFamilyMember(card)) {
//            card.setOccupied(true);
//        } else {
//            throw new IllegalStateException("Card is already occupied.");
//        }
//    }
//
//    public boolean isCardOccupied(ActionRoundCard card) {
//        return card.isOccupied();
//    }
//
//    public void resetFamilyMembersOnCards() {
//        System.out.println("Resetting family members on action cards...");
//        for (ActionRoundCard card : actionCards) {
//            System.out.println("Action card: " + card.getName() + ", occupied: " + card.isOccupied());
//            card.setOccupied(false);
//            System.out.println("Action card: " + card.getName() + ", occupied: " + card.isOccupied());
//        }
//        System.out.println("Resetting family members on round cards...");
//        for (ActionRoundCard card : roundCards) {
//            System.out.println("Round card: " + card.getName() + ", occupied: " + card.isOccupied());
//            card.setOccupied(false);
//            System.out.println("Round card: " + card.getName() + ", occupied: " + card.isOccupied());
//        }
//    }
//
//    private static List<String> getCardNames(List<? extends CommonCard> cards) {
//        List<String> cardNames = new ArrayList<>();
//        for (CommonCard card : cards) {
//            cardNames.add(card.getName());
//        }
//        return cardNames;
//    }
//
//
//    public void addCard(CommonCard card, String type) {
//        if (type.equals("action") && card instanceof ActionRoundCard) {
//            actionCards.add((ActionRoundCard) card);
//        } else if (type.equals("round") && card instanceof ActionRoundCard) {
//            roundCards.add((ActionRoundCard) card);
//        } else if (type.equals("majorImprovement") && card instanceof CommonCard) {
//            majorImprovementCards.add(card);
//        } else {
//            throw new IllegalArgumentException("Invalid card type or card type mismatch.");
//        }
//    }
//
//
//    public void printCardLists() {
//        System.out.println("Action Cards:");
//        for (ActionRoundCard card : actionCards) {
//            System.out.println("- " + card.getName());
//        }
//
//        System.out.println("Round Cards:");
//        for (ActionRoundCard card : roundCards) {
//            System.out.println("- " + card.getName());
//        }
//
//        System.out.println("Major Improvement Cards:");
//        for (CommonCard card : majorImprovementCards) {
//            System.out.println("- " + card.getName());
//        }
//    }
//
//
//}
//

package models;

import cards.common.AccumulativeCard;
import cards.common.ActionRoundCard;
import cards.common.CommonCard;
import cards.common.ExchangeableCard;
import cards.factory.imp.occupation.Lumberjack;
import cards.factory.imp.occupation.Lumberjack;
import cards.majorimprovement.MajorImprovementCard;
import cards.minorimprovement.MinorImprovementCard;
import enums.ExchangeTiming;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class MainBoard {
    private List<ActionRoundCard> actionCards;
    private List<ActionRoundCard> roundCards;
    private List<CommonCard> majorImprovementCards;

    public void setActionCards(List<ActionRoundCard> actionCards) {
        this.actionCards = actionCards;
    }

    public void setRoundCards(List<ActionRoundCard> roundCards) {
        this.roundCards = roundCards;
    }

    public void setMajorImprovementCards(List<CommonCard> majorImprovementCards) {
        this.majorImprovementCards = majorImprovementCards;
    }

    public MainBoard() {
        this.actionCards = new ArrayList<>();
        this.roundCards = new ArrayList<>();
        this.majorImprovementCards = new ArrayList<>();
    }

    public void initializeBoard(List<ActionRoundCard> actionCards, List<List<ActionRoundCard>> roundCycles, List<CommonCard> majorImprovementCards) {
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
        System.out.println("Round " + round + " card revealed: " + roundCard.getName());
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
                System.out.println("Accumulating resources for card: " + card.getName());
                ((AccumulativeCard) card).accumulateResources();
            }
        }
        for (ActionRoundCard card : roundCards) {
            if (card instanceof AccumulativeCard && card.isRevealed()) {
                System.out.println("Accumulating resources for revealed card: " + card.getName());
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

    public List<CommonCard> getAvailableMajorImprovementCards() {
        List<CommonCard> availableCards = new ArrayList<>();
        for (CommonCard card : majorImprovementCards) {
            if (card instanceof MajorImprovementCard && !((MajorImprovementCard) card).isPurchased()) {
                availableCards.add(card);
            }
        }
        return availableCards;
    }

    public List<CommonCard> getAllCards() {
        List<CommonCard> allCards = new ArrayList<>();
        allCards.addAll(actionCards);
        allCards.addAll(roundCards);
        allCards.addAll(majorImprovementCards);
        return allCards;
    }

    public void removeMajorImprovementCard(CommonCard card) {
        System.out.println("Removing major improvement card: " + card.getName());
        majorImprovementCards.remove(card);
    }

    public boolean canPlaceFamilyMember(ActionRoundCard card) {
        return !card.isOccupied();
    }

    public void placeFamilyMember(ActionRoundCard card) {
        if (canPlaceFamilyMember(card)) {
            card.setOccupied(true);
        } else {
            throw new IllegalStateException("Card is already occupied: " + card.getName());
        }
    }

    public boolean isCardOccupied(ActionRoundCard card) {
        return card.isOccupied();
    }

    public void resetFamilyMembersOnCards() {
        System.out.println("Resetting family members on action cards...");
        for (ActionRoundCard card : actionCards) {
            System.out.println("Action card: " + card.getName() + ", occupied: " + card.isOccupied());
            card.setOccupied(false);
            System.out.println("Action card: " + card.getName() + ", reset to occupied: " + card.isOccupied());
        }
        System.out.println("Resetting family members on round cards...");
        for (ActionRoundCard card : roundCards) {
            System.out.println("Round card: " + card.getName() + ", occupied: " + card.isOccupied());
            card.setOccupied(false);
            System.out.println("Round card: " + card.getName() + ", reset to occupied: " + card.isOccupied());
        }
    }

    public void addCard(CommonCard card, String type) {
        if (type.equals("action") && card instanceof ActionRoundCard) {
            actionCards.add((ActionRoundCard) card);
        } else if (type.equals("round") && card instanceof ActionRoundCard) {
            roundCards.add((ActionRoundCard) card);
        } else if (type.equals("majorImprovement") && card instanceof MajorImprovementCard) {
            majorImprovementCards.add(card);
        } else {
            throw new IllegalArgumentException("Invalid card type or card type mismatch.");
        }
    }

    public void printCardLists() {
        System.out.println("Action Cards:");
        for (ActionRoundCard card : actionCards) {
            System.out.println("- " + card.getName() + " (hashCode: " + card.hashCode() + ")");
        }

        System.out.println("Round Cards:");
        for (ActionRoundCard card : roundCards) {
            System.out.println("- " + card.getName() + " (hashCode: " + card.hashCode() + ")");
        }
    }

    public List<ActionRoundCard> getBuildOrRenovateCards() {
        return actionCards.stream()
                .filter(ActionRoundCard::executesBuildOrRenovate)
                .collect(Collectors.toList());
    }

    public void updateCardsWithDecorated(List<ActionRoundCard> originalCards, List<ActionRoundCard> decoratedCards) {
        for (int i = 0; i < actionCards.size(); i++) {
            if (originalCards.contains(actionCards.get(i))) {
                actionCards.set(i, decoratedCards.get(originalCards.indexOf(actionCards.get(i))));
            }
        }
        for (int i = 0; i < roundCards.size(); i++) {
            if (originalCards.contains(roundCards.get(i))) {
                roundCards.set(i, decoratedCards.get(originalCards.indexOf(roundCards.get(i))));
            }
        }
    }
    /**
     메인보드위의 카드 ID정보를 ArrayList<String>으로 보내줌.
     라운드 카드가 보일시 "id" 라운드 카드가 아직 보여지지 않는 카드일시 "x"+"id"
     ex) ["1", "2", "3", "4", "12", "52", "x9", "x5" ...]
     **/
    public ArrayList<String> sendMainBoardCardInfo(MainBoard mainBoard) {
        ArrayList <String> sendToFront = new ArrayList<String>();
        List<ActionRoundCard> actionCards = mainBoard.getActionCards();
        for (ActionRoundCard card : actionCards) {
            String playerInfo = String.valueOf(card.getId());
            if (card.isRevealed()) {
                playerInfo = "x" + playerInfo;
            }
            sendToFront.add(playerInfo);
        }
        //라운드카드
        List<ActionRoundCard> roundCards = mainBoard.getRoundCards();
        for (ActionRoundCard card : roundCards) {
            String playerInfo = String.valueOf(card.getId());
            if (card.isRevealed()) {
                playerInfo = "x" + playerInfo;
            }
            sendToFront.add(playerInfo);
        }
        return sendToFront;
        //카드마다 플레이어가 놓을수있는지없는지 전달.
    }
    /**
     메인보드위의 플레이어들 ID정보를 ArrayList<String>으로 보내줌 플레이어가 점유중일시 "id", 그렇지 않을시 "null"
     ex) ["1", "null", "4", "2", "3"] 1번, 없음, 4번, 2번, 3번 점유중
     ActionRoundCard에서 getPlayerId 구현필요.
     **/
    public ArrayList<String> sendMainBoardFamilyInfo(MainBoard mainBoard) {
        ArrayList <String> sendToFront = new ArrayList<String>();
        List<ActionRoundCard> actionCards = mainBoard.getActionCards();
        for (ActionRoundCard card : actionCards) {
            //카드에 플레이어있을때
            if (card.isOccupied()) {
                //String playerInfo = card.getPlayerId();
                //sendToFront.add(playerInfo);
            }
            else
                sendToFront.add("null");
        }
        //라운드카드
        List<ActionRoundCard> roundCards = mainBoard.getRoundCards();
        for (ActionRoundCard card : roundCards) {
            //카드에 플레이어있을때
            if (card.isOccupied()) {
                String playerInfo = card.getPlayerId();
                sendToFront.add(playerInfo);
            }
            else
                sendToFront.add("null");
        }
        return sendToFront;
        //카드마다 플레이어가 놓을수있는지없는지 전달.
    }

    /**
     메인보드위의 카드들의 축적자원정보를 ArrayList<ArrayList<String>>으로 보내줌,
     자원이 축적되는 칸일시 "[자원:개수, 자원2:개수] "축적되는 칸이아닐시 ["No Ack"] 자원관련칸이 아닌경우 ["null"]
     ex) [[wood:1, clay:2], ["No ack"], [clay:2], ["null"]] 이런식으로 프론트에게 전달.
     **/
    public ArrayList<ArrayList<String>> sendMainBoardResourceInfo(MainBoard mainBoard) {
        ArrayList <ArrayList<String>> sendToFront = new ArrayList<>();

        List<ActionRoundCard> roundCards = mainBoard.getActionCards();
        for (ActionRoundCard card : roundCards) {
            ArrayList <String> resources = new ArrayList<>();
            //축적칸일때
            if (card.isAccumulative()) {
                AccumulativeCard accCard = (AccumulativeCard) card;
                HashMap<String, Integer> resource = (HashMap<String, Integer>) accCard.getAccumulatedResources();
                for (Map.Entry<String, Integer> entry : resource.entrySet()) {
                    String key = entry.getKey();
                    String value = String.valueOf(entry.getValue());
                    resources.add(key + ":" + value);
                }
            }
            //자원축적칸이아닐때
            else if (card.isResource()){
                resources.add("wood:2");
            }
            //자원을 주는 칸이 아닐때
            else {
                resources.add("null");
            }
            sendToFront.add(resources);
        }

        return sendToFront;
    }
    /**
     현재 플레이어가 놓을 수 있는 칸 정보 전달.
     **/

    /**
     교환 가능한 카드 목록을 ArrayList<String>으로 보내줌.
     교환 가능 카드인경우 "id", 불가능한경우 "x" + "id"
     ex) ["1", "x", "3".... ]
     **/
    public ArrayList<String> sendMainBoardExchangableCardInfo(MainBoard mainBoard, Player player, ExchangeTiming timing) {
        ArrayList <String> sendToFront = new ArrayList<>();
        List<CommonCard> majorCards = mainBoard.getMajorImprovementCards();
        ArrayList<CommonCard> majorImprovementCards = (ArrayList<CommonCard>) player.getMajorImprovementCards();
        for (CommonCard card : majorImprovementCards) {
            int id = card.getId();
            //다운캐스팅 가능할때
            if (card instanceof ExchangeableCard) {
                ExchangeableCard exchangeableCard = (ExchangeableCard) card;
                //현재 교환가능한 카드 일시
                if (exchangeableCard.canExchange(timing)) {
                    sendToFront.add("id");
                }
                else {
                    sendToFront.add("x" + "id");
                }
            }
        }
        return sendToFront;
    }
    /**
     구매할 수 있는 주 설비 카드 목록을 ArrayList<String>으로 보내줌.
     구매가능한경우 "id", 불가능한경우 "x" + "id"
     ex) ["1", "x", "3".... ]
     **/
    public ArrayList<String> sendMainBoardMajorImprovementInfo(MainBoard mainBoard) {
        ArrayList <String> sendToFront = new ArrayList<>();
        List<CommonCard> majorCards = mainBoard.getMajorImprovementCards();
        for (CommonCard majorCard : majorCards) {
            if (majorCard instanceof MajorImprovementCard && !((MajorImprovementCard) majorCard).isPurchased()) {
                sendToFront.add(String.valueOf(majorCard.getId()));
            }
            else {
                ((MajorImprovementCard) majorCard).isPurchased();
                sendToFront.add("x"+ String.valueOf(majorCard.getId()));
            }
        }
        return sendToFront;
    }
}
