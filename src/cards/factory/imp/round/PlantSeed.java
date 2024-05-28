package cards.factory.imp.round;

import cards.round.AccumulativeRoundCard;
import cards.round.NonAccumulativeRoundCard;
import models.Player;

import java.util.HashMap;
import java.util.Map;

public class PlantSeed extends NonAccumulativeRoundCard {

    public PlantSeed(int id, int cycle) {
        super(id, "곡식 활용", "밭 1개에 곡식을 심습니다.", cycle);
    }

//    private static Map<String, Integer> neededResources() {
//        Map<String, Integer> neededResources = new HashMap<>();
//        neededResources.put("grain", 1);
//
//        return neededResources;
//    }


    @Override
    public void execute(Player player) {
        plantField(player);
    }
}
