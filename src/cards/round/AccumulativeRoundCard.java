package cards.round;

import cards.action.AccumulativeActionCard;
import cards.common.AccumulativeCard;
import models.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccumulativeRoundCard implements AccumulativeCard {
    private int id;
    private String name;
    private String description;
    private int cycle;
    private boolean revealed;
    private boolean occupied;
    private Map<String, Integer> accumulatedResources;
    private Map<String, Integer> accumulatedAmounts;

    public AccumulativeRoundCard(int id, String name, String description, int cycle, Map<String, Integer> accumulatedAmounts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cycle = cycle;
        this.revealed = false;
        this.occupied = true;
        this.accumulatedResources = new HashMap<>();
        this.accumulatedAmounts = new HashMap<>();
    }

    @Override
    public void execute(Player player) {
        // 누적된 자원을 플레이어에게 부여
        gainResources(player, accumulatedResources);
        accumulatedResources.clear();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public int getCycle() {
        return cycle;
    }

    @Override
    public boolean isRevealed() {
        return revealed;
    }

    @Override
    public void reveal() {
        revealed = true;
    }

    @Override
    public boolean isAccumulative() {
        return true; // 자원 누적 가능
    }

    @Override
    public Map<String, Integer> getAccumulatedResources() {
        return accumulatedResources;
    }

    @Override
    public void clearAccumulatedResources() {
        accumulatedResources.clear();
    }

    @Override
    public void accumulateResources() {
        if (!occupied) {
            for (Map.Entry<String, Integer> entry : accumulatedAmounts.entrySet()) {
                String resource = entry.getKey();
                int amount = entry.getValue();
                accumulatedResources.put(resource, accumulatedResources.getOrDefault(resource, 0) + amount);
            }
        }
        if (occupied) {
            for (String resource : accumulatedResources.keySet()) {
                accumulatedResources.put(resource, accumulatedAmounts.getOrDefault(resource, 0));
            }
            setOccupied(false);
        }
    }


    @Override
    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AccumulativeActionCard that = (AccumulativeActionCard) o;
//        return Objects.equals(name, that.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }
}
