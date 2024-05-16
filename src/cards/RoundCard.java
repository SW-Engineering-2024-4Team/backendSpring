package cards;

import actions.Action;
import actions.CheckResourcesAction;
import actions.GainResourceAction;
import models.Player;

import java.util.Map;

public class RoundCard extends AbstractCard {
    private boolean revealed;

    public RoundCard() {
        revealed = false;
    }

    public RoundCard(Map<String, Integer> requiredResources) {
        revealed = false;
        addPrerequisite(new CheckResourcesAction(requiredResources));
    }

    @Override
    public boolean execute(Player player) {
        return super.execute(player);
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isRevealed() {
        return revealed;
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
