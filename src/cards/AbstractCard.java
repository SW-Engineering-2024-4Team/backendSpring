package cards;

import actions.Action;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCard implements Card {
    private Map<String, Integer> modifiedActions = new HashMap<>();
    private List<Action> actions = new ArrayList<>();
    private List<Action> prerequisites = new ArrayList<>();

    @Override
    public void modifyAction(String actionType, int newValue) {
        modifiedActions.put(actionType, newValue);
    }

    @Override
    public int getModifiedAction(String actionType, int defaultValue) {
        return modifiedActions.getOrDefault(actionType, defaultValue);
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public void addPrerequisite(Action prerequisite) {
        prerequisites.add(prerequisite);
    }

    @Override
    public boolean execute(Player player) {
        for (Action prerequisite : prerequisites) {
            if (!prerequisite.checkPrerequisites(player)) {
                return false; // 선행 조건을 통과하지 못한 경우 실행 중단
            }
        }
        for (Action action : actions) {
            action.execute(player);
        }
        return true;
    }
}
