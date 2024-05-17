package cards.rounds;

import cards.common.Card;

public interface RoundCard extends Card {
    void reveal(); // 카드를 공개하는 메서드
    int getCycle(); // 주기 정보를 가져오는 메서드
    boolean isAccumulative();

}

