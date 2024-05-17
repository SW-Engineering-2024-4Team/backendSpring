package cards.common;

import cards.majorimprovement.MajorImprovementCard;
import models.FamilyMember;
import models.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Card {
    void execute(Player player); // 카드를 실행할 때 필요한 메서드
    String getName(); // 카드의 이름을 가져오는 메서드
    String getDescription(); // 카드의 설명을 가져오는 메서드

    // 기능 조합 유틸리티 메서드

    // '그리고/또는' 조합
    default void executeAndOr(Player player, Runnable... actions) {
        for (Runnable action : actions) {
            action.run();
        }
    }

    // '또는' 조합
    default void executeOr(Player player, Runnable action1, Runnable action2) {
        // 플레이어가 선택 가능: 둘 중 하나만 선택
        // 선택 로직은 실제 게임 로직에 따라 구현 필요
        boolean choice = player.chooseOption(); // 플레이어가 선택한 옵션
        if (choice) {
            action1.run();
        } else {
            action2.run();
        }
    }

    // '한 후에' 조합
    default void executeThen(Player player, Runnable action1, Runnable action2) {
        action1.run();
        action2.run();
    }


    // 자원 획득 메서드
    default void gainResources(Player player, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            player.addResource(entry.getKey(), entry.getValue());
        }
    }

    // 자원 누적 메서드
    default void accumulate() {
        Map<String, Integer> accumulatedResources = new HashMap<>();
        for (Map.Entry<String, Integer> entry : accumulatedResources.entrySet()) {
            accumulatedResources.put(entry.getKey(), accumulatedResources.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }


    // 카드 효과 변경 메서드
    void modifyEffect(String effectType, Object value);

    // 3. 직업 카드 사용
    default void useOccupationCard(Player player) {
        // 직업 카드 사용 로직
    }

    // 4. 방 짓기
    default void buildRoom(Player player) {
//        player.getPlayerBoard().buildStructure("room");
    }

    // 5. 외양간 짓기
    default void buildStable(Player player) {
//        player.getPlayerBoard().buildStructure("stable");
    }

    // 6. 선 플레이어 되기
    default void becomeFirstPlayer(Player player) {
        // 선 플레이어 변경 로직
//        player.setFirstPlayer(true);
    }

    // 7. 밭 짓기
    default void buildField(Player player) {
//        player.getPlayerBoard().buildStructure("field");
    }

    // 8. 울타리 치기
    default void buildFence(Player player) {
//        player.getPlayerBoard().buildStructure("fence");
    }

    // 9. 주요 설비 카드 구매
    default void purchaseMajorImprovementCard(Player player) {
        // 주요 설비 카드 구매 로직
    }

    // 10. 보조 설비 카드 사용
    default void useMinorImprovementCard(Player player) {
        // 보조 설비 카드 사용 로직
    }

    // 11. 신생아 추가
    default void addNewborn(Player player) {
//        player.getPlayerBoard().addFamilyMember(new FamilyMember(false));
    }

    // 12. 방 고치기
    default void renovateRooms(Player player) {
//        player.getPlayerBoard().renovateRooms();
    }

    // 자원 체크 및 지불 메서드
    default boolean checkAndPayResources(Player player, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            if (player.getResource(entry.getKey()) < entry.getValue()) {
                return false; // 자원이 부족하면 false 반환
            }
        }
        // 자원이 충분하면 지불
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            player.addResource(entry.getKey(), -entry.getValue());
        }
        return true; // 성공적으로 지불하면 true 반환
    }

    // 새로운 빵굽기 트리거 메서드 추가
    default void triggerBreadBaking(Player player) {
        // 플레이어가 소유한 주요 설비 카드 중 빵굽기 기능이 있는 카드를 가져옵니다.
        List<MajorImprovementCard> breadBakingCards = player.getBreadBakingCards();
        // 프론트엔드에 빵굽기 가능한 카드 목록을 전송합니다.
        // WebSocketService.sendMessageToClient(player.getId(), "breadBakingCards", breadBakingCards);
    }
}
