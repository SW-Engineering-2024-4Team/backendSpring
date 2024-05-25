package test.actionroundcardtest;

import cards.action.NonAccumulativeActionCard;
import cards.common.ActionRoundCard;
import controllers.GameController;
import controllers.RoomController;
import enums.RoomType;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructionTest {
    private GameController gameController;
    private Player player;
    private ActionRoundCard actionCard;

    @BeforeEach
    public void setUp() {
        List<Player> players = createMockPlayers();
        RoomController roomController = new RoomController();
        roomController.handleGameStart("TestRoom123", players);
        gameController = roomController.getGameController();
        player = gameController.getPlayers().get(0);

        actionCard = new NonAccumulativeActionCard(1, "Construction Test Card", "This is a construction test card.");
        gameController.getMainBoard().getActionCards().add(actionCard);
    }

    private List<Player> createMockPlayers() {
        List<Player> players = new ArrayList<>();
        GameController mockGameController = new GameController("TestRoom123", new RoomController(), players);
        for (int i = 1; i <= 4; i++) {
            players.add(new Player("player" + i, "Player " + i, mockGameController));
        }
        return players;
    }

    //    private void printPlayerBoard(String message, Set<int[]> validPositions) {
//        System.out.println(message);
//        Tile[][] tiles = player.getPlayerBoard().getTiles();
//        for (int i = 0; i < tiles.length; i++) {
//            for (int j = 0; j < tiles[i].length; j++) {
//                boolean isValidPosition = false;
//                for (int[] pos : validPositions) {
//                    if (pos[0] == i && pos[1] == j) {
//                        isValidPosition = true;
//                        break;
//                    }
//                }
//                if (tiles[i][j] == null) {
//                    if (isValidPosition) {
//                        System.out.print("[*]");
//                    } else {
//                        System.out.print("[ ]");
//                    }
//                } else if (tiles[i][j] instanceof Room) {
//                    System.out.print("[R]");
//                } else if (tiles[i][j] instanceof Barn) {
//                    System.out.print("[B]");
//                } else if (tiles[i][j] instanceof FieldTile) {
//                    System.out.print("[F]");
//                }
//            }
//            System.out.println();
//        }
//    }
    // 타일과 울타리를 시각화하는 메서드
    public void printPlayerBoardWithFences(String message, Set<int[]> validPositions) {
        System.out.println(message);
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        boolean[][][] fences = player.getPlayerBoard().getFences();
        int rows = tiles.length;
        int cols = tiles[0].length;

        // Print the top boundary of the board
        for (int i = 0; i < rows; i++) {
            // Print top fences
            for (int j = 0; j < cols; j++) {
                System.out.print("+");
                if (fences[i][j][0]) {
                    System.out.print("---");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println("+");

            // Print left fences and tiles
            for (int j = 0; j < cols; j++) {
                if (fences[i][j][2]) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }

                boolean isValidPosition = false;
                for (int[] pos : validPositions) {
                    if (pos[0] == i && pos[1] == j) {
                        isValidPosition = true;
                        break;
                    }
                }

                if (tiles[i][j] == null) {
                    if (isValidPosition) {
                        System.out.print("  *  ");
                    } else {
                        System.out.print("   ");
                    }
                } else if (tiles[i][j] instanceof Room) {
                    System.out.print(" R ");
                } else if (tiles[i][j] instanceof Barn) {
                    System.out.print(" B ");
                } else if (tiles[i][j] instanceof FieldTile) {
                    System.out.print(" F ");
                }
            }
            if (fences[i][cols - 1][3]) {
                System.out.print("|");
            }
            System.out.println();
        }

        // Print the bottom boundary of the board
        for (int j = 0; j < cols; j++) {
            System.out.print("+");
            if (fences[rows - 1][j][1]) {
                System.out.print("---");
            } else {
                System.out.print("   ");
            }
        }
        System.out.println("+");
    }




    // Fence를 카운트하는 메서드 수정
    private int countFence(boolean[][][] fences, int row, int col) {
        int count = 0;
        if (row >= 0 && col >= 0 && row < fences.length && col < fences[0].length) {
            if (fences[row][col][0]) count++; // 상단 울타리
            if (fences[row][col][1]) count++; // 하단 울타리
            if (fences[row][col][2]) count++; // 좌측 울타리
            if (fences[row][col][3]) count++; // 우측 울타리
        }
        if (row >= 0 && row < fences.length && col - 1 >= 0 && col - 1 < fences[0].length) {
            if (fences[row][col - 1][3]) count++; // 좌측 울타리
        }
        if (row - 1 >= 0 && row - 1 < fences.length && col >= 0 && col < fences[0].length) {
            if (fences[row - 1][col][1]) count++; // 상단 울타리
        }
        if (row >= 0 && row < fences.length && col + 1 >= 0 && col + 1 < fences[0].length) {
            if (fences[row][col + 1][2]) count++; // 우측 울타리
        }
        return count;
    }



    private void printPlayerResources(String message) {
        System.out.println(message);
        for (Map.Entry<String, Integer> resource : player.getResources().entrySet()) {
            System.out.println("  " + resource.getKey() + ": " + resource.getValue());
        }
    }

    private void printHouseTypes(String message) {
        System.out.println(message);
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof Room) {
                    Room room = (Room) tiles[i][j];
                    switch (room.getType()) {
                        case WOOD:
                            System.out.print("[W]");
                            break;
                        case CLAY:
                            System.out.print("[C]");
                            break;
                        case STONE:
                            System.out.print("[S]");
                            break;
                    }
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }

    // 지정한 위치의 타일 울타리 상태를 출력하는 메서드
    public void printTileFenceInfo(int x, int y) {
        boolean[][][] fences = player.getPlayerBoard().getFences();
        System.out.println("Fence information for tile at (" + x + ", " + y + "):");
        System.out.println("  Top: " + (fences[x][y][0] ? "Present" : "Absent"));
        System.out.println("  Bottom: " + (fences[x][y][1] ? "Present" : "Absent"));
        System.out.println("  Left: " + (fences[x][y][2] ? "Present" : "Absent"));
        System.out.println("  Right: " + (fences[x][y][3] ? "Present" : "Absent"));
    }

    private boolean isValidTile(int x, int y) {
        return x >= 0 && x < player.getPlayerBoard().getTiles().length && y >= 0 && y < player.getPlayerBoard().getTiles()[0].length;
    }


    private void buildHouse() {
        Set<int[]> validPositions = player.getPlayerBoard().getValidHousePositions();
        printPlayerBoardWithFences("Player board before building house:", validPositions);
        actionCard.buildHouse(player);
        validPositions = player.getPlayerBoard().getValidHousePositions();
        printPlayerBoardWithFences("Player board after building house:", validPositions);
    }

    private void plowField() {
        Set<int[]> validPositions = player.getPlayerBoard().getValidPlowPositions();
        printPlayerBoardWithFences("Player board before plowing field:", validPositions);
        actionCard.plowField(player);
        validPositions = player.getPlayerBoard().getValidPlowPositions();
        printPlayerBoardWithFences("Player board after plowing field:", validPositions);
    }

    private void buildBarn() {
        Set<int[]> validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board before building barn:", validPositions);
        actionCard.buildBarn(player);
        validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board after building barn:", validPositions);
    }

    @Test
    public void testBuildHouse() {
        player.resetResources();
        player.addResource("wood", 20);
        // 초기 상태 출력
        Set<int[]> validPositions = player.getPlayerBoard().getValidHousePositions();
        printPlayerBoardWithFences("Player board before building house:", validPositions);

        // 첫 번째 집 짓기
        actionCard.buildHouse(player);

        // 상태 출력
        validPositions = player.getPlayerBoard().getValidHousePositions();
        printPlayerBoardWithFences("Player board after building first house:", validPositions);

        // 두 번째 집 짓기
        actionCard.buildHouse(player);

        // 상태 출력
        validPositions = player.getPlayerBoard().getValidHousePositions();
        printPlayerBoardWithFences("Player board after building second house:", validPositions);

        // 검증
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        int houseCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof Room) {
                    houseCount++;
                }
            }
        }
        assertEquals(4, houseCount, "There should be four rooms (houses) built on the board.");
    }

    @Test
    public void testRenovateRooms() {
        player.resetResources();
        player.addResource("wood", 5);  // 초기에 나무로 된 집을 짓기 위한 자원 추가
        player.addResource("clay", 10); // 흙으로 집을 업그레이드하기 위한 자원 추가

        // 초기 상태 출력
        Set<int[]> validPositions = player.getPlayerBoard().getValidHousePositions();
        printHouseTypes("Player board before building and renovating house:");
        printPlayerBoardWithFences("Player board before building and renovating house:", validPositions);
        printPlayerResources("Player resources before renovating house.");

        // 나무 집 짓기
        actionCard.buildHouse(player);
        validPositions = player.getPlayerBoard().getValidHousePositions();
        printHouseTypes("Player board after building wooden house:");
        printPlayerBoardWithFences("Player board after building wooden house:", validPositions);
        printPlayerResources("Player resources after building house.");

        // 흙 집으로 업그레이드
        actionCard.renovateRooms(player);

        // 업그레이드 후 상태 출력
        printHouseTypes("Player board after renovating to clay house:");
        printPlayerBoardWithFences("Player board after renovating to clay house:", validPositions);
        printPlayerResources("Player resources after renovating to clay house.");

        // 검증
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        boolean allRoomsUpgraded = true;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof Room) {
                    Room room = (Room) tiles[i][j];
                    if (room.getType() != RoomType.CLAY) {
                        allRoomsUpgraded = false;
                        break;
                    }
                }
            }
        }
        assertTrue(allRoomsUpgraded, "All rooms should be upgraded to clay house.");
    }

    @Test
    public void testRenovateHouseMultipleStages() {
        player.resetResources();
        player.addResource("wood", 10);  // 초기에 나무로 된 집을 짓기 위한 자원 추가
        player.addResource("clay", 10);  // 흙으로 집을 업그레이드하기 위한 자원 추가
        player.addResource("stone", 10); // 돌로 집을 업그레이드하기 위한 자원 추가

        // 초기 상태 출력
        Set<int[]> validPositions = player.getPlayerBoard().getValidHousePositions();
        printHouseTypes("Player board before building and renovating house:");
        printPlayerBoardWithFences("Player board before building and renovating house:", validPositions);
        printPlayerResources("Player resources before renovating house.");

        // 나무 집 짓기
        actionCard.buildHouse(player);
        validPositions = player.getPlayerBoard().getValidHousePositions();
        printHouseTypes("Player board after building wooden house:");
        printPlayerBoardWithFences("Player board after building wooden house:", validPositions);
        printPlayerResources("Player resources after building house.");

        // 흙 집으로 업그레이드
        actionCard.renovateRooms(player);
        printHouseTypes("Player board after renovating to clay house:");
        printPlayerBoardWithFences("Player board after renovating to clay house:", validPositions);
        printPlayerResources("Player resources after renovating to clay house.");

        // 흙 집 짓기
        actionCard.buildHouse(player);
        validPositions = player.getPlayerBoard().getValidHousePositions();
        printHouseTypes("Player board after building another clay house:");
        printPlayerBoardWithFences("Player board after building another clay house:", validPositions);
        printPlayerResources("Player resources after building another clay house.");

        // 돌 집으로 업그레이드
        actionCard.renovateRooms(player);
        printHouseTypes("Player board after renovating to stone house:");
        printPlayerBoardWithFences("Player board after renovating to stone house:", validPositions);
        printPlayerResources("Player resources after renovating to stone house.");

        // 돌 집에서 더 이상 업그레이드할 수 없는 경우
        actionCard.renovateRooms(player);
        printHouseTypes("Player board after attempting to renovate stone house:");
        printPlayerBoardWithFences("Player board after attempting to renovate stone house:", validPositions);
        printPlayerResources("Player resources after attempting to renovate stone house.");

        // 검증
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        boolean allRoomsUpgradedToStone = true;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof Room) {
                    Room room = (Room) tiles[i][j];
                    if (room.getType() != RoomType.STONE) {
                        allRoomsUpgradedToStone = false;
                        break;
                    }
                }
            }
        }
        assertTrue(allRoomsUpgradedToStone, "All rooms should be upgraded to stone house.");
    }




    @Test
    public void testBuildBarn() {
        player.resetResources();
        player.addResource("wood", 4);
        // 초기 상태 출력
        Set<int[]> validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board before building barn:", validPositions);

        // 첫 번째 외양간 짓기
        actionCard.buildBarn(player);

        // 상태 출력
        validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board after building first barn:", validPositions);

        // 두 번째 외양간 짓기
        actionCard.buildBarn(player);

        // 상태 출력
        validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board after building second barn:", validPositions);
        printPlayerResources("Player resources after building second barn:");

        // 검증
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        int barnCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof Barn) {
                    barnCount++;
                }
            }
        }
        assertEquals(2, barnCount, "There should be two barns built on the board.");
    }


    @Test
    public void testPlowField() {
        // 초기 상태 출력
        Set<int[]> validPositions = player.getPlayerBoard().getValidPlowPositions();
        printPlayerBoardWithFences("Player board before plowing field:", validPositions);

        // 첫 번째 밭 일구기
        actionCard.plowField(player);

        // 상태 출력
        validPositions = player.getPlayerBoard().getValidPlowPositions();
        printPlayerBoardWithFences("Player board after plowing first field:", validPositions);

        // 두 번째 밭 일구기
        actionCard.plowField(player);

        // 상태 출력
        validPositions = player.getPlayerBoard().getValidPlowPositions();
        printPlayerBoardWithFences("Player board after plowing second field:", validPositions);

        // 검증
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        int fieldCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof FieldTile) {
                    fieldCount++;
                }
            }
        }
        assertEquals(2, fieldCount, "There should be two fields plowed on the board.");
    }

    // 집2,밭3, 외양간4을 랜덤하게 짓는 테스트 코드
    @Test
    public void testRandomConstruction() {
        player.resetResources();
        player.addResource("wood", 30);
        Random random = new Random();
        int housesBuilt = 0;
        int fieldsPlowed = 0;
        int barnsBuilt = 0;

        while (housesBuilt < 2 || fieldsPlowed < 3 || barnsBuilt < 4) {
            int choice = random.nextInt(3);

            if (choice == 0 && housesBuilt < 2) {
                buildHouse();
                housesBuilt++;
            } else if (choice == 1 && fieldsPlowed < 3) {
                plowField();
                fieldsPlowed++;
            } else if (choice == 2 && barnsBuilt < 4) {
                buildBarn();
                barnsBuilt++;
            }
        }

        // 최종 검증
        Tile[][] tiles = player.getPlayerBoard().getTiles();
        int houseCount = 0, fieldCount = 0, barnCount = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] instanceof Room) houseCount++;
                else if (tiles[i][j] instanceof FieldTile) fieldCount++;
                else if (tiles[i][j] instanceof Barn) barnCount++;
            }
        }

        assertEquals(4, houseCount, "There should be two houses built on the board.");
        assertEquals(3, fieldCount, "There should be three fields plowed on the board.");
        assertEquals(4, barnCount, "There should be four barns built on the board.");
    }

    // 외양간 짓고 유효한 울타리 위치 확인
    @Test
    public void testValidFencePositionsWithBarns() {
        // 플레이어의 자원 설정
        player.resetResources();
        player.addResource("wood", 4); // 외양간을 짓기 위한 나무 자원 추가

        // 초기 상태 출력
        printPlayerResources("Resources before building barns:");
        Set<int[]> validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board before building barns:", validPositions);

        // 외양간 2개 짓기
        actionCard.buildBarn(player);
        actionCard.buildBarn(player);

        // 외양간을 지은 후 상태 출력
        printPlayerResources("Resources after building barns:");
        validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board after building barns:", validPositions);

        // 울타리 유효 위치 확인
        validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board with valid fence positions:", validPositions);

        // 검증
        // 울타리 유효 위치 검증 추가
        boolean validFencePositionFound = false;
        for (int[] pos : validPositions) {
            if (player.getPlayerBoard().getTiles()[pos[0]][pos[1]] instanceof Barn) {
                validFencePositionFound = true;
                break;
            }
        }
        assertTrue(validFencePositionFound, "There should be valid fence positions around the barns.");
    }

    // TODO 펜스 짓기
