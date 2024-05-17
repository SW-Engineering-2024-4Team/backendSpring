package cards.occupation;

import cards.common.ExchangeTiming;
import models.Player;

public class ExampleOccupationCard implements OccupationCard {
    private String name;
    private String description;
    private int maxPlayers;
    private int minPlayers;
    private ExchangeTiming exchangeTiming;

    public ExampleOccupationCard(String name, String description, int maxPlayers, int minPlayers, ExchangeTiming exchangeTiming) {
        this.name = name;
        this.description = description;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.exchangeTiming = exchangeTiming;
    }

    @Override
    public void execute(Player player) {
        // 직업 카드 실행 로직
    }

    @Override
    public boolean hasExchangeResourceFunction() {
        return true; // 이 직업 카드는 교환 기능이 있음
    }

    @Override
    public ExchangeTiming getExchangeTiming() {
        return exchangeTiming;
    }

    @Override
    public void exchangeResources(Player player, String fromResource, String toResource, int amount) {
        // 교환 로직 구현
        if (player.getResource(fromResource) >= amount) {
            player.addResource(fromResource, -amount);
            player.addResource(toResource, amount);
        }
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
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }
}
