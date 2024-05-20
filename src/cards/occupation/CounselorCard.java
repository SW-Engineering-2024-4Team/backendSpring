package cards.occupation;

import cards.common.AbstractCard;
import enums.ExchangeTiming;
import models.Player;
import java.util.HashMap;
import java.util.Map;

public class CounselorCard extends AbstractCard implements OccupationCard {
    private int maxPlayers;
    private int minPlayers;

    public CounselorCard(String name, String description, int maxPlayers, int minPlayers) {
        super(name, description);
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }

    @Override
    public void execute(Player player) {
        int playerCount = player.getGameController().getPlayers().size();
        Map<String, Integer> resources = new HashMap<>();

        switch (playerCount) {
            case 1:
                resources.put("grain", 2);
                break;
            case 2:
                resources.put("clay", 3);
                break;
            case 3:
                resources.put("stone", 2);
                break;
            case 4:
                resources.put("sheep", 2);
                break;
            default:
                throw new IllegalArgumentException("Invalid number of players: " + playerCount);
        }

        gainOccupationResources(player, resources);
    }

    @Override
    public boolean hasExchangeResourceFunction() {
        return false; // 이 직업 카드는 교환 기능이 없음
    }

    @Override
    public ExchangeTiming getExchangeTiming() {
        return null;
    }

    @Override
    public void exchangeResources(Player player, String fromResource, String toResource, int amount) {

    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    @Override
    public void gainOccupationResources(Player player, Map<String, Integer> resources) {
        for (Map.Entry<String, Integer> entry : resources.entrySet()) {
            player.addResource(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void modifyEffect(String effectType, Player player, Object value) {
        switch (effectType) {
            case "addExtraResource":
                // 효과 변경 로직
                break;
            case "changePaymentResource":
                // 효과 변경 로직
                break;
            case "increaseAnimalCapacity":
                // 효과 변경 로직
                break;
            default:
                throw new IllegalArgumentException("Unknown effect type: " + effectType);
        }
    }

    @Override
    public void accumulate() {

    }

    @Override
    public Map<Player, Map<String, Integer>> getResourceModifications() {
        return null;
    }

    @Override
    public Map<Player, String> getPaymentResources() {
        return null;
    }

    @Override
    public Map<Player, Integer> getAnimalCapacities() {
        return null;
    }
}
