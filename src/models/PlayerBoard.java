package models;

import enums.RoomType;

public class PlayerBoard {
    private Tile[][] tiles;
    private boolean[][] fences;
    private FamilyMember[][] familyMembers;
    private Animal[][] animals;

    public PlayerBoard() {
        this.tiles = new Tile[3][5];
        this.fences = new boolean[4][6];
        this.familyMembers = new FamilyMember[3][5];
        this.animals = new Animal[3][5];
        initializeBoard();
    }

    private void initializeBoard() {
        tiles[0][0] = new Room(RoomType.WOOD, 0, 0);
        tiles[0][1] = new Room(RoomType.WOOD, 0, 1);
        tiles[0][2] = new Room(RoomType.WOOD, 0, 2);
        familyMembers[0][1] = new FamilyMember(0, 1, true);
        familyMembers[0][2] = new FamilyMember(0, 2, true);
    }

    public void buildStructure(String structureType, int x, int y) {
        // 기물 생성 로직
        if (tiles[x][y] == null) {
            if (structureType.equals("house")) {
                tiles[x][y] = new Room(RoomType.WOOD, x, y);
            }
            // 다른 구조물 생성 로직 추가
        }
    }

    public void resetFamilyMembers() {
        // 가족 구성원을 초기 위치로 리셋하는 로직
        initializeBoard();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public FamilyMember[][] getFamilyMembers() {
        return familyMembers;
    }

    public Animal[][] getAnimals() {
        return animals;
    }

    public void removeFamilyMember(int x, int y) {
        familyMembers[x][y] = null;
    }

    // getter and setter methods
}
