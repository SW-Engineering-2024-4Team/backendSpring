package cards.actions;

import cards.common.Card;

public interface ActionCard extends Card {
    // ActionCard에만 필요한 추가 메서드가 있으면 여기에 정의
    boolean isAccumulative();

}