package cards;

import actions.*;
import models.Player;

import java.util.List;
import java.util.Map;

public class CardFactory {
    public Card createCard(String type) {
        switch (type) {
            case "action":
                return new ActionCard();
            case "round":
                return new RoundCard();
            case "occupation":
                return new OccupationCard();
            case "minorImprovement":
                return new MinorImprovementCard();
            // 다른 카드 타입에 대한 생성 로직 추가
            default:
                throw new IllegalArgumentException("Unknown card type: " + type);
        }
    }

    public Card createCard(String type, Map<String, Integer> requiredResources) {
        switch (type) {
            case "action":
                return new ActionCard(requiredResources);
            case "round":
                return new RoundCard(requiredResources);
            case "occupation":
                return new OccupationCard();
            case "minorImprovement":
                return new MinorImprovementCard(requiredResources);
            // 다른 카드 타입에 대한 생성 로직 추가
            default:
                throw new IllegalArgumentException("Unknown card type: " + type);
        }
    }

    public void addActionsToCard(Card card, List<Action> actions) {
        for (Action action : actions) {
            card.addAction(action);
        }
    }
}
