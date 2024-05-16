package actions;

import models.Player;

public class BuildStructureAction implements Action {
    private String structureType;
    private int x;
    private int y;

    public BuildStructureAction(String structureType, int x, int y) {
        this.structureType = structureType;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Player player) {
        player.getPlayerBoard().buildStructure(structureType, x, y);
    }
}
