package models;

import enums.RoomType;

import java.util.HashMap;
import java.util.Map;

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

    public boolean canBuildHouse(int x, int y, RoomType type, Map<String, Integer> resources) {
        // 1. 타일이 비어 있는지 확인
        if (tiles[x][y] != null) {
            return false;
        }

        // 2. 위치가 기존의 집과 이웃해 있는지 확인
        boolean adjacentToExistingHouse = false;
        if (x > 0 && tiles[x - 1][y] instanceof Room) adjacentToExistingHouse = true;
        if (x < tiles.length - 1 && tiles[x + 1][y] instanceof Room) adjacentToExistingHouse = true;
        if (y > 0 && tiles[x][y - 1] instanceof Room) adjacentToExistingHouse = true;
        if (y < tiles[0].length - 1 && tiles[x][y + 1] instanceof Room) adjacentToExistingHouse = true;
        if (!adjacentToExistingHouse) {
            return false;
        }

        // 3. 같은 타입의 집을 지으려는지 확인
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room && ((Room) tile).getType() != type) {
                    return false;
                }
            }
        }

        // 4. 자원이 충분한지 확인
        return true;
    }

    public void buildHouse(int x, int y, RoomType type) {
        if (tiles[x][y] == null) {
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

    public void plowField(int x, int y) {
        if (tiles[x][y] == null) {
            tiles[x][y] = new FieldTile(x, y, 0);
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
    }

    // 빈 방이 있는지 확인하는 메서드
    public boolean hasEmptyRoom() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room && !((Room) tile).hasFamilyMember()) {
                    return true;
                }
            }
        }
        return false;
    }

    // 방이 비어 있는지 확인하는 메서드
    public boolean isEmptyRoom(int x, int y) {
        return tiles[x][y] instanceof Room && !((Room) tiles[x][y]).hasFamilyMember();
    }

    // 보드에 가족 구성원을 추가하는 메서드
    public void addFamilyMemberToBoard(FamilyMember familyMember, int x, int y) {
        if (isEmptyRoom(x, y)) {
            ((Room) tiles[x][y]).setFamilyMember(familyMember);
            familyMember.setX(x);
            familyMember.setY(y);
        }
    }

    // 방이나 울타리에 동물을 배치할 수 있는지 확인하는 메서드
    public boolean canPlaceAnimal(int x, int y, String animalType) {
        // 울타리 내부나 방인지 확인
        if (tiles[x][y] instanceof Room) {
            // 모든 방을 검사하여 이미 동물이 있는지 확인
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    if (tile instanceof Room) {
                        Room room = (Room) tile;
                        if (room.hasAnimal() && !room.getAnimal().getType().equals(animalType)) {
                            return false; // 이미 다른 종류의 동물이 있다면 false
                        }
                    }
                }
            }
            // 모든 방을 통틀어 동물이 없거나 같은 종류의 동물만 있다면 true
            return true;
        } else {
            //TODO 울타리안에 동물을 넣을 수 있는지 확인하는 로직(capacity 계산)
            return fences[x][y];
        }
    }

    // 보드에 동물을 추가하는 메서드
    public void addAnimalToBoard(Animal animal, int x, int y) {
        if (canPlaceAnimal(x, y, animal.getType())) {
            if (tiles[x][y] instanceof Room) {
                ((Room) tiles[x][y]).setAnimal(animal);
            } else {
                animals[x][y] = animal;
            }
            animal.setX(x);
            animal.setY(y);
        }
    }

    public void addAnimal(Animal animal) {
        // 초기에는 보드 밖에 배치
        // 실제 게임에서는 플레이어가 특정 위치에 동물을 배치하게 될 것
        animal.setX(-1);
        animal.setY(-1);
        // 동물을 리스트나 맵에 추가하는 로직 필요
    }

    public void renovateRooms(RoomType newType, Player player) {
        int roomCount = 0;
        RoomType currentType;

        // 1. 현재 방의 타입을 확인하고 업그레이드할 수 있는 방의 수를 계산
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof Room) {
                    Room room = (Room) tile;
                    currentType = room.getType();
                    if (currentType == RoomType.WOOD && newType == RoomType.CLAY) {
                        roomCount++;
                    } else if (currentType == RoomType.CLAY && newType == RoomType.STONE) {
                        roomCount++;
                    }
                }
            }
        }

        if (roomCount == 0) {
            System.out.println("업그레이드할 수 있는 방이 없습니다.");
            return;
        }

        // 2. 자원이 충분한지 확인
        Map<String, Integer> cost = new HashMap<>();
        cost.put(newType.name().toLowerCase(), roomCount); // 방 수만큼 자원 필요
        if (player.checkResources(cost)) {
            player.payResources(cost);

            // 3. 방 타입 업그레이드
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    if (tile instanceof Room) {
                        Room room = (Room) tile;
                        currentType = room.getType();
                        if (currentType == RoomType.WOOD && newType == RoomType.CLAY) {
                            room.setType(newType);
                        } else if (currentType == RoomType.CLAY && newType == RoomType.STONE) {
                            room.setType(newType);
                        }
                    }
                }
            }
        } else {
            System.out.println("자원이 부족하여 집을 고칠 수 없습니다.");
        }
    }







    // getter and setter methods
}
