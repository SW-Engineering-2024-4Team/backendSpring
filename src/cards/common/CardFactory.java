package cards.common;

import cards.actions.ExampleActionCard;
import cards.rounds.ExampleRoundCard;

public class CardFactory {
    public Card createCard(String cardType, int cycle) {
        switch (cardType) {
            case "ActionCard":
//                return new ExampleActionCard("Action Card Example", "Example description for action card");
            case "RoundCard":
//                return new ExampleRoundCard("Round Card Example", "Example description for round card", cycle);
            case "MinorImprovementCard":
//                return new ExampleMinorImprovementCard("Minor Improvement Card Example", "Example description for minor improvement card");
            case "OccupationCard":
//                return new ExampleOccupationCard("Occupation Card Example", "Example description for occupation card");
            case "MajorImprovementCard":
//                return new ExampleMajorImprovementCard("Major Improvement Card Example", "Example description for major improvement card");
            default:
                throw new IllegalArgumentException("Unknown card type: " + cardType);
        }
    }
}
