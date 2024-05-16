package cards;

import actions.Action;
import models.Player;

import java.util.List;

public interface Card {
    boolean execute(Player player);
    List<Action> getActions();
    void addAction(Action action);

    void addPrerequisite(Action prerequisite);

    default void accumulateResources() {
        // 기본 구현은 아무 것도 하지 않음
    }

    default void modifyAction(String actionType, int newValue) {
        // 기본 구현은 아무 것도 하지 않음
    }

    default int getModifiedAction(String actionType, int defaultValue) {
        return defaultValue; // 기본 구현
    }
}
