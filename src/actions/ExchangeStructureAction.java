package actions;

import models.Player;

public class ExchangeStructureAction implements Action {
    private String fromType;
    private String toType;

    public ExchangeStructureAction(String fromType, String toType) {
        this.fromType = fromType;
        this.toType = toType;
    }

    @Override
    public void execute(Player player) {
        // 기물 교환 로직
    }
}
