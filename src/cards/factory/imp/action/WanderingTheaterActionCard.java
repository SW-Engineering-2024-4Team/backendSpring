//package cards.factory.imp.action;
//
//import cards.action.AccumulativeActionCard;
//import cards.common.AccumulativeCard;
//import models.Player;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class WanderingTheaterActionCard extends AccumulativeActionCard {
//    public WanderingTheaterActionCard(int id, String name, String description) {
//        super(id, name, description);
//    }
//
//    @Override
//    public void execute(Player player) {
//        if (getAccumulatedResources().containsKey("food")) {
//            int food = getAccumulatedResources().get("food");
//            player.addResource("food", food);
//            getAccumulatedResources().put("food", 0);
//        }
//    }
//}
