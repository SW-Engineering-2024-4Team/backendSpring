package cards.rounds;

import models.Player;
import java.util.Map;
import java.util.HashMap;

public class ExampleRoundCard implements RoundCard {
    private String name;
    private String description;
    private int cycle;
    private boolean revealed;
    private boolean accumulative;
    private Map<Player, Map<String, Integer>> playerResourceModifications = new HashMap<>();
    private Map<Player, String> playerPaymentResource = new HashMap<>();
    private Map<Player, Integer> playerAnimalCapacity = new HashMap<>();

    public ExampleRoundCard(String name, String description, int cycle, boolean accumulative) {
        this.name = name;
        this.description = description;
        this.cycle = cycle;
        this.revealed = false;
        this.accumulative = accumulative;
    }

    @Override
    public void execute(Player player) {
        // 기존 자원 획득 로직에 자원 변경 사항 적용
        Map<String, Integer> resources = new HashMap<>(Map.of("wood", 2, "clay", 1));
        Map<String, Integer> modifications = playerResourceModifications.getOrDefault(player, new HashMap<>());
        for (Map.Entry<String, Integer> entry : modifications.entrySet()) {
            resources.put(entry.getKey(), resources.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
        gainResources(player, resources);
        becomeFirstPlayer(player);
    }

    @Override
    public void accumulate() {
        // 누적 로직
    }

    @Override
    public void modifyEffect(String effectType, Object value) {

    }

    @Override
    public void modifyEffect(String effectType, Player player, Object value) {
        switch (effectType) {
            case "addExtraResource":
                playerResourceModifications.put(player, (Map<String, Integer>) value);
                break;
            case "changePaymentResource":
                playerPaymentResource.put(player, (String) value);
                break;
            case "increaseAnimalCapacity":
                playerAnimalCapacity.put(player, (int) value);
                break;
            default:
                throw new IllegalArgumentException("Unknown effect type: " + effectType);
        }
    }

    @Override
    public void addResourceModification(Player player, Map<String, Integer> resourceModification) {
        playerResourceModifications.put(player, resourceModification);
    }

    @Override
    public void changePaymentResource(Player player, String newResourceType) {
        playerPaymentResource.put(player, newResourceType);
    }

    @Override
    public void increaseAnimalCapacity(Player player, int extraCapacity) {
        playerAnimalCapacity.put(player, extraCapacity);
    }

    @Override
    public Map<Player, Map<String, Integer>> getResourceModifications() {
        return playerResourceModifications;
    }

    @Override
    public Map<Player, String> getPaymentResources() {
        return playerPaymentResource;
    }

    @Override
    public Map<Player, Integer> getAnimalCapacities() {
        return playerAnimalCapacity;
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
    public void reveal() {
        this.revealed = true;
    }

    @Override
    public int getCycle() {
        return cycle;
    }

    @Override
    public boolean isAccumulative() {
        return accumulative;
    }

    @Override
    public boolean isRevealed() {
        return revealed;
    }
}
