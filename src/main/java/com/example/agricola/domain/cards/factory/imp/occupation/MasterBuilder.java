package com.example.agricola.domain.cards.factory.imp.occupation;

import cards.common.CommonCard;
import cards.occupation.OccupationCard;
import cards.majorimprovement.MajorImprovementCard;
import enums.ExchangeTiming;
import models.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterBuilder extends OccupationCard {
    public MasterBuilder(int id) {
        super(id, "숙련 벽돌공", "주요 설비를 지을 때마다 초기 집에 추가로 지은 방의 수만큼 돌을 적게 냅니다.", null, null, 1, 4, ExchangeTiming.NONE);
    }

    @Override
    public void execute(Player player) {
        int additionalRooms = player.getPlayerBoard().getAdditionalRoomsCount();
        player.setStoneDiscount(additionalRooms);
    }
}
