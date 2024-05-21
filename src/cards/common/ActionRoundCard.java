package cards.common;

import cards.majorimprovement.MajorImprovementCard;
import enums.RoomType;
import models.Animal;
import models.MainBoard;
import models.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ActionRoundCard extends CommonCard {
    boolean isRevealed(); // RoundCard와 ActionCard 구분을 위해 추가
    void reveal(); // 라운드 카드를 공개하는 메서드
    boolean isAccumulative(); // 누적 가능한지 여부 확인 메서드

    // 자원 획득
    default void gainResources(Player player, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            String resource = entry.getKey();
            int amount = entry.getValue();

            if (resource.equals("sheep")) {
                // 양 자원일 경우
                player.addResource(resource, amount);
                for (int i = 0; i < amount; i++) {
                    // 양 객체를 추가하여 플레이어 보드에 배치
                    player.getPlayerBoard().addAnimal(new Animal(-1, -1, resource));
                }
            } else {
                // 일반 자원일 경우
                player.addResource(resource, amount);
            }
        }
    }


    // 직업 카드 사용
    default void useOccupationCard(Player player) {
        // 직업 카드 사용 로직
        List<CommonCard> occupationCards = player.getOccupationCards();
        if (!occupationCards.isEmpty()) {
            // 플레이어가 카드를 선택하는 로직 (예시로 첫 번째 카드를 선택했다고 가정)
            UnifiedCard selectedCard = (UnifiedCard) occupationCards.get(0);
            player.useUnifiedCard(selectedCard);
        }
    }

    // 보조 설비 카드 사용
    default void useMinorImprovementCard(Player player) {
        // 보조 설비 카드 사용 로직
        List<CommonCard> minorImprovementCards = player.getMinorImprovementCards();
        if (!minorImprovementCards.isEmpty()) {
            // 플레이어가 카드를 선택하는 로직 (예시로 첫 번째 카드를 선택했다고 가정)
            UnifiedCard selectedCard = (UnifiedCard) minorImprovementCards.get(0);
            player.useUnifiedCard(selectedCard);
        }
    }

    // 주요 설비 카드 구매
    default void purchaseMajorImprovementCard(Player player, MainBoard mainBoard, int cardId) {
        // 메인 보드에서 구매 가능한 주요 설비 카드를 확인합니다.
        List<CommonCard> majorImprovementCards = mainBoard.getMajorImprovementCards();
        MajorImprovementCard cardToPurchase = null;

        for (CommonCard card : majorImprovementCards) {
            if (card.getId() == cardId && card instanceof MajorImprovementCard) {
                cardToPurchase = (MajorImprovementCard) card;
                break;
            }
        }

        if (cardToPurchase != null && player.checkResources(cardToPurchase.getPurchaseCost())) {
            player.payResources(cardToPurchase.getPurchaseCost());
            player.addMajorImprovementCard(cardToPurchase);
            mainBoard.removeMajorImprovementCard(cardToPurchase);
        } else {
            // 플레이어에게 구매가 불가능하다는 메시지를 표시합니다.
//            notifyPlayer(player, "자원이 부족하여 주요 설비 카드를 구매할 수 없습니다.");
        }
    }

    // 빵굽기 행동(주요 설비 카드 사용)
    default void triggerBreadBaking(Player player) {
        List<CommonCard> majorImprovementCards = player.getMajorImprovementCards();
        List<BakingCard> bakingCards = majorImprovementCards.stream()
                .filter(card -> card instanceof BakingCard)
                .map(card -> (BakingCard) card)
                .collect(Collectors.toList());

        if (!bakingCards.isEmpty()) {
            BakingCard selectedCard = player.selectBakingCard(bakingCards);
            selectedCard.triggerBreadBaking(player);
        } else {
            // 빵굽기 가능한 설비가 없다는 메시지 표시
        }
    }

    // 기물 짓기

    // 집 짓기
    default void buildHouse(Player player, int x, int y, RoomType type) {
        Map<String, Integer> playerResources = player.getResources(); // 플레이어의 자원을 가져옴
        if (player.getPlayerBoard().canBuildHouse(x, y, type, playerResources)) {
            Map<String, Integer> cost = player.getHouseResourceCost(type);
            if (player.checkResources(cost)) {
                player.payResources(cost);
                player.getPlayerBoard().buildHouse(x, y, type);
            } else {
                // 자원이 부족하다는 메시지 표시
                System.out.println("자원이 부족합니다.");
            }
        } else {
            // 집을 지을 수 없는 조건이라는 메시지 표시
            System.out.println("집을 지을 수 없습니다.");
        }
    }

    // 밭 일구기
    default void plowField(Player player, int x, int y) {
        player.getPlayerBoard().plowField(x, y);
    }

    // TODO 외양간 짓기
    default void buildBarn(Player player, int x, int y) {
        player.getPlayerBoard().buildBarn(x, y);
    }

    // TODO 울타리 치기
    default void buildFence(Player player, int startX, int startY, int endX, int endY) {
        player.getPlayerBoard().buildFence(startX, startY, endX, endY);
    }

    // 곡식 심기
    default void plantField(Player player, int x, int y, int initialCrops) {
        player.getPlayerBoard().plantField(x, y, initialCrops);
    }

    // 선 플레이어 되기
    default void becomeFirstPlayer(Player player) {
        player.setFirstPlayer(true);
    }

    // 객체 추가

    // 1. 가족 구성원 추가
    default void addNewborn(Player player) {
        player.addFamilyMember();
    }

    // 기물 변경

    // TODO  집 변경
    default void renovateRooms(Player player) {
        player.getPlayerBoard().changeHouse();
    }

//    // 자원 체크 및 지불
//    default boolean checkResources(Player player, Map<String, Integer> resources) {
//        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
//            if (player.getResource(entry.getKey()) < entry.getValue()) {
//                return false; // 자원이 부족하면 false 반환
//            }
//        }
//        return true; // 자원이 충분하면 true 반환
//    }
//
//    default void payResources(Player player, Map<String, Integer> resources) {
//        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
//            player.addResource(entry.getKey(), -entry.getValue());
//        }
//    }

    // 추가 효과 확인 및 적용 메서드
    default void applyAdditionalEffects(Player player) {
        // 추가 효과가 필요한 경우 오버라이드하여 구현
    }
}
