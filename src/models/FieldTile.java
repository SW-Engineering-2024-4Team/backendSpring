package models;

public class FieldTile implements Tile {
    private int crops;

    public FieldTile(int x, int y, int initialCrops) {
        this.crops = initialCrops;
    }

    public int getCrops() {
        return crops;
    }

    public void removeCrop(int amount) {
        this.crops = Math.max(0, this.crops - amount);
    }

    // getter와 setter 메서드 추가
}
