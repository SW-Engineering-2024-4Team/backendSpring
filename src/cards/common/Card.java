package cards.common;

import models.FamilyMember;
import models.Player;

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

    // 13. 자원 체크
    default boolean checkResources(Player player, Map<String, Integer> requiredResources) {
        for (Map.Entry<String, Integer> entry : requiredResources.entrySet()) {
            if (player.getResource(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    // 14. 자원 지불
    default void payResources(Player player, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            player.addResource(entry.getKey(), -entry.getValue());
        }
    }
}
