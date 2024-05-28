package cards.factory.imp.occupation;

import cards.common.ExchangeableCard;
import cards.common.UnifiedCard;
import cards.occupation.OccupationCard;
import enums.ExchangeTiming;
import models.Player;

import java.util.Map;

public class MagicianOccupationCard extends OccupationCard {
    public MagicianOccupationCard(int id, String name, String description, Map<String, Integer> exchangeRate, Map<String, Integer> gainResources, int minPlayer, int maxPlayer) {
        super(id, name, description, exchangeRate, gainResources, minPlayer, maxPlayer);
    }

    // 이 클래스에서 추가로 구현할 메서드나 기능이 있다면 여기에 추가하면 됩니다.
}
