package cards.common;

import cards.majorimprovement.MajorImprovementCard;
import enums.RoomType;
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
            player.addResource(entry.getKey(), entry.getValue());
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
    default void buildHouse(Player player, int x, int y, RoomType type) {
        player.getPlayerBoard().buildHouse(x, y, type);
    }

    default void buildBarn(Player player, int x, int y) {
        player.getPlayerBoard().buildBarn(x, y);
    }

    default void buildFence(Player player, int startX, int startY, int endX, int endY) {
        player.getPlayerBoard().buildFence(startX, startY, endX, endY);
    }

    default void plantField(Player player, int x, int y, int initialCrops) {
        player.getPlayerBoard().plantField(x, y, 3);
    }

    // 선 플레이어 되기
    default void becomeFirstPlayer(Player player) {
        player.setFirstPlayer(true);
    }

    // 객체 추가
    default void addNewborn(Player player) {
        player.getPlayerBoard().addFamilyMember(false);
    }

    // 기물 변경
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
