package cards.majorimprovement;

import cards.common.ExchangeTiming;
import models.Player;
import java.util.Map;

public class ExampleMajorImprovementCard implements MajorImprovementCard {
    private String name;
    private String description;
    private Map<String, Integer> cost;
    private int additionalPoints;

    public ExampleMajorImprovementCard(String name, String description, Map<String, Integer> cost, int additionalPoints) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.additionalPoints = additionalPoints;
    }

    @Override
    public void execute(Player player) {
//        if (checkCondition(player)) {
//            payCost(player);
//            applyEffect(player);
//        }
    }

    @Override
    public boolean hasExchangeResourceFunction() {
        return false; // 예시로 교환 기능이 없음
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
    public int calculateScore(Player player) {
        // 점수 계산 로직 구현
        return 0; // 예시로 단순히 0점 반환
    }

    @Override
    public boolean hasBreadBakingFunction() {
        return true; // 이 예시는 빵굽기 기능이 있음
    }

    @Override
    public void bakeBread(Player player, Map<String, Integer> exchangeRate) {
        // 빵굽기 로직 구현
    }

    @Override
    public void applyEffect(Player player) {
        // 기본 효과 적용 로직
    }
}
