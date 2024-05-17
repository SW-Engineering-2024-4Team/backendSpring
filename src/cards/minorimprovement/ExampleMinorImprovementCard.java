package cards.minorimprovement;

import cards.common.ExchangeTiming;
import enums.RoomType;
import models.Player;
import java.util.Map;
import java.util.HashMap;

public class ExampleMinorImprovementCard implements MinorImprovementCard {
    private String name;
    private String description;
    private Map<String, Integer> cost;
    private int additionalPoints;
    private boolean conditionMet;

    public ExampleMinorImprovementCard(String name, String description, Map<String, Integer> cost, int additionalPoints, boolean conditionMet) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.additionalPoints = additionalPoints;
        this.conditionMet = conditionMet;
    }

    @Override
    public void execute(Player player) {
        if (checkCondition(player)) {
            payCost(player);
            applyEffect(player);
        }
    }

    @Override
    public boolean hasExchangeResourceFunction() {
        return false; // 이 예시에서는 교환 기능이 없음
    }

    @Override
    public ExchangeTiming getExchangeTiming() {
        return ExchangeTiming.ANYTIME; // 교환 타이밍 설정
    }

    @Override
    public void exchangeResources(Player player, String fromResource, String toResource, int amount) {
        // 교환 로직 구현
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void modifyEffect(String effectType, Object value) {

    }

    @Override
    public Map<String, Integer> getCost() {
        return cost;
    }

    @Override
    public int getAdditionalPoints() {
        return additionalPoints;
    }

    @Override
    public boolean checkCondition(Player player) {
        // 조건 확인 로직
        return conditionMet; // 예시로 단순히 조건을 반환
    }

    @Override
    public void applyEffect(Player player) {
        // 기본 효과 적용 로직
        if (player.getPlayerBoard().getTiles().equals(RoomType.WOOD)) {
            player.addResource("food", 1); // 매 라운드 시작 시 나무 집 타입이면 음식 1개 제공
        }
    }
}
