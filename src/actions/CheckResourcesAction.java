package actions;

import models.Player;

import java.util.Map;

public class CheckResourcesAction implements Action {
    private Map<String, Integer> requiredResources;

    public CheckResourcesAction(Map<String, Integer> requiredResources) {
        this.requiredResources = requiredResources;
    }

    public boolean checkPrerequisites(Player player) {
        for (Map.Entry<String, Integer> entry : requiredResources.entrySet()) {
            String resource = entry.getKey();
            int requiredAmount = entry.getValue();
            if (player.getResource(resource) < requiredAmount) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(Player player) {
        
    }
}
