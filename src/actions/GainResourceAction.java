package actions;

import models.Player;

public class GainResourceAction implements Action {
    private String resource;
    private int amount;

    public GainResourceAction(String resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    @Override
    public void execute(Player player) {
        int finalAmount = player.getModifiedAction("gainResource_" + resource, amount);
        player.addResource(resource, finalAmount);
    }

    public void accumulateResource() {
        amount += 1; // 자원 누적 로직
    }

    public String getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
