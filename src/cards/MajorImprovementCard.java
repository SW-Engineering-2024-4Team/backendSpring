package cards;

import actions.Action;
import models.Player;

import java.util.List;
import java.util.Map;

public class MajorImprovementCard extends AbstractCard {
    private int points; // 카드의 점수
    private Map<String, Integer> cost; // 구입 비용
    private boolean availableAnytime; // 아무때나 자원교환 가능 여부
    private boolean availableDuringHarvest; // 수확 때 자원교환 가능 여부
    private boolean availableDuringBakeBread; // 빵굽기 시 자원교환 가능 여부
    private Map<String, Integer> resourceToPoints; // 자원에 따른 추가 점수

    // 생성자
    public MajorImprovementCard(int points, Map<String, Integer> cost, boolean availableAnytime,
                                boolean availableDuringHarvest, boolean availableDuringBakeBread,
                                Map<String, Integer> resourceToPoints) {
        this.points = points;
        this.cost = cost;
        this.availableAnytime = availableAnytime;
        this.availableDuringHarvest = availableDuringHarvest;
        this.availableDuringBakeBread = availableDuringBakeBread;
        this.resourceToPoints = resourceToPoints;
    }

    // 구입 비용 체크
    public boolean canAfford(Player player) {
        for (Map.Entry<String, Integer> entry : cost.entrySet()) {
            if (player.getResource(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    // 구입 처리
    public void purchase(Player player) {
        if (canAfford(player)) {
            for (Map.Entry<String, Integer> entry : cost.entrySet()) {
                player.addResource(entry.getKey(), -entry.getValue());
            }
            player.addCard(this, "active");
        } else {
            throw new IllegalStateException("Player cannot afford this card.");
        }
    }

    // 자원 교환
    public void exchangeResources(Player player, String fromResource, String toResource, int amount) {
        int playerResource = player.getResource(fromResource);
        if (playerResource >= amount) {
            player.addResource(fromResource, -amount);
            player.addResource(toResource, amount);
        } else {
            throw new IllegalStateException("Player does not have enough resources to exchange.");
        }
    }

    // 점수 계산
    public int calculateAdditionalPoints(Player player) {
        int additionalPoints = 0;
        for (Map.Entry<String, Integer> entry : resourceToPoints.entrySet()) {
            int playerResource = player.getResource(entry.getKey());
            additionalPoints += (playerResource / entry.getValue());
        }
        return additionalPoints;
    }

    // 특정 조건에 따라 자원 교환 가능 여부 반환
    public boolean isAvailableAnytime() {
        return availableAnytime;
    }

    public boolean isAvailableDuringHarvest() {
        return availableDuringHarvest;
    }

    public boolean isAvailableDuringBakeBread() {
        return availableDuringBakeBread;
    }

    // 카드의 점수 반환
    public int getPoints() {
        return points;
    }

    // 카드 실행 메서드
    @Override
    public boolean execute(Player player) {
        // 여기에 해당 카드가 실행될 때의 로직을 추가
        return true;
    }
}
