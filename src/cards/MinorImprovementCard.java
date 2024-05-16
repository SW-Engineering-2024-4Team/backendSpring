package cards;

import actions.Action;
import actions.CheckResourcesAction;
import models.Player;

import java.util.Map;

public class MinorImprovementCard extends AbstractCard {
    public MinorImprovementCard() {
        // 기본 생성자
    }

    public MinorImprovementCard(Map<String, Integer> requiredResources) {
        addPrerequisite(new CheckResourcesAction(requiredResources));
    }

    @Override
    public boolean execute(Player player) {
        return super.execute(player);
    }
}