//    @Test
//    public void testBuildFence() {
//        player.resetResources();
//        player.addResource("wood", 20);
//
//        // Initial state
//        printPlayerResources("Resources before building fence:");
//        Set<int[]> validPositions = player.getPlayerBoard().getValidFencePositions();
//        printPlayerBoardWithFences("Player board before building fence:", validPositions);
//
//        // Build fence
//        List<int[]> selectedPositions = Arrays.asList(
//                new int[]{1, 1},
//                new int[]{1, 2},
//                new int[]{1, 3}
//        );
//        player.getPlayerBoard().buildFences(selectedPositions, player);
//
//        // State after building fence
//        printPlayerResources("Resources after building fence:");
//        validPositions = player.getPlayerBoard().getValidFencePositions();
//        printPlayerBoardWithFences("Player board after building fence:", validPositions);
//
//        printTileFenceInfo(1, 1);
//        printTileFenceInfo(1, 2);
//        printTileFenceInfo(1, 3);
//
//        // Verification
//        boolean fenceBuilt = false;
//        for (boolean[][] row : player.getPlayerBoard().getFences()) {
//            for (boolean[] tile : row) {
//                for (boolean hasFence : tile) {
//                    if (hasFence) {
//                        fenceBuilt = true;
//                        break;
//                    }
//                }
//                if (fenceBuilt) break;
//            }
//            if (fenceBuilt) break;
//        }
//        assertTrue(fenceBuilt, "A fence should be built at the expected position.");
//    }
    @Test
    public void testBuildFence() {
        player.resetResources();
        player.addResource("wood", 20);

        // Initial state
        printPlayerResources("Resources before building fence:");
        Set<int[]> validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board before building fence:", validPositions);

        // Build fence
        List<int[]> selectedPositions = Arrays.asList(
                new int[]{1, 1},
                new int[]{1, 2},
                new int[]{2, 2}
        );
        player.getPlayerBoard().buildFences(selectedPositions, player);

        // State after building fence
        printPlayerResources("Resources after building fence:");
        validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board after building fence:", validPositions);

        printTileFenceInfo(1, 1);
        printTileFenceInfo(1, 2);
        printTileFenceInfo(2, 2);

        // Verification
        boolean fenceBuilt = false;
        for (boolean[][] row : player.getPlayerBoard().getFences()) {
            for (boolean[] tile : row) {
                for (boolean hasFence : tile) {
                    if (hasFence) {
                        fenceBuilt = true;
                        break;
                    }
                }
                if (fenceBuilt) break;
            }
            if (fenceBuilt) break;
        }
        assertTrue(fenceBuilt, "A fence should be built at the expected position.");
    }







    @Test
    public void testValidFencePositionsWithBarnsAndOneFence() {
        // 플레이어의 자원 설정
        player.resetResources();
        player.addResource("wood", 12); // 외양간을 짓기 위한 나무 자원 추가 및 울타리를 위한 추가 자원

        // 초기 상태 출력
        printPlayerResources("Resources before building barns:");
        Set<int[]> validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board before building barns:", validPositions);

        // 외양간 2개 짓기
        player.getPlayerBoard().buildBarn(1, 1);
        player.getPlayerBoard().buildBarn(1, 2);

        // 외양간을 지은 후 상태 출력
        printPlayerResources("Resources after building barns:");
        validPositions = player.getPlayerBoard().getValidBarnPositions();
        printPlayerBoardWithFences("Player board after building barns:", validPositions);

        // 울타리 유효 위치 확인
        validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board with valid fence positions:", validPositions);

        // 울타리 위치 설정
        List<int[]> fencePositions = Arrays.asList(
                new int[]{1, 1},
                new int[]{1, 2},
                new int[]{1, 3}
        );
        player.getPlayerBoard().buildFences(fencePositions, player);

        // 울타리를 지은 후 상태 출력
        printPlayerResources("Resources after building fences:");
        validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board after building fences:", validPositions);

        printTileFenceInfo(1, 1);
        printTileFenceInfo(1, 2);
        printTileFenceInfo(1, 3);
        printTileFenceInfo(0, 1);
        printTileFenceInfo(2, 1);
        printTileFenceInfo(1, 0);
        printTileFenceInfo(1, 4);

        // 검증
        boolean validFencePositionFound = false;
        for (int[] pos : validPositions) {
            if (player.getPlayerBoard().getTiles()[pos[0]][pos[1]] == null || player.getPlayerBoard().getTiles()[pos[0]][pos[1]] instanceof Barn) {
                validFencePositionFound = true;
                break;
            }
        }
        assertTrue(validFencePositionFound, "There should be valid fence positions around the barns and the first fence.");
    }

    // TODO 수용능력 계산
    @Test
    public void testBuildTwoFences() {
        // 플레이어의 자원 설정
        player.resetResources();
        player.addResource("wood", 16); // 울타리를 짓기 위한 나무 자원 추가

        // 초기 상태 출력
        printPlayerResources("Resources before building fences:");
        Set<int[]> validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board before building fences:", validPositions);

        // 첫 번째 울타리 위치 설정 및 건설
        List<int[]> firstFencePositions = Arrays.asList(
                new int[]{1, 2},
                new int[]{1, 3}
        );
        player.getPlayerBoard().buildFences(firstFencePositions, player);

        // 첫 번째 울타리를 지은 후 상태 출력
        printPlayerResources("Resources after building first fence:");
        validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board after building first fence:", validPositions);

        // 두 번째 울타리 위치 설정 및 건설
        List<int[]> secondFencePositions = Arrays.asList(
                new int[]{2, 2},
                new int[]{2, 3}
        );
        player.getPlayerBoard().buildFences(secondFencePositions, player);

        // 두 번째 울타리를 지은 후 상태 출력
        printPlayerResources("Resources after building second fence:");
        validPositions = player.getPlayerBoard().getValidFencePositions();
        printPlayerBoardWithFences("Player board after building second fence:", validPositions);

        // 첫 번째 울타리 타일 정보 출력
        printTileFenceInfo(1, 2);
        printTileFenceInfo(1, 3);
        // 두 번째 울타리 타일 정보 출력
        printTileFenceInfo(2, 2);
        printTileFenceInfo(2, 3);
        // 인접한 타일 정보 출력
        if (isValidTile(0, 2)) printTileFenceInfo(0, 2);
        if (isValidTile(1, 1)) printTileFenceInfo(1, 1);
        if (isValidTile(1, 4)) printTileFenceInfo(1, 4);
        if (isValidTile(2, 1)) printTileFenceInfo(2, 1);
        if (isValidTile(2, 4)) printTileFenceInfo(2, 4);

        // 검증
        boolean validFencePositionFound = false;
        for (int[] pos : validPositions) {
            if (player.getPlayerBoard().getTiles()[pos[0]][pos[1]] == null || player.getPlayerBoard().getTiles()[pos[0]][pos[1]] instanceof Barn) {
                validFencePositionFound = true;
                break;
            }
        }
        assertTrue(validFencePositionFound, "There should be valid fence positions around the barns and the fences.");

        // 각 펜스 영역의 수용 능력 계산 및 출력
        List<FenceArea> fenceAreas = player.getPlayerBoard().getFenceAreas();
        for (int i = 0; i < fenceAreas.size(); i++) {
            int capacity = fenceAreas.get(i).calculateCapacity();
            System.out.println("Fence Area " + (i + 1) + " Capacity: " + capacity);
        }
    }

}




