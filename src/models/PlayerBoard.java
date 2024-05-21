package models;

import enums.RoomType;

import java.util.Map;

public class PlayerBoard {
    private Tile[][] tiles;
    private boolean[][] fences;
    private FamilyMember[][] familyMembers;
    private Animal[][] animals;
    private RoomType houseType;

    public PlayerBoard() {
        this.tiles = new Tile[3][5];
        this.fences = new boolean[4][6];
        this.familyMembers = new FamilyMember[3][5];
        this.animals = new Animal[3][5];
        this.houseType = RoomType.WOOD; // 초기 집 타입
        initializeBoard();
    }

    private void initializeBoard() {
        tiles[0][0] = new Room(RoomType.WOOD, 0, 0);
        tiles[0][1] = new Room(RoomType.WOOD, 0, 1);
        tiles[0][2] = new Room(RoomType.WOOD, 0, 2);
        familyMembers[0][1] = new FamilyMember(0, 1, true);
        familyMembers[0][2] = new FamilyMember(0, 2, true);
    }

    public boolean canBuildHouse(int x, int y, RoomType type, Map<String, Integer> playerResources) {
        if (tiles[x][y] != null) {
            return false; // 타일이 비어있지 않음
        }

        if (!isAdjacentToExistingHouse(x, y)) {
            return false; // 기존 집과 인접하지 않음
        }

        if (type != houseType) {
            return false; // 집 타입이 일치하지 않음
        }

        if (!hasSufficientResources(playerResources, type)) {
            return false; // 자원이 충분하지 않음
        }

        return true;
    }

    private boolean isAdjacentToExistingHouse(int x, int y) {
        // 기존 집과 인접한지 확인하는 로직
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX >= 0 && newX < tiles.length && newY >= 0 && newY < tiles[0].length) {
                if (tiles[newX][newY] instanceof Room && ((Room) tiles[newX][newY]).getType() == houseType) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasSufficientResources(Map<String, Integer> playerResources, RoomType type) {
        // 각 타입별 자원 5개가 있는지 확인
        String resourceType = type.name().toLowerCase();
        return playerResources.getOrDefault(resourceType, 0) >= 5;
    }

    public void buildHouse(int x, int y, RoomType type) {
        if (canBuildHouse(x, y, type, null)) { // 자원 체크는 이미 했다고 가정
            tiles[x][y] = new Room(type, x, y);
        }
    }

    public void buildBarn(int x, int y) {
        if (tiles[x][y] == null) {
//            tiles[x][y] = new Barn(x, y);
        }
    }

    public void buildFence(int startX, int startY, int endX, int endY) {
        if (isValidFencePosition(startX, startY, endX, endY)) {
            for (int i = startX; i <= endX; i++) {
                for (int j = startY; j <= endY; j++) {
                    fences[i][j] = true;
                }
            }
        }
    }

    private boolean isValidFencePosition(int startX, int startY, int endX, int endY) {
        // 울타리 위치가 유효한지 확인하는 로직 추가
        return true;
    }

    public void plantField(int x, int y, int initialCrops) {
        if (tiles[x][y] == null) {
            tiles[x][y] = new FieldTile(x, y, initialCrops);
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

    public void changeHouse() {
        // 집 개조 로직 추가
    }

    public void addFamilyMember(boolean adult) {
        // 새로운 가족 구성원 추가 로직 추가
    }

    // getter and setter methods
}
