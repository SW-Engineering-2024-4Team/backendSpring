package cards.factory;

import cards.action.AccumulativeActionCard;
import cards.action.NonAccumulativeActionCard;
import cards.round.AccumulativeRoundCard;
import cards.round.NonAccumulativeRoundCard;
import cards.minorimprovement.MinorImprovementCard;
import cards.occupation.OccupationCard;
import cards.majorimprovement.MajorImprovementCard;
import cards.common.CommonCard;

import java.util.HashMap;
import java.util.List;

public class CardFactory {
    public static void createCards(List<CommonCard> actionCards, List<CommonCard> roundCards, List<CommonCard> minorImprovementCards, List<CommonCard> occupationCards, List<CommonCard> majorImprovementCards) {
        for (int i = 0; i < 8; i++) {
            actionCards.add(new AccumulativeActionCard(i, "Accumulative Action Card " + (i + 1), "Description of Accumulative Action Card " + (i + 1)));
            actionCards.add(new NonAccumulativeActionCard(i + 8, "Non-Accumulative Action Card " + (i + 1), "Description of Non-Accumulative Action Card " + (i + 1)));
        }

        int roundId = 0;
        for (int i = 0; i < 2; i++) {
            roundCards.add(new AccumulativeRoundCard(roundId++, "Accumulative Round Card A" + (i + 1), "Description of Accumulative Round Card A" + (i + 1), 1));
            roundCards.add(new NonAccumulativeRoundCard(roundId++, "Non-Accumulative Round Card A" + (i + 1), "Description of Non-Accumulative Round Card A" + (i + 1), 1));
        }
        for (int i = 0; i < 2; i++) {
            roundCards.add(new AccumulativeRoundCard(roundId++, "Accumulative Round Card B" + (i + 1), "Description of Accumulative Round Card B" + (i + 1), 2));
            roundCards.add(new NonAccumulativeRoundCard(roundId++, "Non-Accumulative Round Card B" + (i + 1), "Description of Non-Accumulative Round Card B" + (i + 1), 2));
        }
        for (int i = 0; i < 1; i++) {
            roundCards.add(new AccumulativeRoundCard(roundId++, "Accumulative Round Card C" + (i + 1), "Description of Accumulative Round Card C" + (i + 1), 3));
            roundCards.add(new NonAccumulativeRoundCard(roundId++, "Non-Accumulative Round Card C" + (i + 1), "Description of Non-Accumulative Round Card C" + (i + 1), 3));
        }
        for (int i = 0; i < 1; i++) {
            roundCards.add(new AccumulativeRoundCard(roundId++, "Accumulative Round Card D" + (i + 1), "Description of Accumulative Round Card D" + (i + 1), 4));
            roundCards.add(new NonAccumulativeRoundCard(roundId++, "Non-Accumulative Round Card D" + (i + 1), "Description of Non-Accumulative Round Card D" + (i + 1), 4));
        }
        for (int i = 0; i < 1; i++) {
            roundCards.add(new AccumulativeRoundCard(roundId++, "Accumulative Round Card E" + (i + 1), "Description of Accumulative Round Card E" + (i + 1), 5));
            roundCards.add(new NonAccumulativeRoundCard(roundId++, "Non-Accumulative Round Card E" + (i + 1), "Description of Non-Accumulative Round Card E" + (i + 1), 5));
        }
        roundCards.add(new AccumulativeRoundCard(roundId++, "Accumulative Round Card F", "Description of Accumulative Round Card F", 6));
        roundCards.add(new NonAccumulativeRoundCard(roundId++, "Non-Accumulative Round Card F", "Description of Non-Accumulative Round Card F", 6));

        for (int i = 0; i < 8; i++) {
//            minorImprovementCards.add(new MinorImprovementCard(i, "Minor Improvement Card " + (i + 1), "Description of Minor Improvement Card " + (i + 1)));
//            occupationCards.add(new OccupationCard(i, "Occupation Card " + (i + 1), "Description of Occupation Card " + (i + 1)));
        }

        for (int i = 0; i < 6; i++) {
            majorImprovementCards.add(new MajorImprovementCard(i, "Major Improvement Card " + (i + 1), "Description of Major Improvement Card " + (i + 1), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, false));
        }
    }
}
