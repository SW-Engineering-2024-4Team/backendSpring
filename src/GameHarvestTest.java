import cards.common.ActionRoundCard;
import cards.common.CommonCard;
import controllers.GameController;
import controllers.RoomController;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameHarvestTest {
    public static void main(String[] args) {
        // 가상 플레이어 목록 생성
        List<Player> players = createMockPlayers();

        // RoomController를 생성하고 가상 플레이어 목록을 전달하여 게임 시작
        RoomController roomController = new RoomController();
        roomController.handleGameStart("TestRoom123", players);

        // GameController를 가져와서 초기화 상태 확인
        GameController gameController = roomController.getGameController();

        // 모든 플레이어의 상태를 확인
        for (Player player : gameController.getPlayers()) {
            System.out.println("Player ID: " + player.getId());
            System.out.println("Occupation Cards: " + getCardNames(player.getOccupationCards()));
            System.out.println("Minor Improvement Cards: " + getCardNames(player.getMinorImprovementCards()));
            System.out.println("Resources: " + player.getResources());
            System.out.println("Player Board Initialized: " + player.getPlayerBoard().getTiles().length + "x" + player.getPlayerBoard().getTiles()[0].length);
            System.out.println("Family Members on Board: ");
            printFamilyMembers(player);
            System.out.println("----------");
        }

        // 1라운드 진행
        gameController.startGame();

        // 메인 보드의 카드 배치 상태 확인
        System.out.println("Main Board Action Cards: " + getActionRoundCardNames(gameController.getMainBoard().getActionCards()));
        System.out.println("Main Board Round Cards: " + getActionRoundCardNames(gameController.getMainBoard().getRoundCards()));
        System.out.println("Main Board Major Improvement Cards: " + getCardNames(gameController.getMainBoard().getMajorImprovementCards()));

        // 수확 단계 테스트
        System.out.println("Starting harvest phase test...");
        gameController.harvestPhase();

        // 수확 단계 이후 각 플레이어의 상태를 확인
        for (Player player : gameController.getPlayers()) {
            System.out.println("After harvest phase - Player ID: " + player.getId());
            System.out.println("Resources: " + player.getResources());
            System.out.println("Player Board Crops and Animals: ");
            printPlayerBoardCropsAndAnimals(player);
            System.out.println("----------");
        }
    }

    private static List<Player> createMockPlayers() {
        List<Player> players = new ArrayList<>();
        RoomController roomController = new RoomController();
        GameController mockGameController = new GameController("TestRoom123", roomController, players);
        for (int i = 1; i <= 4; i++) {
            Player player = new Player("player" + i, "Player " + i, mockGameController);
            initializeFamilyMembers(player);
            players.add(player);
        }
        return players;
    }

    private static void initializeFamilyMembers(Player player) {
        // 기본 가족 구성원 설정
        player.getPlayerBoard().getFamilyMembers()[0][1] = new FamilyMember(0, 1, true);
        player.getPlayerBoard().getFamilyMembers()[0][2] = new FamilyMember(0, 2, true);
    }

    private static void printFamilyMembers(Player player) {
        FamilyMember[][] familyMembers = player.getPlayerBoard().getFamilyMembers();
        for (int i = 0; i < familyMembers.length; i++) {
            for (int j = 0; j < familyMembers[i].length; j++) {
                if (familyMembers[i][j] != null) {
                    FamilyMember member = familyMembers[i][j];
                    System.out.println("  Family Member at (" + i + ", " + j + ") - Adult: " + member.isAdult());
                }
            }
        }
    }

    private static List<String> getCardNames(List<? extends CommonCard> cards) {
        List<String> cardNames = new ArrayList<>();
        for (CommonCard card : cards) {
            cardNames.add(card.getName());
        }
        return cardNames;
    }

    private static List<String> getActionRoundCardNames(List<? extends ActionRoundCard> cards) {
        List<String> cardNames = new ArrayList<>();
        for (ActionRoundCard card : cards) {
            cardNames.add(card.getName());
        }
        return cardNames;
    }

    private static void printPlayerBoardCropsAndAnimals(Player player) {
        // 플레이어 보드의 작물 및 동물 상태를 출력
        System.out.println("Crops:");
        for (Tile[] row : player.getPlayerBoard().getTiles()) {
            for (Tile tile : row) {
                if (tile instanceof FieldTile) {
                    FieldTile field = (FieldTile) tile;
                    System.out.println("  Field at (" + field.getX() + ", " + field.getY() + ") - Crops: " + field.getCrops());
                }
            }
        }

        System.out.println("Animals:");
        for (Animal[] row : player.getPlayerBoard().getAnimals()) {
            for (Animal animal : row) {
                if (animal != null) {
                    System.out.println("  Animal at (" + animal.getX() + ", " + animal.getY() + ") - Type: " + animal.getType());
                }
            }
        }
    }
}
