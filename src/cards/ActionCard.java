package cards;

import actions.Action;
import actions.CheckResourcesAction;
import actions.GainResourceAction;
import models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionCard extends AbstractCard {
    public ActionCard() {
        // 기본 생성자
    }

    public ActionCard(Map<String, Integer> requiredResources) {
        addPrerequisite(new CheckResourcesAction(requiredResources));
    }

    @Override
    public boolean execute(Player player) {
        return super.execute(player);
    }

    @Override
    public void accumulateResources() {
        for (Action action : getActions()) {
            if (action instanceof GainResourceAction) {
                ((GainResourceAction) action).accumulateResource();
            }
        }
    }
}
